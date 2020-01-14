package org.model.enums;

import org.common.enums.BaseEnum;

/**
 * @author nurhier
 * @date 2020/1/14
 */
public enum VehicleNoType implements BaseEnum {
    BLUE("蓝色牌照", 10),
    GREEN("绿色牌照", 20);

    private Integer code;
    private String name;

    VehicleNoType(String name, Integer code) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
