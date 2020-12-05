package xyz.bzennn.wavyarch.controller;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.bzennn.wavyarch.config.CommonProperties;
import xyz.bzennn.wavyarch.exception.NotFoundException;

/**
 * Controller that maps file system files to files/* mapping
 *
 * @author bzennn
 * @version 1.0
 */
@Controller
@RequestMapping("files/")
@PropertySource("classpath:upload.properties")
public class FileController {

	@Value("${upload.file_path}")
	private String uploadPath;
	
	@RequestMapping(path = "images/{avatarName}", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> getAvatar(@PathVariable String avatarName) throws NotFoundException {
		File imgFile = new File(uploadPath + CommonProperties.AVATAR_FILE_PATH, avatarName);
		
		return createFileResponseBody(imgFile);
	}
	
	@RequestMapping(path = "audios/{audioName}", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> getAudio(@PathVariable String audioName) throws NotFoundException {
		File audioFile = new File(uploadPath + CommonProperties.AUDIO_FILE_PATH, audioName);
		
		return createFileResponseBody(audioFile);
	}
	
	@RequestMapping(path = "albums/{albumCoverName}", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> getAlbumCover(@PathVariable String albumCoverName) throws NotFoundException {
		File albumCoverFile = new File(uploadPath + CommonProperties.ALBUM_COVER_FILE_PATH, albumCoverName);
		
		return createFileResponseBody(albumCoverFile);
	}
	
	@RequestMapping(path = "performers/{performerImageName}", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> getPerformerImage(@PathVariable String performerImageName) throws NotFoundException {
		File performerImageFile = new File(uploadPath + CommonProperties.AUTHOR_COVER_FILE_PATH, performerImageName);
		
		return createFileResponseBody(performerImageFile);
	}
	
	@RequestMapping(path = "playlists/{playlistCoverName}", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> getPlaylistCover(@PathVariable String playlistCoverName) throws NotFoundException {
		File playlistCoverFile = new File(uploadPath + CommonProperties.PLAYLIST_COVER_FILE_PATH, playlistCoverName);
		
		return createFileResponseBody(playlistCoverFile);
	}
	
	private HttpEntity<byte[]> createFileResponseBody(File file) throws NotFoundException {
		byte[] fileBytes;
		String[] contentType;
		try {
			fileBytes = FileUtils.readFileToByteArray(file);
			contentType = file.getName().split("_")[0].split("-");
		} catch (Exception e) {
			throw new NotFoundException("File not found on server!", e);
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType(contentType[0], contentType[1]));
		headers.setContentLength(fileBytes.length);
		if (contentType[0].equals("audio")) {
			headers.add("accept-ranges", "bytes");
			headers.add("Content-Range", "bytes 0-" + (fileBytes.length - 1) + "/" + fileBytes.length);
		}
		return new HttpEntity<byte[]>(fileBytes, headers);
	}

}
