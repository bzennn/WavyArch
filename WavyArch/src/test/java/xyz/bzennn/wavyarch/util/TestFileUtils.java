package xyz.bzennn.wavyarch.util;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class TestFileUtils {

	@Test
	public void fileNameForm() {
		String prefix = "prefix";
		String suffix = "suffix";
		
		String fileName = FileUtils.getFileName(prefix, suffix);
		assertTrue(fileName.contains(suffix));
		assertTrue(fileName.contains(prefix));
	}

	@Test
	public void fileNameUnique() {
		String prefix = "prefix";
		String suffix = "suffix";
		File directory = new File("");
		
		String file1 = FileUtils.getUniqueFileName(prefix, suffix, directory);
		String file2 = FileUtils.getUniqueFileName(prefix, suffix, directory);
		
		assertFalse(file1.equals(file2));
	}
	
	@Test
	public void fileForm() {
		String prefix = "prefix";
		String suffix = "suffix";
		File directory = new File("");
		
		File file = FileUtils.getUniqueFile(prefix, suffix, directory);
		String filePath = file.getPath();
		
		assertTrue(filePath.contains(directory.getPath()));
		assertTrue(filePath.contains(prefix));
		assertTrue(filePath.contains(suffix));
	}
}
