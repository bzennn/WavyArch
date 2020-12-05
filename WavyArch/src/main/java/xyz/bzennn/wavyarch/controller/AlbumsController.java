package xyz.bzennn.wavyarch.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import xyz.bzennn.wavyarch.data.model.AudioAlbum;
import xyz.bzennn.wavyarch.data.model.AudioMaker;
import xyz.bzennn.wavyarch.form.AlbumEditForm;
import xyz.bzennn.wavyarch.service.AlbumService;
import xyz.bzennn.wavyarch.service.AudioService;
import xyz.bzennn.wavyarch.service.AudioSortingService;
import xyz.bzennn.wavyarch.util.FileUtils;
import xyz.bzennn.wavyarch.util.ImageUtils;
import xyz.bzennn.wavyarch.util.StringUtils;

@Controller
@RequestMapping("/albums")
@PropertySource("classpath:upload.properties")
public class AlbumsController {

	@Value("${upload.file_path}")
	private String uploadPath;
	
	@Autowired
	FileUtils fileUtils;
	
	@Autowired
	StringUtils stringUtils;
	
	@Autowired
	ImageUtils imageUtils;
	
	@Autowired
	AlbumService albumService;
	
	@Autowired
	AudioService audioService;
	
	@Autowired
	AudioSortingService sortingService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showAlbumsPage(Model model) {
		Account account = (Account) model.getAttribute("user");
		List<AudioAlbum> albums = albumService.findByAccount(account);
		
		model.addAttribute("accountAlbums", albums);
		
		return "albums";
	}

	@RequestMapping(path = "/album/{albumName}", method = RequestMethod.GET)
	public String showAlbumPage(@PathVariable String albumName, @RequestParam Map<String, String> params, Model model) {
		if (albumName == null) {
			model.asMap().clear();
			return "redirect:/albums";
		}
		
		AudioAlbum album = albumService.findByName(albumName);
		if (album == null) {
			model.asMap().clear();
			return "redirect:/albums";
		}
		
		Account account = (Account) model.getAttribute("user");
		
		Set<String> audiosNotInAccount = new HashSet<String>(); 
		List<Audio> albumAudios = new ArrayList<Audio>(album.getAudios());
		List<Audio> accountAudios = audioService.findByAccount(account);
		for (Audio audio : albumAudios) {
			if (!accountAudios.contains(audio)) {
				audiosNotInAccount.add(audio.getName());
			}
		}
		
		sortingService.sort(albumAudios, params);
		
		model.addAttribute("audiosNotInAccount", audiosNotInAccount);
		model.addAttribute("album", album);
		model.addAttribute("albumAudios", albumAudios);
		
		return "album_audios";
	}
	
	@RequestMapping(path = "/album/edit/{albumName}", method = RequestMethod.GET)
	public String showEditAlbumPage(@PathVariable String albumName, Model model) {
		if (albumName == null) {
			model.asMap().clear();
			return "redirect:/albums";
		}
		
		AudioAlbum album = albumService.findByName(albumName);
		if (album == null) {
			model.asMap().clear();
			return "redirect:/albums";
		}
		
		Date creationDate = album.getCreationDate();
		if (creationDate != null) {
			model.addAttribute("creationDate", new SimpleDateFormat("yyyy-MM").format(creationDate));
		}
		
		AudioMaker audioMaker = album.getAudioMaker();
		if (audioMaker != null) {
			model.addAttribute("author", audioMaker.getName());
		}
		
		model.addAttribute("album", album);
		
		return "album_edit";
	}
	
	@RequestMapping(path = "/album/edit/{albumName}", method = RequestMethod.POST)
	public String handleEditAlbum(@Valid AlbumEditForm form, BindingResult result, @RequestParam("cover") MultipartFile file, @PathVariable String albumName, Model model) throws UnsupportedEncodingException {
		if (result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
			
			return "album_edit";	
		}
		
		AudioAlbum album = albumService.findByName(albumName);
		
		if (file != null && file.getSize() != 0 && file.getContentType().contains("image/")) {
			String coverPath = uploadPath + CommonProperties.ALBUM_COVER_FILE_PATH;
			File coverFile = fileUtils.getUniqueFile(coverPath, file.getContentType());
			
			try {
				file.transferTo(coverFile);
			} catch (Exception e) {
				e.printStackTrace();
				return "account_edit";
			}
			
			String currentCoverPath = album.getImagePath();
			if (currentCoverPath != null && !currentCoverPath.isEmpty()) {
				fileUtils.deleteFile(uploadPath + CommonProperties.ALBUM_COVER_FILE_PATH, currentCoverPath);
			}
			
			try {
				imageUtils.cropImageToSquare(coverFile);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			form.setImagePath(coverFile.getName());
		}
		
		album = albumService.buildAlbumFromEditForm(form);
		albumService.update(album);
		
		String encodedAlbumName = albumName;
		if (!stringUtils.isAsciiString(albumName)) {
			encodedAlbumName = stringUtils.encodeToUtf8(albumName);
		}
		
		model.asMap().clear();
		return "redirect:/albums/album/edit/" + encodedAlbumName;
	}
	
}
