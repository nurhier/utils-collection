package org.common.utils;

import org.common.enums.BaseEnum;

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
     * @param <T>       编码类型
     * @return 枚举名称
     */
    public static <T> String getEnumNameByCode(Class<? extends BaseEnum<T>> enumClass, T code) {
        if (code == null) {
            return null;
        }
        BaseEnum<T> enumValue = getEnumByCode(enumClass, code);
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
     * @param <T>       编码类型
     * @return 枚举编码
     */
    public static <T> T getEnumCodeByName(Class<? extends BaseEnum<T>> enumClass, String name) {
        if (name == null) {
            return null;
        }
        BaseEnum<T> enumValue = getEnumByName(enumClass, name);
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
     * @param <T>       编码类型
     * @param <E>       枚举类型
     * @return 枚举
     */
    public static <E extends BaseEnum<T>, T> E getEnumByCode(Class<E> enumClass, T code) {
        if (code == null) {
            return null;
        }
        for (E each : enumClass.getEnumConstants()) {
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
     * @param <T>       编码类型
     * @param <E>       enum类型
     * @return 枚举
     */
    public static <E extends BaseEnum<T>, T> E getEnumByName(Class<E> enumClass, String name) {
        if (name == null) {
            return null;
        }
        for (E each : enumClass.getEnumConstants()) {
            if (each.getName().equals(name)) {
                return each;
            }
        }
        return null;
    }
}
