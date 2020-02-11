package org.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

/**
 * @author nurhier
 * @date 2020/2/11
 */
@Getter
@ToString
@AllArgsConstructor
public class ValidationResult<T> {
    /**
     * 是否通过校验
     */
    private boolean passed;
    /**
     * 校验原始结果
     */
    private Set<ConstraintViolation<T>> violations;
    /**
     * 错误信息数组
     */
    private List<String> errorMessageList;
}
