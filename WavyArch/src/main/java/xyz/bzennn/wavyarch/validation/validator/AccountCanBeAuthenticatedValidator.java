package xyz.bzennn.wavyarch.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.bzennn.wavyarch.service.AccountService;
import xyz.bzennn.wavyarch.validation.annotation.AccountCanBeAuthenticated;

/**
 * Validator class for {@link AccountCanBeAuthenticated} 
 *
 * @author bzennn
 * @version 1.0
 */
@Component
public class AccountCanBeAuthenticatedValidator implements ConstraintValidator<AccountCanBeAuthenticated, Object>{
	
	@Autowired
	private AccountService accountService;
	
	private String loginField;
	private String passwordField;
	
	@Override
	public void initialize(AccountCanBeAuthenticated constraintAnnotation) {
		this.loginField = constraintAnnotation.login();
		this.passwordField = constraintAnnotation.password();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		String loginValue = (String) new BeanWrapperImpl(value).getPropertyValue(loginField);
		String passwordValue = (String) new BeanWrapperImpl(value).getPropertyValue(passwordField);
		
		return accountService.canAuthenticate(loginValue, passwordValue);
	}

}
