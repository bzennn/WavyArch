package xyz.bzennn.wavyarch.util;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Component;

/**
 * File utility class
 *
 * @author bzennn
 * @version 1.0
 */
@Component
public class FileUtils {
	
	public File getUniqueFile(String directoryPath, String contentType) {
		File directory = new File(directoryPath);
		
		if (!directory.exists()) {
			directory.mkdirs();
		}
		
		if (!directory.isDirectory()) {
			directory = new File("");
		}
		
		return getUniqueFile(directory, contentType);
	}
	
	public File getUniqueFile( File directory, String contentType) {
		return new File(directory, getUniqueFileName(directory, contentType));
	}
	
	public String getUniqueFileName(File directory, String contentType) {
		String fileName = getFileName(contentType);
		while (new File(directory, fileName).exists()) {
			fileName = getFileName(contentType);
		}
		
		return fileName;
	}
	
	public String getFileName(String contentType) {
		String[] type = contentType.split("/");
		StringBuffer sb = new StringBuffer(type[0]);
		sb.append("-")
			.append(type[1])
			.append("_")
			.append(UUID.randomUUID());
		
		return sb.toString();
	}
	
	public void deleteFile(String directory, String fileName) {
		File file = new File(directory + fileName);
		if (file.exists() && file.isFile()) {
			file.delete();
		}
	}
}
