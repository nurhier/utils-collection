package org.common.utils;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * bean copier
 *
 * @author nurhier
 * @date 2020/3/3
 */
public class BeanUtils {
    private BeanUtils() {}

    /**
     * mapperFactory
     */
    private static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    /**
     * mapperFactoryNotNull 不拷贝空值
     */
    private static MapperFactory mapperFactoryNotNull = new DefaultMapperFactory.Builder().mapNulls(false).build();
    /**
     * mapperFactory 缓存
     * key: source className +$+ target className +$+ filed map hash code +$+ ignore properties hash code
     */
    private static Map<String, MapperFacade> mapperFacadeCache = new ConcurrentHashMap<>();

    /**
     * 深拷贝属性值
     *
     * @param source source
     * @param target target
     * @date 2020/3/4 15:20
     */
    public static <S, T> void copyProperties(S source, T target) {
        mapperFactory.getMapperFacade().map(source, target);
    }

    /**
     * 深拷贝属性非空值
     *
     * @param source source
     * @param target target
     * @date 2020/3/4 15:20
     */
    public static <S, T> void copyNonNullProperties(S source, T target) {
        mapperFactoryNotNull.getMapperFacade().map(source, target);
    }

    /**
     * 生成新对象并拷贝源对象属性
     *
     * @param source source
     * @param target target
     * @return T
     * @date 2020/3/4 15:29
     */
    public static <S, T> T copy(S source, Class<T> target) {
        return mapperFactory.getMapperFacade().map(source, target);
    }

    /**
     * 生成新对象并拷贝源对象属性，自定义映射字段
     *
     * @param source   source
     * @param target   target
     * @param fieldMap fieldMap 映射字段
     * @return T
     * @date 2020/3/4 15:29
     */
    public static <S, T> T copy(S source, Class<T> target, Map<String, String> fieldMap) {
        return copy(source, target, fieldMap, (String[]) null);
    }

    /**
     * 生成新对象并拷贝源对象属性，忽略定义字段
     *
     * @param source           source
     * @param target           target
     * @param ignoreProperties ignoreProperties 忽略定义字段
     * @return T
     * @date 2020/3/4 15:29
     */
    public static <S, T> T copy(S source, Class<T> target, String... ignoreProperties) {
        return copy(source, target, null, ignoreProperties);
    }

    /**
     * 生成新对象并拷贝源对象属性，自定义映射字段，忽略定义字段
     *
     * @param source           source
     * @param target           target
     * @param fieldMap         fieldMap 自定义映射字段
     * @param ignoreProperties ignoreProperties 忽略定义字段
     * @return T
     * @date 2020/3/4 15:29
     */
    public static <S, T> T copy(S source, Class<T> target, Map<String, String> fieldMap, String... ignoreProperties) {
        return doCopy(source, target, fieldMap, ignoreProperties);
    }

    /**
     * 执行拷贝
     *
     * @param source           source
     * @param target           target
     * @param fieldMap         fieldMap
     * @param ignoreProperties ignoreProperties
     * @return T
     * @date 2020/3/4 15:30
     */
    private static <S, T> T doCopy(S source, Class<T> target, Map<String, String> fieldMap,
                                   String[] ignoreProperties) {
        MapperFacade mapperFacade = getMapperFacade(source, target, fieldMap, ignoreProperties);
        return mapperFacade.map(source, target);
    }

    /**
     * 生成新对象数组并拷贝源对象数组
     *
     * @param source source
     * @param target target
     * @return T
     * @date 2020/3/4 15:29
     */
    public static <S, T> List<T> copyList(Collection<S> source, Class<T> target) {
        return mapperFactory.getMapperFacade().mapAsList(source, target);
    }

    /**
     * 生成新对象数组并拷贝源对象数组，自定义映射字段
     *
     * @param source source
     * @param target target
     * @return T
     * @date 2020/3/4 15:29
     */
    public static <S, T> List<T> copyList(Collection<S> source, Class<T> target, Map<String, String> fieldMap) {
        return getMapperFacade(source, target, fieldMap, (String) null).mapAsList(source, target);
    }

    /**
     * getMapperFacade
     *
     * @param source           source
     * @param target           target
     * @param fieldMap         fieldMap
     * @param ignoreProperties ignoreProperties
     * @return ma.glasnost.orika.MapperFacade
     * @date 2020/3/4 15:30
     */
    private static <S, T> MapperFacade getMapperFacade(S source, Class<T> target, Map<String, String> fieldMap,
                                                       String... ignoreProperties) {
        String dollar = "$";
        String key = source.getClass().getCanonicalName() + dollar + target.getCanonicalName();
        if (fieldMap != null) {
            key += dollar + fieldMap.hashCode();
        }
        if (ignoreProperties != null) {
            key += dollar + Arrays.hashCode(ignoreProperties);
        }
        MapperFacade mapperFacade = mapperFacadeCache.get(key);
        if (Objects.nonNull(mapperFacade)) {
            return mapperFacade;
        }
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        ClassMapBuilder<?, T> classMapBuilder = mapperFactory.classMap(source.getClass(), target);
        if (ignoreProperties != null && ignoreProperties.length != 0) {
            Arrays.stream(ignoreProperties).filter(Objects::nonNull).forEach(classMapBuilder::exclude);
        }
        if (fieldMap != null && !fieldMap.isEmpty()) {
            fieldMap.forEach(classMapBuilder::field);
        }
        classMapBuilder.byDefault().register();
        mapperFacade = mapperFactory.getMapperFacade();
        mapperFacadeCache.put(key, mapperFacade);
        return mapperFacade;
    }
}
