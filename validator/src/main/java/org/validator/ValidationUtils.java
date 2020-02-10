package org.validator;

import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author nurhier
 * @date 2020/2/10
 */
public class ValidationUtils {

    private static Validator validator = Validation.byProvider(HibernateValidator.class).configure().failFast(false)
                                                   .buildValidatorFactory().getValidator();

    public static <T> void validate(T target) {
        Set<ConstraintViolation<T>> violationSet = validator.validate(target);
        if (violationSet != null && !violationSet.isEmpty()){
            for (ConstraintViolation<T> violation : violationSet) {
                System.out.println(violation.getMessage());
                System.out.println(violation.getPropertyPath().toString());
            }
        }
    }
}
