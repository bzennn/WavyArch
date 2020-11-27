package xyz.bzennn.wavyarch.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.bzennn.wavyarch.service.AudioService;
import xyz.bzennn.wavyarch.validation.annotation.AudioExists;


/**
 * Validator class for {@link AudioExists} 
 *
 * @author bzennn
 * @version 1.0
 */
@Component
public class AudioExistsValidator implements ConstraintValidator<AudioExists, String> {
	
	@Autowired
	AudioService audioService;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return audioService.isAudioExists(value);
	}

}
