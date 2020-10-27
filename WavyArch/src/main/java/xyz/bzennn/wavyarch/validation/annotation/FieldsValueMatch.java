package xyz.bzennn.wavyarch.validation.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import xyz.bzennn.wavyarch.validation.validator.FieldsValueMatchValidator;


/**
 * The annotated types class variables field and fieldMatch values must be equal 
 *
 * @author bzennn
 * @version 1.0
 */
@Documented
@Retention(RUNTIME)
@Target({ ElementType.TYPE })
@Constraint(validatedBy = FieldsValueMatchValidator.class)
public @interface FieldsValueMatch {
	
	String message() default "Fields do not match!";
	
	Class<?>[] groups() default { };
	
	Class<? extends Payload>[] payload() default { };
	
	String field();
	
	String fieldMatch();
	
}	
