package xyz.bzennn.wavyarch.controller;

import java.io.File;

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
