package org.common.enums;

/**
 * @author nurhier
 * @date 2019/9/27
 */
public interface BaseEnum<T> {
    /**
     * enum name
     *
     * @return string
     */
    String getName();

    /**
     * enum code
     *
     * @return T
     */
    T getCode();
}
