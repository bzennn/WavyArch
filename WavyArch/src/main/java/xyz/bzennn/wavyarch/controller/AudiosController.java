package xyz.bzennn.wavyarch.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import xyz.bzennn.wavyarch.data.model.AudioAlbum;
import xyz.bzennn.wavyarch.data.model.AudioGenre;
import xyz.bzennn.wavyarch.data.model.AudioTag;
import xyz.bzennn.wavyarch.data.model.Author;
import xyz.bzennn.wavyarch.data.model.Performer;
import xyz.bzennn.wavyarch.form.AudioEditForm;
import xyz.bzennn.wavyarch.form.AudioUploadForm;
import xyz.bzennn.wavyarch.service.AudioService;
import xyz.bzennn.wavyarch.util.AudioUtils;
import xyz.bzennn.wavyarch.util.FileUtils;

@Controller
@RequestMapping("/audios")
@PropertySource("classpath:upload.properties")
public class AudiosController {

	@Value("${upload.file_path}")
	private String uploadPath;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Autowired
	private AudioUtils audioUtils;
	
	@Autowired
	private AudioService audioService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showAccountAudiosPage(Model model) {
		return "account_audios";
	}

	@RequestMapping(path = "/upload", method = RequestMethod.GET)
	public String showUploadAudioPage() {
		return "audio_upload";
	}
	
	@RequestMapping(path = "/upload", method = RequestMethod.POST)
	public String handleUploadAudio(@Valid AudioUploadForm form, BindingResult result, @RequestParam("audio") MultipartFile file, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
			
			return "audio_upload";	
		}
		
		File audioFile = null;
		try {
			if (file != null && file.getSize() != 0 && file.getContentType().contains("audio/")) {
				String audioPath = uploadPath + CommonProperties.AUDIO_FILE_PATH;
				audioFile = fileUtils.getUniqueFile(audioPath, file.getContentType());
				
				try {
					file.transferTo(audioFile);
				} catch (Exception e) {
					e.printStackTrace();
					return "account_edit";
				}
				
				form.setDuration(audioUtils.getAudioDuration(audioFile, file.getContentType()));
				form.setFilePath(audioFile.getName());
			}
			
			Audio audio = audioService.buildAudioFromUploadForm(form);
			audioService.save(audio);
		} catch (Exception e) {
			e.printStackTrace();
			if (audioFile != null && audioFile.exists()) {
				audioFile.delete();
			}
			
			Audio audio = audioService.findByName(form.getName());
			if (audio != null) {
				audioService.delete(audio);
			}
		}
		
		Account account = (Account) model.getAttribute("user");
		Audio audio = audioService.findByName(form.getName());
		audioService.addAudioToAccount(audio, account);
		
		model.asMap().clear();
		return "redirect:/audios";
	}
	
	@RequestMapping(path = "/edit/{audioName}", method = RequestMethod.GET)
	public String showEditAudioPage(@PathVariable String audioName, Model model) {
		Account account = (Account) model.getAttribute("user");
		Audio audio = audioService.findByName(audioName);
		
		if (audio == null) {
			model.asMap().clear();
			return "redirect:/audios";
		}
		
		if (account.getRole().getName().equals("admin")) {
			model.addAttribute("displayDeleteButton", true);
		} else {
			model.addAttribute("displayDeleteButton", false);
		}
		
		model.addAttribute("audioName", audio.getName());
		
		Date creationDate = audio.getCreationDate();
		if (creationDate != null) {
			model.addAttribute("creationDate", new SimpleDateFormat("yyyy-MM").format(creationDate));
		}
		
		AudioGenre genre = audio.getGenre();
		if (genre != null) {
			model.addAttribute("genre", genre.getName());
		}
		
		AudioAlbum album = audio.getAlbum();
		if (album != null) {
			model.addAttribute("album", album.getName());
		}
		
		Set<Performer> performers = audio.getPerformers();
		if (performers != null && !performers.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (Performer performer : performers) {
				sb.append(performer.getAudioMaker().getName()).append(";");
			}
			sb.deleteCharAt(sb.length() - 1);
			
			model.addAttribute("performers", sb.toString());
		}
		
		Set<Author> authors = audio.getAuthors();
		if (authors != null && !authors.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (Author author : authors) {
				String authorName = author.getAudioMaker().getName();
				String authorRole = author.getRole().getName();
				
				sb.append(authorName);
				if (authorRole != null && !authorRole.isEmpty()) {
					sb.append(":").append(authorRole);
				}
				sb.append(";");
			}
			sb.deleteCharAt(sb.length() - 1);
			
			model.addAttribute("authors", sb.toString());
		}
		
		Set<AudioTag> tags = audio.getTags();
		if (tags != null && !tags.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (AudioTag tag : tags) {
				sb.append(tag.getName()).append(";");
			}
			sb.deleteCharAt(sb.length() - 1);
			
			model.addAttribute("tags", sb.toString());
		}
		
		return "audio_edit";
	}
	
	@RequestMapping(path = "/edit/{audioName}", method = RequestMethod.POST)
	public String handleEditAudio(@Valid AudioEditForm form, BindingResult result, @RequestParam("audio") MultipartFile file, @PathVariable String audioName, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
			
			return "audio_upload";	
		}
		
		Audio audio = audioService.findByName(audioName);
		File currentFile = new File(uploadPath + CommonProperties.AUDIO_FILE_PATH + audio.getFilePath());
		File audioFile = null;
		try {
			if (file != null && file.getSize() != 0 && file.getContentType().contains("audio/")) {
				String audioPath = uploadPath + CommonProperties.AUDIO_FILE_PATH;
				audioFile = fileUtils.getUniqueFile(audioPath, file.getContentType());
				
				try {
					file.transferTo(audioFile);
				} catch (Exception e) {
					e.printStackTrace();
					return "account_edit";
				}
				
				form.setDuration(audioUtils.getAudioDuration(audioFile, file.getContentType()));
				form.setFilePath(audioFile.getName());
			}
			
			audio = audioService.buildAudioFromEditForm(form);
			audioService.update(audio);
		} catch (Exception e) {
			e.printStackTrace();
			if (audioFile != null && audioFile.exists()) {
				audioFile.delete();
			}
		}
		
		if (currentFile != null && currentFile.exists()) {
			currentFile.delete();
		}

		model.asMap().clear();
		return "redirect:/audios/edit/" + audioName;
	}
	
	@RequestMapping(path = "/delete/{audioName}", method = RequestMethod.GET)
	public String handleDeleteAudio(@PathVariable String audioName, Model model) {
		Audio audio = audioService.findByName(audioName);
		Account account = (Account) model.getAttribute("user");
		
		if (audio != null) {
			audioService.deleteFromAccount(audio, account);
		}
		
		model.asMap().clear();
		return "redirect:/audios";
	}
	
	@RequestMapping(path = "/deleteFromServer/{audioName}", method = RequestMethod.GET)
	public String handleDeleteAudioFromServer(@PathVariable String audioName, Model model) {
		Audio audio = audioService.findByName(audioName);
		
		if (audio != null) {
			fileUtils.deleteFile(uploadPath + CommonProperties.AUDIO_FILE_PATH, audio.getFilePath());
			audioService.delete(audio);
		}
		
		model.asMap().clear();
		return "redirect:/audios";
	}
	
	@RequestMapping(path = "/addToAccount/{audioName}", method = RequestMethod.GET)
	public String handleAddAudioToAccount(@PathVariable String audioName, Model model) {
		Audio audio = audioService.findByName(audioName);
		Account account = (Account) model.getAttribute("user");
		
		if (audio != null) {
			if (!audioService.isAudioExistsOnAccount(audio, account)) {
				audioService.addAudioToAccount(audio, account);	
			}
		}
		
		model.asMap().clear();
		return "redirect:/audios";
	}
	
}
