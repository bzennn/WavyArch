package xyz.bzennn.wavyarch.util;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class TestFileUtils {

	private FileUtils fileUtils = new FileUtils();
	
	@Test
	public void fileNameForm() {
		String prefix = "prefix";
		
		String fileName = fileUtils.getFileName(prefix);
		assertTrue(fileName.contains(prefix));
	}

	@Test
	public void fileNameUnique() {
		String prefix = "prefix";
		File directory = new File("");
		
		String file1 = fileUtils.getUniqueFileName(prefix, directory);
		String file2 = fileUtils.getUniqueFileName(prefix, directory);
		
		assertFalse(file1.equals(file2));
	}
	
	@Test
	public void fileForm() {
		String prefix = "prefix";
		File directory = new File("");
		
		File file = fileUtils.getUniqueFile(prefix, directory);
		String filePath = file.getPath();
		
		assertTrue(filePath.contains(directory.getPath()));
		assertTrue(filePath.contains(prefix));
	}
}
