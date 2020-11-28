package xyz.bzennn.wavyarch.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.service.AudioService;
import xyz.bzennn.wavyarch.service.SearchService;

/**
 * Controller for library searching 
 *
 * @author bzennn
 * @version 1.0
 */
@Controller
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@Autowired
	private AudioService audioService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showSearchResults(@RequestParam(defaultValue = "") String request, @RequestParam(defaultValue = "") String category, Model model) {
		if (request == null || request.isEmpty()) {
			model.asMap().clear();
			return "redirect:/";
		}
		
		Account account = (Account) model.getAttribute("user");
		List<Audio> foundAudios = searchByCategory(request, category);
		List<Audio> accountAudios = audioService.findByAccount(account);
		
		Set<String> audiosNotInAccount = new HashSet<String>(); 
		for (Audio audio : foundAudios) {
			if (!accountAudios.contains(audio)) {
				audiosNotInAccount.add(audio.getName());
			}
		}
		
		model.addAttribute("audiosNotInAccount", audiosNotInAccount);
		model.addAttribute("searchRequest", request);
		model.addAttribute("searchAudios", foundAudios);
		
		return "search_audios";
	}
	
	private List<Audio> searchByCategory(String request, String category) {
		switch (category) {
			case "audioMakers":
				return searchService.searchByAudioMakerName(request);
			case "genres":
				return searchService.searchByGenreName(request);
			case "albums":
				return searchService.searchByAlbumName(request);
			case "tags":
				return searchService.searchByTagName(request);
			default:
				return searchService.searchAll(request);
		}
	}
	
}
