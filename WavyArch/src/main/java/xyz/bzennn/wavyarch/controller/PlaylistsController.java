package xyz.bzennn.wavyarch.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import xyz.bzennn.wavyarch.data.model.AccountPlaylist;
import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.data.model.PlaylistAudio;
import xyz.bzennn.wavyarch.form.PlaylistAddAudioForm;
import xyz.bzennn.wavyarch.form.PlaylistCreateForm;
import xyz.bzennn.wavyarch.form.PlaylistEditForm;
import xyz.bzennn.wavyarch.service.AudioFilterService;
import xyz.bzennn.wavyarch.service.AudioService;
import xyz.bzennn.wavyarch.service.AudioSortingService;
import xyz.bzennn.wavyarch.service.PlaylistService;
import xyz.bzennn.wavyarch.service.PlaylistSortingService;
import xyz.bzennn.wavyarch.util.FileUtils;
import xyz.bzennn.wavyarch.util.ImageUtils;
import xyz.bzennn.wavyarch.util.StringUtils;

@Controller
@RequestMapping("/playlists")
@PropertySource("classpath:upload.properties")
public class PlaylistsController {

	@Value("${upload.file_path}")
	private String uploadPath;
	
	@Autowired
	FileUtils fileUtils;
	
	@Autowired
	StringUtils stringUtils;
	
	@Autowired
	ImageUtils imageUtils;
	
	@Autowired
	PlaylistService playlistService;
	
	@Autowired
	AudioService audioService;
	
	@Autowired
	AudioSortingService audioSortingService;
	
	@Autowired
	PlaylistSortingService playlistSortingService;
	
	@Autowired
	private AudioFilterService audioFilterService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showPlaylistsPage(@RequestParam Map<String, String> params, Model model) {
		Account account = (Account) model.getAttribute("user");
		List<AccountPlaylist> playlists = playlistService.findByAccount(account);
		
		playlistSortingService.sort(playlists, params);
		
		model.addAttribute("accountPlaylists", playlists);
		
		return "playlists";
	}
	
	@RequestMapping(path = "/create", method = RequestMethod.GET)
	public String showPlaylistCreatePage(Model model) {
		return "playlist_create";
	}
	
	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public String handlePlaylistCreatePage(@Valid PlaylistCreateForm form, BindingResult result, @RequestParam("cover") MultipartFile file, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
			
			return "playlist_create";	
		}
		
		if (file != null && file.getSize() != 0 && file.getContentType().contains("image/")) {
			String coverPath = uploadPath + CommonProperties.PLAYLIST_COVER_FILE_PATH;
			File coverFile = fileUtils.getUniqueFile(coverPath, file.getContentType());
			
			try {
				file.transferTo(coverFile);
			} catch (Exception e) {
				e.printStackTrace();
				return "playlist_edit";
			}
			
			try {
				imageUtils.cropImageToSquare(coverFile);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			form.setImagePath(coverFile.getName());
		}
		
		AccountPlaylist playlist = playlistService.buildPlaylistFromCreateForm(form);
		playlistService.save(playlist);
		
		model.asMap().clear();
		return "redirect:/playlists";
	}
	
	@RequestMapping(path = "/playlist/{playlistName:.+}", method = RequestMethod.GET)
	public String showPlaylistPage(@PathVariable String playlistName, @RequestParam Map<String, String> params, Model model) {
		if (playlistName == null) {
			model.asMap().clear();
			return "redirect:/playlists";
		}
		
		Account account = (Account) model.getAttribute("user");
		AccountPlaylist playlist = playlistService.findByNameAndLogin(playlistName, account.getLogin());
		if (playlist == null) {
			model.asMap().clear();
			return "redirect:/playlists";
		}
		
		List<Audio> playlistAudios = new ArrayList<Audio>();
		for (PlaylistAudio playlistAudio : playlist.getAudios()) {
			if (playlistAudio != null) {
				playlistAudios.add(playlistAudio.getAudio());
			}
		}
		
		audioFilterService.filter(playlistAudios, params);
		audioSortingService.sort(playlistAudios, params);
		
		model.addAttribute("playlist", playlist);
		model.addAttribute("playlistAudios", playlistAudios);
		model.addAttribute("playerAvailable", true);
		
		return "playlist_audios";
	}
	
	@RequestMapping(path = "/playlist/edit/{playlistName:.+}", method = RequestMethod.GET)
	public String showEditPlaylistPage(@PathVariable String playlistName, Model model) {
		if (playlistName == null) {
			model.asMap().clear();
			return "redirect:/playlists";
		}
		
		Account account = (Account) model.getAttribute("user");
		AccountPlaylist playlist = playlistService.findByNameAndLogin(playlistName, account.getLogin());
		if (playlist == null) {
			model.asMap().clear();
			return "redirect:/playlists";
		}
		
		model.addAttribute("playlist", playlist);
		
		return "playlist_edit";
	}
	
	@RequestMapping(path = "/playlist/edit/{playlistName:.+}", method = RequestMethod.POST)
	public String handleEditPlaylist(@Valid PlaylistEditForm form, BindingResult result, @RequestParam("cover") MultipartFile file, @PathVariable String playlistName, Model model) throws UnsupportedEncodingException {
		if (result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
			
			return "playlist_edit";	
		}
		
		Account account = (Account) model.getAttribute("user");
		AccountPlaylist playlist = playlistService.findByNameAndLogin(playlistName, account.getLogin());
		
		if (file != null && file.getSize() != 0 && file.getContentType().contains("image/")) {
			String coverPath = uploadPath + CommonProperties.PLAYLIST_COVER_FILE_PATH;
			File coverFile = fileUtils.getUniqueFile(coverPath, file.getContentType());
			
			try {
				file.transferTo(coverFile);
			} catch (Exception e) {
				e.printStackTrace();
				return "playlist_edit";
			}
			
			String currentCoverPath = playlist.getImagePath();
			if (currentCoverPath != null && !currentCoverPath.isEmpty()) {
				fileUtils.deleteFile(uploadPath + CommonProperties.PLAYLIST_COVER_FILE_PATH, currentCoverPath);
			}
			
			try {
				imageUtils.cropImageToSquare(coverFile);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			form.setImagePath(coverFile.getName());
		}
		
		playlist = playlistService.buildPlaylistFromEditForm(form);
		playlistService.update(playlist);
		
		String encodedPlaylistName = playlistName;
		if (!stringUtils.isAsciiString(playlistName)) {
			encodedPlaylistName = stringUtils.encodeToUtf8(playlistName);
		}
		
		model.asMap().clear();
		return "redirect:/playlists/playlist/edit/" + encodedPlaylistName;
	}
	
	@RequestMapping(path = "/playlist/add/{playlistName:.+}", method = RequestMethod.GET)
	public String showAddAudioPage(@PathVariable String playlistName, Model model) {
		if (playlistName == null) {
			model.asMap().clear();
			return "redirect:/playlists";
		}
		
		Account account = (Account) model.getAttribute("user");
		AccountPlaylist playlist = playlistService.findByNameAndLogin(playlistName, account.getLogin());
		if (playlist == null) {
			model.asMap().clear();
			return "redirect:/playlists";
		}
		
		model.addAttribute("playlistName", playlist.getName());
		
		return "playlist_add_audio";
	}
	
	@RequestMapping(path = "/playlist/add/{playlistNameUri:.+}", method = RequestMethod.POST)
	public String handleAddAudio(@Valid PlaylistAddAudioForm form, BindingResult result, @PathVariable String playlistNameUri, Model model) throws UnsupportedEncodingException {
		if (result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
			model.addAttribute("playlistName", playlistNameUri);
			
			return "playlist_add_audio";	
		}
		
		Account account = (Account) model.getAttribute("user");
		AccountPlaylist playlist = playlistService.findByNameAndLogin(playlistNameUri, account.getLogin());
		
		Audio audio = audioService.findByName(form.getAudioName());
		
		playlistService.addAudioToPlaylist(audio, playlist);
		
		String encodedPlaylistName = playlistNameUri;
		if (!stringUtils.isAsciiString(playlistNameUri)) {
			encodedPlaylistName = stringUtils.encodeToUtf8(playlistNameUri);
		}
		
		model.asMap().clear();
		return "redirect:/playlists/playlist/" + encodedPlaylistName;
	}
	
	@RequestMapping(path = "/playlist/removeAudio/{playlistName:.+}/audio/{audioName:.+}", method = RequestMethod.GET)
	public String handleDeleteAudioFromPlaylist(@PathVariable String playlistName, @PathVariable String audioName, Model model) throws UnsupportedEncodingException {
		if (playlistName == null) {
			model.asMap().clear();
			return "redirect:/playlists";
		}
		
		Account account = (Account) model.getAttribute("user");
		AccountPlaylist playlist = playlistService.findByNameAndLogin(playlistName, account.getLogin());
		if (playlist == null) {
			model.asMap().clear();
			return "redirect:/playlists";
		}
		
		Audio audio = audioService.findByName(audioName);
		if (audio == null) {
			model.asMap().clear();
			return "redirect:/playlists";
		}
		
		playlistService.removeAudioFromPlaylist(audio, playlist);
		
		String encodedPlaylistName = playlistName;
		if (!stringUtils.isAsciiString(playlistName)) {
			encodedPlaylistName = stringUtils.encodeToUtf8(playlistName);
		}
		
		model.asMap().clear();
		return "redirect:/playlists/playlist/" + encodedPlaylistName;
	}
	
	@RequestMapping(path = "/playlist/delete/{playlistName:.+}", method = RequestMethod.GET)
	public String handleDeletePlaylist(@PathVariable String playlistName, Model model) {
		if (playlistName == null) {
			model.asMap().clear();
			return "redirect:/playlists";
		}
		
		Account account = (Account) model.getAttribute("user");
		AccountPlaylist playlist = playlistService.findByNameAndLogin(playlistName, account.getLogin());
		if (playlist == null) {
			model.asMap().clear();
			return "redirect:/playlists";
		}
		
		playlistService.delete(playlist);
		
		model.asMap().clear();
		return "redirect:/playlists";
	}
	
}
