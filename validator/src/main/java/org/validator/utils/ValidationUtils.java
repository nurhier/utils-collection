package org.validator.utils;

import org.hibernate.validator.HibernateValidator;
import org.validator.ValidationResult;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author nurhier
 * @date 2020/2/10
 */
public class ValidationUtils {

    private static Validator validator = Validation.byProvider(HibernateValidator.class).configure().failFast(false)
                                                   .buildValidatorFactory().getValidator();

    public static <T> ValidationResult<T> validate(T target) {
        Set<ConstraintViolation<T>> violationSet = validator.validate(target);
        return validateResult(violationSet);

    }

    public static <T, G> ValidationResult<T> validate(T target, Class<G>... groupClass) {
        Set<ConstraintViolation<T>> violationSet = validator.validate(target, groupClass);
        return validateResult(violationSet);
    }

    public static <T> ValidationResult<T> validate(T target, String property) {
        Set<ConstraintViolation<T>> violationSet = validator.validateProperty(target, property);
        return validateResult(violationSet);
    }

    private static <T> ValidationResult<T> validateResult(Set<ConstraintViolation<T>> violationSet) {
        if (violationSet == null || violationSet.isEmpty()) {
            return new ValidationResult<>(true, violationSet, Collections.emptyList());
        }
        List<String> errorList = new ArrayList<>();
        for (ConstraintViolation<T> violation : violationSet) {
            errorList.add(violation.getMessage());
        }
        return new ValidationResult<>(false, violationSet, errorList);
    }
}
