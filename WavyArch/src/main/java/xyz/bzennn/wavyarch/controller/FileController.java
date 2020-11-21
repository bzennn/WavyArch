package xyz.bzennn.wavyarch.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URLConnection;

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
	public HttpEntity<byte[]> getAvatarUrl(@PathVariable String avatarName) throws NotFoundException {
		File imgFile = new File(uploadPath + CommonProperties.AVATAR_FILE_PATH, avatarName);
		byte[] img;
		String[] contentType;
		try {
			img = FileUtils.readFileToByteArray(imgFile);
			String contentTypeFull = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(img));
			contentType = contentTypeFull.split("/");
		} catch (Exception e) {
			throw new NotFoundException("File not found on server!", e);
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType(contentType[0], contentType[1]));
		headers.setContentLength(img.length);
		return new HttpEntity<byte[]>(img, headers);
	}

}
