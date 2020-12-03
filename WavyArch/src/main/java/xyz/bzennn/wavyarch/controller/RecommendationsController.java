package xyz.bzennn.wavyarch.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.service.AudioService;

/**
 * Controller that find same audios by audio name 
 *
 * @author bzennn
 * @version 1.0
 */
@Controller
@RequestMapping("/recommendations")
public class RecommendationsController {

	@Autowired
	AudioService audioService;
	
	@RequestMapping(path = "/{audioName}", method = RequestMethod.GET)
	public String showRecommendationsResult(@PathVariable String audioName, Model model) {
		if (audioName == null || audioName.isEmpty()) {
			model.asMap().clear();
			return "redirect:/";
		}
		
		Audio audio = audioService.findByName(audioName);
		if (audio == null) {
			model.asMap().clear();
			return "redirect:/";
		}
		
		Account account = (Account) model.getAttribute("user");
		List<Audio> recommendationsAudios = audioService.recommendations(audioName, 50);
		List<Audio> accountAudios = audioService.findByAccount(account);
		
		Set<String> audiosNotInAccount = new HashSet<String>(); 
		for (Audio audioTemp : recommendationsAudios) {
			if (!accountAudios.contains(audioTemp)) {
				audiosNotInAccount.add(audioTemp.getName());
			}
		}
		
		model.addAttribute("audiosNotInAccount", audiosNotInAccount);
		model.addAttribute("audioName", audioName);
		model.addAttribute("recommendationsAudios", recommendationsAudios);
		
		return "recommendations_audios";
	}
	
}
