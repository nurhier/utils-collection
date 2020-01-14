package org.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 分片分页工具
 * <p>
 * 根据数据总数和分片片数计算
 *
 * @author nurhier
 * @date 2019/12/25
 */

public class ShardingPagination {
    /**
     * 每页的开始下标
     */
    @Getter
    private int start;
    /**
     * 每页的结束下标
     */
    @Getter
    private int end;
    /**
     * 分片的开始
     */
    private int firstIndex;
    /**
     * 分片的结束
     */
    private int lastIndex;
    /**
     * 每页数据总数
     */
    private int pageSize;
    /**
     * 数据总数
     */
    @Getter
    private int itemTotal;
    /**
     * 分页总数
     */
    @Getter
    private int pageTotal;
    /**
     * 当前页码
     */
    @Getter
    private int currentPage = 1;

    private ShardingPagination() {}

    /**
     * 构造方法
     *
     * @param pageSize      每页大小
     * @param dataTotal     数据总数
     * @param shardItem     分片序号，从0开始
     * @param shardingCount 分片总数
     */
    public ShardingPagination(int pageSize, int dataTotal, int shardItem, int shardingCount) {
        Range range = getRangeBySharding(dataTotal, shardItem, shardingCount);
        this.firstIndex = range.startIndex;
        this.lastIndex = range.endIndex;
        this.pageSize = pageSize;
        this.itemTotal = lastIndex - firstIndex + 1;
        this.pageTotal = (this.itemTotal + pageSize - 1) / pageSize;
        page(this.currentPage);
    }

    /**
     * 下一页
     */
    public void nextPage() {
        if (hasNextPage()) {
            page(this.currentPage + 1);
        } else {
            throw new RuntimeException("翻页越界");
        }
    }

    /**
     * 是否可翻页
     *
     * @return Boolean
     */
    public boolean hasNextPage() {
        return this.currentPage <= this.pageTotal;
    }

    /**
     * 分页
     *
     * @param currentPage 当前页码
     */
    private void page(int currentPage) {
        this.currentPage = currentPage;
        this.start = this.firstIndex + (currentPage - 1) * this.pageSize;
        if (this.itemTotal < this.pageSize) {
            this.end = this.lastIndex + 1;
        } else if (this.currentPage == this.pageTotal && this.itemTotal % this.pageSize != 0) {
            this.end = this.start + this.itemTotal % this.pageSize;
        } else {
            this.end = this.start + this.pageSize;
        }
    }

    /**
     * 1.根据数据总数和分片片数计算每个片区分担数据数量
     * 2.根据分片序号计算此分片的起始标号和结束标号
     * 3.如果为最后一个分片，则结束标号为数据总数减1
     *
     * @param vehicleTotal  数据总数
     * @param shardItem     分片序号
     * @param shardingCount 分片总数
     * @return Range
     */
    private Range getRangeBySharding(int vehicleTotal, int shardItem, int shardingCount) {
        if (shardItem >= shardingCount) {
            throw new RuntimeException("分片序号超过分片数量");
        }
        int shardItemTotal;
        if (shardingCount > 1) {
            shardItemTotal = vehicleTotal / shardingCount;
        } else {
            shardItemTotal = vehicleTotal;
        }
        int startIndex = shardItem * shardItemTotal;
        int endIndex = startIndex + shardItemTotal - 1;
        if (shardItem == shardingCount - 1) {
            endIndex = vehicleTotal - 1;
        }
        return new Range(startIndex, endIndex);
    }

    /**
     * 起止坐标
     */
    @Getter
    @AllArgsConstructor
    private static class Range {
        private int startIndex;
        private int endIndex;
    }
}
