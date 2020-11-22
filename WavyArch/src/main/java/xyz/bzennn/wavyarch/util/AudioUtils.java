package xyz.bzennn.wavyarch.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.springframework.stereotype.Component;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

/**
 * Audio utility class 
 *
 * @author bzennn
 * @version 1.0
 */
@Component
public class AudioUtils {

	public Integer getAudioDuration(File file, String contentType) throws UnsupportedAudioFileException, IOException {
		String[] contentTypeParts = contentType.split("/"); 
		
		Long duration = null;
		if (contentTypeParts[0].equals("audio")) {
			if (contentTypeParts[1].equals("mpeg")) {
				AudioFileFormat baseFileFormat = new MpegAudioFileReader().getAudioFileFormat(file);
				Map<?, ?> properties = baseFileFormat.properties();
				duration = (Long) properties.get("duration") / 1000000;
			} else if (contentTypeParts[1].equals("wav")) {
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
				AudioFormat format = audioInputStream.getFormat();
				long frames = audioInputStream.getFrameLength();
				duration = (long) ((frames+0.0) / format.getFrameRate()); 
			} else {
				throw new UnsupportedAudioFileException();
			}
		}
 	
	    return Integer.parseInt(duration.toString());
	}
	
}
