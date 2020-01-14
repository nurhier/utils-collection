package org.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author nurhier
 * @date 2020/1/14 16:25
 */
@Slf4j
public class ReflectionUtils {
    private ReflectionUtils() {
    }

    /**
     * 判断是否属性是否都为null
     *
     * @param target           待判断对象
     * @param ignoreProperties 不参与校验的字段
     * @return boolean
     */
    public static <T> boolean isPropertiesNull(T target, String... ignoreProperties) {
        return isPropertiesNull(target, null, ignoreProperties);
    }

    /**
     * 判断是否属性是否都为null
     *
     * @param <T>              T
     * @param <I>              I
     * @param target           target 待判断对象
     * @param ignore           ignore 需忽略此对象中存在的属性
     * @param ignoreProperties ignoreProperties 需忽略的属性数组
     * @return boolean
     */
    public static <T, I> boolean isPropertiesNull(T target, Class<I> ignore, String... ignoreProperties) {
        if (target == null) {
            return true;
        }
        Set<String> ignoreFieldSet = new HashSet<>(16);
        ignoreFieldSet.addAll(Arrays.asList(ignoreProperties));
        if (ignore != null) {
            Field[] ignoreFields = ignore.getDeclaredFields();
            for (Field field : ignoreFields) {
                ignoreFieldSet.add(field.getName());
            }
        }
        return isPropertiesNull(target, ignoreFieldSet);
    }

    /**
     * 判断是否属性是否都为null
     *
     * @param <T>            T
     * @param target         target
     * @param ignoreFieldSet ignoreFieldSet
     * @return boolean
     */
    public static <T> boolean isPropertiesNull(T target, Set<String> ignoreFieldSet) {
        if (target == null) {
            return true;
        }
        if (ignoreFieldSet == null) {
            ignoreFieldSet = new HashSet<>();
        }
        try {
            boolean allPropertyIsNull = true;
            Field[] targetFields = target.getClass().getDeclaredFields();
            for (Field field : targetFields) {
                String fieldName = field.getName();
                if (ignoreFieldSet.contains(fieldName)) {
                    continue;
                }
                field.setAccessible(true);
                Object value = field.get(target);
                if (value != null) {
                    allPropertyIsNull = false;
                    break;
                }
            }
            return allPropertyIsNull;
        } catch (IllegalAccessException e) {
            return false;
        }
    }
}
