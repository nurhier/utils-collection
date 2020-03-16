package org.common.pagination.query;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author nurhier
 * @date 2020/3/16
 **/
public class PageShardQueryTest {
    private PageShardQuery<String> pageShardQuery = new PageShardQuery<>();

    @Test
    public void testPageShardQuery() {
        List<String> result = new ArrayList<>();
        result.addAll(pageShardQuery.doQuery(50, 5, pageNo -> {
            try {
                Thread.sleep(RandomUtils.nextInt(1, 100));
            } catch (Exception e) {
                e.getStackTrace();
            }
            return Arrays.asList(pageNo + "1", pageNo + "2", pageNo + "3", pageNo + "4", pageNo + "5");
        }));
        result.forEach(System.out::println);
    }

    @Test
    public void testPageShardQuerySequence() {
        List<String> result = new ArrayList<>();
        result.addAll(pageShardQuery.doQuerySequence(50, 5, pageNo -> {
            try {
                Thread.sleep(RandomUtils.nextInt(1, 100));
            } catch (Exception e) {
                e.getStackTrace();
            }
            return new PageShardResult<List<String>>().setPage(pageNo).setData(
                    Arrays.asList(pageNo + "1", pageNo + "2", pageNo + "3", pageNo + "4", pageNo + "5"));
        }));
        result.forEach(System.out::println);
    }
}
