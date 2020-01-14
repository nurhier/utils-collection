package org.comparer;

import lombok.extern.slf4j.Slf4j;
import org.common.enums.BaseEnum;
import org.common.enums.EnumUtils;
import org.comparer.annotation.Comparer;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author nurhier
 * @date 2019/9/27
 */
@Slf4j
public abstract class AbstractComparer {
    /**
     * 格式化原值和目标值的输出方式
     *
     * @param logCompare  注解
     * @param sourceValue 源值
     * @param targetValue 目标值
     * @return
     */
    protected abstract String formatOutput(Comparer logCompare, String sourceValue, String targetValue);

    /**
     * 简单数组过滤，为真则不再解析数据
     * 支持重写扩展
     *
     * @param data
     * @return
     */
    protected boolean parseSimpleListValueFilter(Object data) {
        return false;
    }

    /**
     * 判断两个值是否相等
     * 支持重写扩展
     *
     * @param sourceValue
     * @param targetValue
     * @return
     */
    protected boolean isEqualValue(Object sourceValue, Object targetValue) {
        return sourceValue == null && targetValue == null || sourceValue != null && sourceValue.equals(targetValue);
    }

    protected <T> ComparerService<T> getComparerService(Comparer comparer)
            throws IllegalAccessException, InstantiationException {
        if (comparer == null) {
            return (ComparerService<T>) new Comparer.DefaultComparerServiceImpl();
        }
        return (ComparerService<T>) comparer.beanClass().newInstance();
    }

    /**
     * 比较两个对象属性差异
     *
     * @param clazz      对象类
     * @param sourceData 比较源对象
     * @param targetData 比较目标对象
     * @param <T>
     * @param <K>
     * @return 每个属性的差异
     */
    public <T, K> List<String> compareDiff(Class<T> clazz, K sourceData, K targetData) {
        List<String> diffResult = new ArrayList<>();
        doCompareDiff(clazz, sourceData, targetData, diffResult);
        return diffResult;
    }

    private <T, K> void doCompareDiff(Class<T> clazz, K sourceData, K targetData, List<String> diffResult) {
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                Comparer logCompare = field.getAnnotation(Comparer.class);
                if (logCompare == null || !logCompare.isCompare()) {
                    continue;
                }
                Object sourceValue = getFieldObject(sourceData, field);
                Object targetValue = getFieldObject(targetData, field);
                if (!isBaseProperty(field)) {
                    doCompareDiff(field.getType(), sourceValue, targetValue, diffResult);
                } else {
                    if (isEqualValue(sourceValue, targetValue)) {
                        continue;
                    }
                    String source = null;
                    String target = null;
                    try {
                        //待优化，只支持List中对象存在少量对比字段
                        if (List.class.isAssignableFrom(field.getType())) {
                            source = parseSimpleListValue((List<?>) sourceValue);
                            target = parseSimpleListValue((List<?>) targetValue);
                        } else {
                            source = parseValue(logCompare, sourceValue);
                            target = parseValue(logCompare, targetValue);
                        }
                    } catch (Exception e) {
                        log.error("field parse exception，field：{}, value: {}, {}, error:", field.getName(),
                                  sourceValue, targetValue, e);
                    }
                    diffResult.add(formatOutput(logCompare, source, target));
                }
            }
        } catch (Exception e) {
            log.error("compare object exception:", e);
        }
    }

    private <K> Object getFieldObject(K data, Field field) throws NoSuchFieldException, IllegalAccessException {
        Object targetValue = null;
        if (data != null) {
            Field targetField = data.getClass().getDeclaredField(field.getName());
            if (!targetField.isAccessible()) {
                targetField.setAccessible(true);
            }
            targetValue = targetField.get(data);
        }
        return targetValue;
    }

    private <T> String parseValue(Comparer comparer, T value) throws IllegalAccessException, InstantiationException {
        String parsedValue;
        if (value == null) {
            return null;
        }
        if (comparer.enumClass().isEnum() && !comparer.enumClass().equals(Comparer.DefaultCompareEnum.class)) {
            parsedValue = (EnumUtils.getEnumNameByCode(comparer.enumClass(), (Integer) value));
        } else if (!(comparer.beanClass().equals(Comparer.DefaultComparerServiceImpl.class))) {
            ComparerService<T> comparerService = getComparerService(comparer);
            parsedValue = comparerService.getName(value);
        } else if (value instanceof BaseEnum) {
            parsedValue = ((BaseEnum) value).getName();
        } else {
            parsedValue = value.toString();
            if (value instanceof Date) {
                SimpleDateFormat timeSdf = new SimpleDateFormat(comparer.datePattern());
                parsedValue = timeSdf.format(value);
            }
        }
        return parsedValue;
    }

    private <T> String parseSimpleListValue(List<T> list) {
        StringBuilder sb = new StringBuilder();
        if (list == null) {
            return sb.toString();
        }
        for (T object : list) {
            if (parseSimpleListValueFilter(object)) {
                continue;
            }
            Field[] fields = object.getClass().getDeclaredFields();
            StringBuilder eleInnerSb = new StringBuilder();
            for (Field field : fields) {
                Comparer logCompare = field.getAnnotation(Comparer.class);
                if (logCompare == null || !logCompare.isCompare()) {
                    continue;
                }
                try {
                    Object value = getFieldObject(object, field);
                    String convertValue = parseValue(logCompare, value);
                    if (convertValue != null) {
                        eleInnerSb.append(convertValue).append(",");
                    }
                } catch (Exception e) {
                    log.error("List element field parse exception, field：{}, error:", field.getName(), e);
                }
            }
            if (eleInnerSb.length() > 0) {
                sb.append(eleInnerSb.substring(0, eleInnerSb.length() - 1)).append(";");
            }
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
    }

    private boolean isBaseProperty(Field field) {
        Class<?> typeClazz = field.getType();
        return typeClazz.isPrimitive() || String.class.isAssignableFrom(typeClazz) || List.class
                .isAssignableFrom(typeClazz) || Number.class.isAssignableFrom(typeClazz) || Date.class
                .isAssignableFrom(typeClazz) || typeClazz.isEnum();
    }
}
