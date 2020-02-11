package org.validator.bean;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.validator.custom.ValidDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author nurhier
 * @date 2020/2/10
 */
@Data
public class Vehicle {
    @NotNull(message = "车架号不能为空", groups = Check.class)
    @Length(max = 17, message = "车架号不能大于17位")
    private String frameNo;
    @Length(max = 8, min = 7, message = "车牌号'${validatedValue}'位数需要在{min}和{max}开区间")
    private String vehicleNo;
    private String engineNo;
    @Pattern(regexp = "[0-9]")
    private String modelId;
    @ValidDate
    private String createTime;
}
