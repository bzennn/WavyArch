package xyz.bzennn.wavyarch.util;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Utility class for images manipulation 
 *
 * @author bzennn
 * @version 1.0
 */
@Component
public class ImageUtils {

	@Autowired
	FileUtils fileUtils;
	
	public File cropImageToSquare(File imageFile) throws Exception {
		File directory = imageFile.getParentFile();
		
		BufferedImage image = ImageIO.read(imageFile);
		int width = image.getWidth();
		int height = image.getHeight();
		
		if (width == height) {
			return imageFile;
		}
		
		File tmp = File.createTempFile("image_crop", fileUtils.getUniqueFileName(directory, "tmp/file"));
		if (width < height) {
			BufferedImage dst = new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB);
			int pos = (height - width) / 2;
			dst.getGraphics().drawImage(image, 0, 0, width, width, 0, pos, width, pos + width, null);
			
			ImageIO.write(dst, "png", tmp);
		} else {
			BufferedImage dst = new BufferedImage(height, height, BufferedImage.TYPE_INT_ARGB);
			int pos = (width - height) / 2;
			dst.getGraphics().drawImage(image, 0, 0, height, height, pos, 0, pos + height, height, null);
			
			ImageIO.write(dst, "png", tmp);
		}
		
		if (imageFile.exists()) {
			imageFile.delete();
		}
		
		tmp.renameTo(imageFile);
		
		return imageFile;
	}
	
}
