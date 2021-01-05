package com.ezy.message.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * @author Caixiaowei
 * @ClassName DateUtil.java
 * @Description 日期工具类
 * @createTime 2020年05月14日 09:37:00
 */
public class DateUtil {

    public static final String FULL_TIME_PATTERN = "yyyyMMddHHmmss";

    public static final String FULL_TIME_SPLIT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String CST_TIME_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

    public static String formatFullTime(LocalDateTime localDateTime) {
        return formatFullTime(localDateTime, FULL_TIME_PATTERN);
    }

    public static String formatFullTime(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dateTimeFormatter);
    }

    public static String getDateFormat(Date date, String dateFormatType) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatType, Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    public static String formatCSTTime(String date, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CST_TIME_PATTERN, Locale.US);
        Date usDate = simpleDateFormat.parse(date);
        return DateUtil.getDateFormat(usDate, format);
    }

    public static String formatInstant(Instant instant, String format) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * LocalDateTime 转 时间戳(秒级别)
     *
     * @param localDateTime
     * @return Long
     * @author Caixiaowei
     * @updateTime 2020/9/8 10:17
     */
    public static Long localDateTimeToSecond(LocalDateTime localDateTime) {
        Long epochSecond = localDateTime.toEpochSecond(ZoneOffset.ofHours(8));
        return epochSecond;
    }

    /**
     * LocalDateTime 转 时间戳(毫秒级别)
     *
     * @param localDateTime
     * @return Long
     * @author Caixiaowei
     * @updateTime 2020/9/8 10:17
     */
    public static Long localDateTimeToMilliseconds(LocalDateTime localDateTime) {
        long milliseconds = localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        return milliseconds;
    }

    /**
     * 时间戳(秒) 转 LocalDateTime
     *
     * @param second 时间戳(秒)
     * @return LocalDateTime
     * @author Caixiaowei
     * @updateTime 2020/9/8 10:20
     */
    public static LocalDateTime secondToLocalDateTime(Long second) {
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(second, 0, ZoneOffset.ofHours(8));
        return localDateTime;
    }

    /**
     * 时间戳(毫秒) 转 LocalDateTime
     *
     * @param milliseconds 时间戳(毫秒)
     * @return LocalDateTime
     * @author Caixiaowei
     * @updateTime 2020/9/8 10:20
     */
    public static LocalDateTime millisecondsToLocalDateTime(Long milliseconds) {
        LocalDateTime localDateTime = Instant.ofEpochMilli(milliseconds).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        return localDateTime;
    }
}
