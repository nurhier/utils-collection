package org.validator.custom;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自应用约束
 *
 * @author nurhier
 * @date 2020/2/11
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {ValidDate.ValidDateValidator.class})
public @interface ValidDate {
    String message() default "日期格式不匹配{dateFormat}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String dateFormat() default "yyyy-MM-dd HH:mm:ss";

    class ValidDateValidator implements ConstraintValidator<ValidDate, String> {
        private String dateFormat;

        @Override
        public void initialize(ValidDate constraintAnnotation) {
            dateFormat = constraintAnnotation.dateFormat();
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
            if (value == null || value.equals("")) {
                return true;
            }
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
                simpleDateFormat.parse(value);
            } catch (ParseException e) {
                return false;
            }
            return true;
        }
    }
}
