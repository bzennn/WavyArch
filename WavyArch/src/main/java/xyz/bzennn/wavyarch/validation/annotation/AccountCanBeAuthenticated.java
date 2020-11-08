package xyz.bzennn.wavyarch.validation.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import xyz.bzennn.wavyarch.validation.validator.AccountCanBeAuthenticatedValidator;

/**
 * The annotated types class variable password must be equal to password field in database with passed login 
 *
 * @author bzennn
 * @version 1.0
 */
@Documented
@Retention(RUNTIME)
@Target({ ElementType.TYPE })
@Constraint(validatedBy = AccountCanBeAuthenticatedValidator.class)
public @interface AccountCanBeAuthenticated {

	String message() default "Wrong password!";
	
	Class<?>[] groups() default { };
	
	Class<? extends Payload>[] payload() default { };
	
	String login();
	
	String password();
	
}
