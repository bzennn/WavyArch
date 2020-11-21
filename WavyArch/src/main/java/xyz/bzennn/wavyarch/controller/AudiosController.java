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
import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.form.AudioUploadForm;
import xyz.bzennn.wavyarch.service.AudioService;
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
	private AudioService audioService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showAccountAudiosPage() {
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
		
		if (file != null && file.getSize() != 0 && file.getContentType().contains("audio/")) {
			String audioPath = uploadPath + CommonProperties.AUDIO_FILE_PATH;
			String prefix = "audio";
			File audioFile = fileUtils.getUniqueFile(prefix, audioPath);
			
			try {
				file.transferTo(audioFile);
			} catch (Exception e) {
				e.printStackTrace();
				return "account_edit";
			}
			
			form.setFilePath(audioFile.getName());
		}
		
		Audio audio = audioService.buildAudioFromUploadForm(form);
		audioService.save(audio);
		
		model.asMap().clear();
		return "redirect:/audios";
	}
	
	@RequestMapping(path = "/delete/{audioName}", method = RequestMethod.GET)
	public String handleDeleteAudio(@PathVariable String audioName, Model model) {
		Audio audio = audioService.findByName(audioName);
		
		if (audio != null) {
			audioService.delete(audio);
		}
		
		model.asMap().clear();
		return "redirect:/audios";
	}
	
}
