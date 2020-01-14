package org.common.enums;

/**
 * 枚举工具类
 *
 * @author nurhier
 * @date 2019/9/27
 */
public class EnumUtils {
    /**
     * get enum name by code
     *
     * @param enumClass 枚举class
     * @param code      枚举编码
     * @return 枚举名称
     */
    public static String getEnumNameByCode(Class<? extends BaseEnum> enumClass, Integer code) {
        if (code == null) {
            return null;
        }
        BaseEnum enumValue = getEnumByCode(enumClass, code);
        if (enumValue == null) {
            return null;
        }
        return enumValue.getName();
    }

    /**
     * get enum name by code
     *
     * @param enumClass 枚举class
     * @param name      枚举名称
     * @return 枚举编码
     */
    public static Integer getEnumCodeByName(Class<? extends BaseEnum> enumClass, String name) {
        if (name == null) {
            return null;
        }
        BaseEnum enumValue = getEnumByName(enumClass, name);
        if (enumValue == null) {
            return null;
        }
        return enumValue.getCode();
    }

    /**
     * get enum by code
     *
     * @param enumClass 枚举class
     * @param code      枚举编码
     * @param <T>       枚举类型
     * @return 枚举
     */
    public static <T extends BaseEnum> T getEnumByCode(Class<T> enumClass, Integer code) {
        if (code == null) {
            return null;
        }
        for (T each : enumClass.getEnumConstants()) {
            if (each.getCode().equals(code)) {
                return each;
            }
        }
        return null;
    }

    /**
     * get enum by name
     *
     * @param enumClass 枚举class
     * @param name      枚举名称
     * @param <T>       枚举类型
     * @return 枚举
     */
    public static <T extends BaseEnum> T getEnumByName(Class<T> enumClass, String name) {
        if (name == null) {
            return null;
        }
        for (T each : enumClass.getEnumConstants()) {
            if (each.getName().equals(name)) {
                return each;
            }
        }
        return null;
    }
}
