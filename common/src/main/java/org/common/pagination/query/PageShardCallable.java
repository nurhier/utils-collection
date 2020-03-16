package org.common.pagination.query;

/**
 * 分片查询Callable
 *
 * @author nurhier
 * @date 2020/3/16
 */
public interface PageShardCallable<E> {

    /**
     * 回调执行业务逻辑
     *
     * @param pageNo 页码
     * @return {@link E}
     * @date 2020/3/16 14:55
     */
    E call(Integer pageNo);
}
