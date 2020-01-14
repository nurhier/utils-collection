package org.comparer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.common.enums.BaseEnum;
import org.common.enums.EnumUtils;
import org.comparer.annotation.Comparer;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    protected boolean isEqualOfValue(Object sourceValue, Object targetValue) {
        return sourceValue == null && targetValue == null || sourceValue != null && sourceValue.equals(targetValue);
    }

    protected boolean isEqualOfStringBuilder(StringBuilder sourceValue, StringBuilder targetValue) {
        return (sourceValue == null && targetValue == null) || (sourceValue != null && targetValue != null && Objects
                .equals(sourceValue.toString(), targetValue.toString()));
    }

    protected boolean isEqualOfBlankString(String sourceValue, String targetValue) {
        return StringUtils.isBlank(sourceValue) && StringUtils.isBlank(targetValue);
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
        try {
            doCompareDiff(clazz, sourceData, targetData, diffResult);
        } catch (Exception e) {
            log.error("compare object exception:", e);
        }
        return diffResult;
    }

    private <T, K> void doCompareDiff(Class<T> clazz, K sourceData, K targetData, List<String> diffResult)
            throws NoSuchFieldException, IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
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
                if (isEqualOfValue(sourceValue, targetValue)) {
                    continue;
                }
                try {
                    //待优化，只支持List中对象属性字段为基本类型
                    if (List.class.isAssignableFrom(field.getType())) {
                        compareListValue(diffResult, (List) sourceValue, (List) targetValue);
                    } else {
                        String source = parseValue(logCompare, sourceValue);
                        String target = parseValue(logCompare, targetValue);
                        diffResult.add(formatOutput(logCompare, source, target));
                    }
                } catch (Exception e) {
                    log.error("field parse exception，field：{}, value: {}, {}, error:", field.getName(),
                              sourceValue, targetValue, e);
                }
            }
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

    private <T> void compareListValue(List<String> diffResult, List<T> sourceList, List<T> targetList) {
        Map<Comparer, StringBuilder> sourceMap = parseListValue(sourceList);
        Map<Comparer, StringBuilder> targetMap = parseListValue(targetList);
        Map<Comparer, StringBuilder> mainMap;
        if (sourceMap.isEmpty() && targetMap.isEmpty()) {
            return;
        }
        mainMap = sourceMap;
        if (mainMap.isEmpty()) {
            mainMap = targetMap;
        }
        for (Map.Entry<Comparer, StringBuilder> entry : mainMap.entrySet()) {
            StringBuilder source = sourceMap.get(entry.getKey());
            StringBuilder target = targetMap.get(entry.getKey());
            if (isEqualOfStringBuilder(source, target)) {
                continue;
            }
            String sourceString = null;
            String targetString = null;
            if (source != null && source.length() > 0) {
                sourceString = source.substring(0, source.length() - 1);
            }
            if (target != null && target.length() > 0) {
                targetString = target.substring(0, target.length() - 1);
            }
            if (isEqualOfBlankString(sourceString, targetString)) {
                continue;
            }
            diffResult.add(formatOutput(entry.getKey(), sourceString, targetString));
        }
    }

    private <T> Map<Comparer, StringBuilder> parseListValue(List<T> list) {
        Map<Comparer, StringBuilder> result = new HashMap<>(16);
        if (list == null || list.isEmpty()) {
            return result;
        }
        Map<Field, Comparer> fieldCompareMap = new HashMap<>(16);
        Field[] fields = list.get(0).getClass().getDeclaredFields();
        for (Field field : fields) {
            Comparer comparer = field.getAnnotation(Comparer.class);
            if (comparer == null || !comparer.isCompare()) {
                continue;
            }
            fieldCompareMap.put(field, comparer);
        }
        for (T item : list) {
            if (parseSimpleListValueFilter(item)) {
                continue;
            }
            for (Map.Entry<Field, Comparer> entry : fieldCompareMap.entrySet()) {
                StringBuilder sb = result.get(entry.getValue());
                if (sb == null) {
                    sb = new StringBuilder();
                    result.put(entry.getValue(), sb);
                }
                buildListValueText(item, sb, entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    private <T> void buildListValueText(T object, StringBuilder eleInnerSb, Field field, Comparer comparer) {
        try {
            Object value = getFieldObject(object, field);
            String convertValue = parseValue(comparer, value);
            if (convertValue != null) {
                eleInnerSb.append(convertValue).append(",");
            }
        } catch (Exception e) {
            log.error("List字段解析异常，field：{}, error:", field.getName(), e);
        }
    }

    private boolean isBaseProperty(Field field) {
        Class<?> typeClazz = field.getType();
        return typeClazz.isPrimitive() || String.class.isAssignableFrom(typeClazz) || List.class
                .isAssignableFrom(typeClazz) || Number.class.isAssignableFrom(typeClazz) || Date.class
                .isAssignableFrom(typeClazz) || typeClazz.isEnum();
    }
}
