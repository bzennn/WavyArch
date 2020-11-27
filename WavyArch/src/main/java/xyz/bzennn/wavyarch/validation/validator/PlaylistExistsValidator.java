package xyz.bzennn.wavyarch.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.bzennn.wavyarch.data.model.AccountPlaylist;
import xyz.bzennn.wavyarch.service.PlaylistService;
import xyz.bzennn.wavyarch.validation.annotation.AudioUnique;
import xyz.bzennn.wavyarch.validation.annotation.PlaylistExists;


/**
 * Validator class for {@link AudioUnique} 
 *
 * @author bzennn
 * @version 1.0
 */
@Component
public class PlaylistExistsValidator implements ConstraintValidator<PlaylistExists, Object> {
	
	@Autowired
	PlaylistService playlistService;
	
	private String loginField;
	private String nameField;
	
	@Override
	public void initialize(PlaylistExists constraintAnnotation) {
		this.loginField = constraintAnnotation.login();
		this.nameField = constraintAnnotation.name();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		String loginValue = (String) new BeanWrapperImpl(value).getPropertyValue(loginField);
		String nameValue = (String) new BeanWrapperImpl(value).getPropertyValue(nameField);
		
		AccountPlaylist playlist = playlistService.findByNameAndLogin(nameValue, loginValue);
		
		if (playlist != null) {
			return true;
		} else {
			return false;
		}
	}

}
