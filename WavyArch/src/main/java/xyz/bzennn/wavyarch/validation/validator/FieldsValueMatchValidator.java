package xyz.bzennn.wavyarch.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import xyz.bzennn.wavyarch.validation.annotation.FieldsValueMatch;


/**
 * Validator class for {@link FieldsValueMatch} 
 *
 * @author bzennn
 * @version 1.0
 */
@Component
public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch, Object> {

	private String field;
	private String fieldMatch;
	
	@Override
	public void initialize(FieldsValueMatch constraintAnnotation) {
		this.field = constraintAnnotation.field();
		this.fieldMatch = constraintAnnotation.fieldMatch();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
		Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
		
		if (fieldValue != null) {
			return fieldValue.equals(fieldMatchValue);
		} else {
			return fieldMatchValue == null;
		}
	}

}
