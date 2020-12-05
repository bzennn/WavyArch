package xyz.bzennn.wavyarch.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import xyz.bzennn.wavyarch.config.CommonProperties;
import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.data.model.AudioMaker;
import xyz.bzennn.wavyarch.form.PerformerEditForm;
import xyz.bzennn.wavyarch.service.AudioService;
import xyz.bzennn.wavyarch.service.AudioSortingService;
import xyz.bzennn.wavyarch.service.PerformerService;
import xyz.bzennn.wavyarch.util.FileUtils;
import xyz.bzennn.wavyarch.util.ImageUtils;
import xyz.bzennn.wavyarch.util.StringUtils;

@Controller
@RequestMapping("/performers")
@PropertySource("classpath:upload.properties")
public class PerformersController {

	@Value("${upload.file_path}")
	private String uploadPath;
	
	@Autowired
	FileUtils fileUtils;
	
	@Autowired
	StringUtils stringUtils;
	
	@Autowired
	ImageUtils imageUtils;
	
	@Autowired
	PerformerService performerService;
	
	@Autowired
	AudioService audioService;
	
	@Autowired
	AudioSortingService sortingService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showPerformersPage(Model model) {
		Account account = (Account) model.getAttribute("user");
		List<AudioMaker> performers = performerService.findByAccount(account);
		
		model.addAttribute("accountPerformers", performers);
		
		return "performers";
	}
	
	@RequestMapping(path = "/performer/{performerName}", method = RequestMethod.GET)
	public String showPerformerPage(@PathVariable String performerName, @RequestParam Map<String, String> params, Model model) {
		if (performerName == null) {
			model.asMap().clear();
			return "redirect:/performers";
		}
		
		AudioMaker performer = performerService.findByName(performerName);
		if (performer == null) {
			model.asMap().clear();
			return "redirect:/performers";
		}
		
		Account account = (Account) model.getAttribute("user");
		Set<String> audiosNotInAccount = new HashSet<String>();
		List<Audio> accountAudios = audioService.findByAccount(account);
		List<Audio> performerAudios = performerService.findAudiosByName(performerName);
		for (Audio audio : performerAudios) {
			if (!accountAudios.contains(audio)) {
				audiosNotInAccount.add(audio.getName());
			}
		}
		
		sortingService.sort(performerAudios, params);
		
		model.addAttribute("audiosNotInAccount", audiosNotInAccount);
		model.addAttribute("performer", performer);
		model.addAttribute("performerAudios", performerAudios);
		
		return "performer_audios";
	}
	
	@RequestMapping(path = "/performer/edit/{performerName}", method = RequestMethod.GET)
	public String showEditPerformerPage(@PathVariable String performerName, Model model) {
		if (performerName == null) {
			model.asMap().clear();
			return "redirect:/performers";
		}
		
		AudioMaker performer = performerService.findByName(performerName);
		if (performer == null) {
			return "redirect:/performers";
		}
		
		Date creationDate = performer.getCreationDate();
		if (creationDate != null) {
			model.addAttribute("creationDate", new SimpleDateFormat("yyyy-MM").format(creationDate));
		}
		
		String description = performer.getDescription();
		if (description != null && !description.isEmpty()) {
			model.addAttribute("description", description);
		}
		
		model.addAttribute("performer", performer);
		
		return "performer_edit";
	}

	@RequestMapping(path = "/performer/edit/{performerName}", method = RequestMethod.POST)
	public String handleEditPerformer(@Valid PerformerEditForm form, BindingResult result, @RequestParam("image") MultipartFile file, @PathVariable String performerName, Model model) throws UnsupportedEncodingException {
		if (result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
			
			return "performer_edit";	
		}
		
		AudioMaker performer = performerService.findByName(performerName);
		
		if (file != null && file.getSize() != 0 && file.getContentType().contains("image/")) {
			String imagePath = uploadPath + CommonProperties.AUTHOR_COVER_FILE_PATH;
			File imageFile = fileUtils.getUniqueFile(imagePath, file.getContentType());
			
			try {
				file.transferTo(imageFile);
			} catch (Exception e) {
				e.printStackTrace();
				return "playlist_edit";
			}
			
			String currentImagePath = performer.getImagePath();
			if (currentImagePath != null && !currentImagePath.isEmpty()) {
				fileUtils.deleteFile(uploadPath + CommonProperties.AUTHOR_COVER_FILE_PATH, currentImagePath);
			}
			
			try {
				imageUtils.cropImageToSquare(imageFile);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			form.setImagePath(imageFile.getName());
		}
		
		performer = performerService.buildAudioMakerFromEditForm(form);
		performerService.update(performer);
		
		String encodedPerformerName = performerName;
		if (!stringUtils.isAsciiString(performerName)) {
			encodedPerformerName = stringUtils.encodeToUtf8(performerName);
		}
		
		model.asMap().clear();
		return "redirect:/performers/performer/edit/" + encodedPerformerName;
	}
	
}
