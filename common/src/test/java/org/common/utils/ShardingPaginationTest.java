package org.common.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author nurhier
 * @date 2020/1/14
 */
public class ShardingPaginationTest {
    @Test
    public void taskRangeTest() {
        List<Integer> shardingItems = Arrays.asList(0, 1, 2, 3);
        for (Integer shardingItem : shardingItems) {
            getRangeByTaskSharding(shardingItem, shardingItems.size());
        }
    }

    private void getRangeByTaskSharding(int shardItem, int shardingCount) {
        ShardingPagination shardingPagination = new ShardingPagination(100, 2401, shardItem, shardingCount);
        System.out.println(String.format("分片序号:%d; 总页数:%d; 总项数:%d", shardItem, shardingPagination.getPageTotal(),
                                         shardingPagination.getItemTotal()));
        while (shardingPagination.hasNextPage()) {
            System.out.println(String.format("当前页:%d; 起止坐标:%d; 终止坐标:%d", shardingPagination.getCurrentPage(),
                                             shardingPagination.getStart(), shardingPagination.getEnd()));
            shardingPagination.nextPage();
        }
    }
}
