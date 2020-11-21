package xyz.bzennn.wavyarch.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.bzennn.wavyarch.service.AudioService;
import xyz.bzennn.wavyarch.validation.annotation.AudioUnique;


/**
 * Validator class for {@link AudioUnique} 
 *
 * @author bzennn
 * @version 1.0
 */
@Component
public class AudioUniqueValidator implements ConstraintValidator<AudioUnique, String> {
	
	@Autowired
	AudioService audioService;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return !audioService.isAudioExists(value);
	}

}
