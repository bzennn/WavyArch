package xyz.bzennn.wavyarch.util;

import java.awt.Graphics2D;
import java.awt.Image;
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
	
	private static final int IMAGE_SIDE_SIZE = 500;
	
	public File cropImageToSquare(File imageFile) throws Exception {
		File directory = imageFile.getParentFile();
		
		BufferedImage image = ImageIO.read(imageFile);
		int width = image.getWidth();
		int height = image.getHeight();
		
		File tmp = File.createTempFile("image_crop", fileUtils.getUniqueFileName(directory, "tmp/file"));
		if (width < height) {
			BufferedImage dst = new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB);
			int pos = (height - width) / 2;
			dst.getGraphics().drawImage(image, 0, 0, width, width, 0, pos, width, pos + width, null);
			if (width > IMAGE_SIDE_SIZE) {
				dst = resizeImage(dst, IMAGE_SIDE_SIZE, IMAGE_SIDE_SIZE);
			}
			
			ImageIO.write(dst, "png", tmp);
		} else if (width > height){
			BufferedImage dst = new BufferedImage(height, height, BufferedImage.TYPE_INT_ARGB);
			int pos = (width - height) / 2;
			dst.getGraphics().drawImage(image, 0, 0, height, height, pos, 0, pos + height, height, null);
			if (height > IMAGE_SIDE_SIZE) {
				dst = resizeImage(dst, IMAGE_SIDE_SIZE, IMAGE_SIDE_SIZE);
			}
			
			ImageIO.write(dst, "png", tmp);
		} else {
			BufferedImage dst = new BufferedImage(height, height, BufferedImage.TYPE_INT_ARGB);
			dst.getGraphics().drawImage(image, 0, 0, width, width, 0, 0, width, width, null);
			if (width > IMAGE_SIDE_SIZE) {
				dst = resizeImage(dst, IMAGE_SIDE_SIZE, IMAGE_SIDE_SIZE);
			}
			
			ImageIO.write(dst, "png", tmp);
		}
		
		if (imageFile.exists()) {
			imageFile.delete();
		}
		
		tmp.renameTo(imageFile);
		
		return imageFile;
	}
	
	public static BufferedImage resizeImage(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}  
	
}
