package org.validator.bean;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author nurhier
 * @date 2020/2/10
 */
@Data
public class Vehicle {
    @NotNull(message = "车架号不能为空")
    @Length(max = 17, message = "车架号不能大于17位")
    private String frameNo;
    private String vehicleNo;
    private String engineNo;
    @Pattern(regexp = "[0-9]")
    private String modelId;
}
