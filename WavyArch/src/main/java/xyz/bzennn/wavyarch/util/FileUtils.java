package xyz.bzennn.wavyarch.util;

import java.io.File;
import java.util.UUID;

/**
 * File utility class
 *
 * @author bzennn
 * @version 1.0
 */
public class FileUtils {
	
	public static File getUniqueFile(String prefix, String suffix, File directory) {
		return new File(directory, getUniqueFileName(prefix, suffix, directory));
	}
	
	public static String getUniqueFileName(String prefix, String suffix, File directory) {
		String fileName = getFileName(prefix, suffix);
		while (new File(directory, fileName).exists()) {
			fileName = getFileName(prefix, suffix);
		}
		
		return directory.getPath() + fileName;
	}
	
	public static String getFileName(String prefix, String suffix) {
		StringBuffer sb = new StringBuffer(prefix);
		sb.append("-")
			.append(UUID.randomUUID())
			.append(".")
			.append(suffix);
		
		return sb.toString();
	}
}
