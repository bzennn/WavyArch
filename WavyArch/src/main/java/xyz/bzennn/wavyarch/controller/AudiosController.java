package xyz.bzennn.wavyarch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/audios")
public class AudiosController {

	@RequestMapping(method = RequestMethod.GET)
	public String showAccountAudiosPage() {
		return "account_audios";
	}

	@RequestMapping(path = "/upload", method = RequestMethod.GET)
	public String showUploadAudioPage() {
		return "upload_audio";
	}
	
	@RequestMapping(path = "/upload", method = RequestMethod.POST)
	public String handleUploadAudio() {
		return "redirect:/audios";
	}
	
}
