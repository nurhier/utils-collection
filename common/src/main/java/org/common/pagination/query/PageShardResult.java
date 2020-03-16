package org.common.pagination.query;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 分页查询结果
 *
 * @author nurhier
 * @date 2020/3/16
 **/
@Data
@Accessors(chain = true)
public class PageShardResult<T> {
    /**
     * 页码
     */
    private Integer page;
    /**
     * 数据
     */
    private T data;
}
