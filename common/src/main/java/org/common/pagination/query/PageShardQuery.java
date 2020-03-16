package org.common.pagination.query;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 分片查询类
 *
 * @param <T>
 * @author nurhier
 * @date 2020/3/16
 */
@Slf4j
public class PageShardQuery<T> {

    /**
     * 测试
     */
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    /**
     * 分片查询,返回结果无序归并
     *
     * @param total    总记录数
     * @param pageSize 分页数
     * @param callable 查询的具体实现
     * @return {@link List<T>}
     */
    public List<T> doQuery(Integer total, Integer pageSize, PageShardCallable<List<T>> callable) {
        if (isSinglePage(total, pageSize)) {
            return Collections.emptyList();
        }
        //分片个数
        final int shardSize = total / pageSize;
        CompletionService<List<T>> completionService = new ExecutorCompletionService<>(executorService);
        for (int i = 1; i <= shardSize; i++) {
            int finalI = i;
            completionService.submit(() -> callable.call(finalI + 1));
        }
        List<T> resultList = new ArrayList<>();
        for (int i = 1; i <= shardSize; i++) {
            try {
                List<T> result = completionService.take().get();
                resultList.addAll(result);
            } catch (Exception e) {
                log.error("多线程分页查询获取结果异常：{}", e.getMessage());
            }
        }
        return resultList;
    }


    /**
     * 分片查询,返回结果按照页码归并
     *
     * @param total    总数
     * @param pageSize 每页条数
     * @param callable callable
     * @return {@link List<T>}
     * @date 2020/3/16 16:29
     */
    public List<T> doQuerySequence(Integer total, Integer pageSize,
                                   PageShardCallable<PageShardResult<List<T>>> callable) {
        if (isSinglePage(total, pageSize)) {
            return Collections.emptyList();
        }
        //分片个数
        final int shardSize = total / pageSize;
        CompletionService<PageShardResult<List<T>>> completionService = new ExecutorCompletionService<>(
                executorService);
        for (int i = 1; i <= shardSize; i++) {
            int finalI = i;
            completionService.submit(() -> callable.call(finalI + 1));
        }
        Map<Integer, List<T>> resultMap = new ConcurrentHashMap<>(shardSize);
        for (int i = 1; i <= shardSize; i++) {
            PageShardResult<List<T>> result = null;
            try {
                result = completionService.take().get();
            } catch (Exception e) {
                log.error("多线程分页查询获取结果异常：{}", e.getMessage());
            }
            if (result != null && result.getPage() != null) {
                resultMap.put(result.getPage(), result.getData());
            }
        }
        List<T> resultList = new ArrayList<>();
        for (int i = 2; i <= shardSize + 1; i++) {
            List<T> t = resultMap.get(i);
            if (t != null) {
                resultList.addAll(t);
            }
        }
        return resultList;
    }

    /**
     * 不需要分页查询
     *
     * @param total    total
     * @param pageSize pageSize
     * @return {@link boolean}
     * @date 2020/3/16 16:30
     */
    private boolean isSinglePage(Integer total, Integer pageSize) {
        if (null == total || 0 == total || null == pageSize || 0 == pageSize) {
            return true;
        }
        //不需要分片
        return total.intValue() == pageSize.intValue();
    }
}
