package org.comparer.annotation;

import org.common.enums.BaseEnum;
import org.comparer.ComparerService;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author nurhier
 * @date 2019/9/27
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Comparer {
    /**
     * 是否开启比较
     *
     * @return boolean
     */
    boolean isCompare() default true;

    /**
     * 字段名称
     *
     * @return string
     */
    String name();

    /**
     * 日期格式
     *
     * @return string
     */
    String datePattern() default "yyyy-MM-dd HH:mm:ss";

    /**
     * 如果字段为枚举，需提供枚举类，用于获取name
     * 枚举类需实现此接口
     *
     * @return class
     */
    Class<? extends BaseEnum> enumClass() default DefaultCompareEnum.class;

    /**
     * bean服务类
     *
     * @return class
     */
   Class<? extends ComparerService<?>> beanClass() default DefaultComparerServiceImpl.class;

    /**
     * default comparer service
     */
    class DefaultComparerServiceImpl implements ComparerService<Object> {
        @Override
        public String getName(Object param) {
            return null;
        }
    }

    /**
     * default comparer enum
     */
    enum DefaultCompareEnum implements BaseEnum<Integer> {
        ;

        @Override
        public String getName() {
            return null;
        }

        @Override
        public Integer getCode() {
            return null;
        }
    }
}
