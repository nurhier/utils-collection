package org.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nurhier
 * @date 2020/1/15
 */
public class DateUtils {
    private DateUtils() {}

    private static ThreadLocal<Map<String, DateFormat>> threadLocal = new ThreadLocal<Map<String, DateFormat>>() {
        @Override
        protected Map<String, DateFormat> initialValue() {
            return new HashMap<>(16);
        }
    };

    public static Date parse(String date, DatePattern datePattern) throws ParseException {
        if (date == null || datePattern == null) {
            return null;
        }
        return getDateFormat(datePattern).parse(date);
    }

    public static String format(Date date, DatePattern datePattern) {
        if (date == null || datePattern == null) {
            return null;
        }
        return getDateFormat(datePattern).format(date);
    }

    private static DateFormat getDateFormat(DatePattern datePattern) {
        Map<String, DateFormat> dateFormatMap = threadLocal.get();
        DateFormat dateFormat = dateFormatMap.get(datePattern.getPattern());
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(datePattern.getPattern());
            dateFormatMap.put(datePattern.getPattern(), dateFormat);
        }
        return dateFormat;
    }

    /**
     * 时间表达式枚举
     */
    public enum DatePattern {
        /**
         * yyyy-MM-dd HH:mm:ss
         */
        yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss"),
        /**
         * yyyy-MM-dd
         */
        yyyy_MM_dd("yyyy-MM-dd");
        private String pattern;

        DatePattern(String pattern) {
            this.pattern = pattern;
        }

        public String getPattern() {
            return pattern;
        }
    }
}
