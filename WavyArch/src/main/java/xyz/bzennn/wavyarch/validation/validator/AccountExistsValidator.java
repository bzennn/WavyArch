package xyz.bzennn.wavyarch.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.bzennn.wavyarch.service.AccountService;
import xyz.bzennn.wavyarch.validation.annotation.AccountExists;


/**
 * Validator class for {@link AccountExists} 
 *
 * @author bzennn
 * @version 1.0
 */
@Component
public class AccountExistsValidator implements ConstraintValidator<AccountExists, String>{

	@Autowired
	private AccountService accountService;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return accountService.isLoginExists(value);
	}

}
