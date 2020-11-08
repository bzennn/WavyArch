package xyz.bzennn.wavyarch.validation.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import xyz.bzennn.wavyarch.validation.validator.AccountExistsValidator;


/**
 * The annotated account login must exists in database.
 *
 * @author bzennn
 * @version 1.0
 */
@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
@Constraint(validatedBy = AccountExistsValidator.class)
public @interface AccountExists {
	String message() default "Account with this login does not exists!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
