package org.example.aspect.spel.redis;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 启用reids 缓存
 *
 * @author nurhier
 * @date 2020/2/24
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisCache {
    /**
     * keyType 为 SPEL
     * redis key 值, 支持Spring Expression Language (SpEL) 表达式
     * <br>
     * 例：
     * <br>
     * {@code key = "#user.name"}
     * <br>
     * {@code key = "#user.id + ':' + user.name"}
     * keyType为VALUE 直接取值
     *
     * @return String
     */
    String key();

    /**
     * 过期时间 默认1000
     *
     * @return long
     */
    long timeout() default 1000;

    /**
     * 过期时间单位 默认毫秒
     *
     * @return {@link TimeUnit}
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
