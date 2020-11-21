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
	
	public File getUniqueFile(String prefix, String directoryPath) {
		File directory = new File(directoryPath);
		
		if (!directory.exists()) {
			directory.mkdirs();
		}
		
		if (!directory.isDirectory()) {
			directory = new File("");
		}
		
		return getUniqueFile(prefix, directory);
	}
	
	public File getUniqueFile(String prefix, File directory) {
		return new File(directory, getUniqueFileName(prefix, directory));
	}
	
	public String getUniqueFileName(String prefix, File directory) {
		String fileName = getFileName(prefix);
		while (new File(directory, fileName).exists()) {
			fileName = getFileName(prefix);
		}
		
		return fileName;
	}
	
	public String getFileName(String prefix) {
		StringBuffer sb = new StringBuffer(prefix);
		sb.append("-")
			.append(UUID.randomUUID());
		
		return sb.toString();
	}
}
