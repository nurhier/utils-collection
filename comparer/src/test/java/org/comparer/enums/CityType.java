package org.comparer.enums;

import org.common.enums.BaseEnum;

/**
 * @author nurhier
 * @date 2019/9/27
 */
public enum CityType implements BaseEnum {
    /**
     * enum
     */
    SHENG("省会市", 1), DI("地级市", 2), XIAN("县级市", 3);

    private String name;
    private Integer code;

    CityType(String name, Integer code) {
        this.name = name;
        this.code = code;
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
