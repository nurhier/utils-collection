package org.comparer;

/**
 * @author nurhier
 * @date 2019/9/27
 */
public interface ComparerService<T> {
    /**
     * get name
     *
     * @param param 参数
     * @return string
     */
    String getName(T param);
}
