package xyz.bzennn.wavyarch.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

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
	private static final float IMAGE_QUALITY = 0.5f;

	public File cropImageToSquare(File imageFile) throws Exception {
		File directory = imageFile.getParentFile();

		BufferedImage image = ImageIO.read(imageFile);
		int width = image.getWidth();
		int height = image.getHeight();

		File tmp = File.createTempFile("image_crop", fileUtils.getUniqueFileName(directory, "tmp/file"));
		if (width < height) {
			BufferedImage dst = new BufferedImage(width, width, BufferedImage.TYPE_INT_BGR);
			int pos = (height - width) / 2;
			dst.getGraphics().drawImage(image, 0, 0, width, width, 0, pos, width, pos + width, null);
			if (width > IMAGE_SIDE_SIZE) {
				dst = resizeImage(dst, IMAGE_SIDE_SIZE, IMAGE_SIDE_SIZE);
			}

			//ImageIO.write(dst, "png", tmp);
			writeCompressedImage(dst, tmp, IMAGE_QUALITY);
		} else if (width > height) {
			BufferedImage dst = new BufferedImage(height, height, BufferedImage.TYPE_INT_BGR);
			int pos = (width - height) / 2;
			dst.getGraphics().drawImage(image, 0, 0, height, height, pos, 0, pos + height, height, null);
			if (height > IMAGE_SIDE_SIZE) {
				dst = resizeImage(dst, IMAGE_SIDE_SIZE, IMAGE_SIDE_SIZE);
			}

			//ImageIO.write(dst, "png", tmp);
			writeCompressedImage(dst, tmp, IMAGE_QUALITY);
		} else {
			BufferedImage dst = new BufferedImage(height, height, BufferedImage.TYPE_INT_BGR);
			dst.getGraphics().drawImage(image, 0, 0, width, width, 0, 0, width, width, null);
			if (width > IMAGE_SIDE_SIZE) {
				dst = resizeImage(dst, IMAGE_SIDE_SIZE, IMAGE_SIDE_SIZE);
			}

			//ImageIO.write(dst, "png", tmp);
			writeCompressedImage(dst, tmp, IMAGE_QUALITY);
		}

		if (imageFile.exists()) {
			imageFile.delete();
		}

		tmp.renameTo(imageFile);

		return imageFile;
	}

	public BufferedImage resizeImage(BufferedImage img, int newW, int newH) {
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_BGR);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
	}

	public void writeCompressedImage(BufferedImage img, File imageFile, float quality) throws IOException {
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
		ImageWriter writer = (ImageWriter) writers.next();
		
		OutputStream os = new FileOutputStream(imageFile);
		ImageOutputStream ios = ImageIO.createImageOutputStream(os);
		writer.setOutput(ios);
		
		ImageWriteParam param = writer.getDefaultWriteParam();
		
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(quality);
		writer.write(null, new IIOImage(img, null, null), param);
		
		os.close();
		ios.close();
		writer.dispose();
	}

}
