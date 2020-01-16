package org.common.utils;

import org.common.concurrency.AbstractConcurrency;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

public class DateUtilsTest extends AbstractConcurrency {
    @Test
    public void dateUtilsTest() {
        try {
            Assert.assertNull(DateUtils.format(null, DateUtils.DatePattern.yyyy_MM_dd_HH_mm_ss));
            Assert.assertNull(DateUtils.format(new Date(), null));
            String date = "2020-01-15 22:59:43";
            Date parsedDate = DateUtils.parse(date, DateUtils.DatePattern.yyyy_MM_dd_HH_mm_ss);
            String formatDate = DateUtils.format(parsedDate, DateUtils.DatePattern.yyyy_MM_dd_HH_mm_ss);
            System.out.println(formatDate);
            Assert.assertEquals(date, formatDate);
        } catch (ParseException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void concurrencyDateParseTest() throws InterruptedException {
        super.execute(200);
    }

    @Override
    protected void runTask() {
        try {
            String date = "2020-01-15 22:59:43";
            Date parsedDate = DateUtils.parse(date, DateUtils.DatePattern.yyyy_MM_dd_HH_mm_ss);
            String formatDate = DateUtils.format(parsedDate, DateUtils.DatePattern.yyyy_MM_dd_HH_mm_ss);
            System.out.println(formatDate);
            Assert.assertEquals(date, formatDate);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
