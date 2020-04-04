package org.common.consistent;

/**
 * hash function
 *
 * @author nurhier
 * @date 2020/4/4
 **/
public interface HashFunction {
    /**
     * calculate hash value
     *
     * @param key key
     * @return {@link Long}
     * @date 2020/4/4 19:43
     */
    Long hash(String key);
}
