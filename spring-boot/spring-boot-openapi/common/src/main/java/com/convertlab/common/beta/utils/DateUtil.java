package com.convertlab.common.beta.utils;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * 时间工具类
 *
 * @author LIUJUN
 * @date 2021-01-26 20:13
 */
public class DateUtil {

    private DateUtil() {

    }

    /** 日志 */
    private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);

    /** 年月日时分： yyyyMMddHHmm */
    public static final String DATE_TIME_STYLE_0 = "yyyyMMddHHmm";

    /** 年月日时分秒： yyyyMMddHHmmss */
    public static final String DATE_TIME_STYLE_1 = "yyyyMMddHHmmss";

    /** 年月日时分秒毫秒： yyMMddHHmmssSSS */
    public static final String DATE_TIME_STYLE_3 = "yyMMddHHmmssSSS";

    /** 年月日时分秒毫秒： yyyy-MM-dd HH:mm:ss.SSS */
    public static final String DATE_TIME_STYLE_7 = "yyyy-MM-dd HH:mm:ss.SSS";

    /** 年月日时分秒： yyyy-MM-dd HH:mm:ss */
    public static final String DATE_TIME_STYLE = "yyyy-MM-dd HH:mm:ss";

    /** 年月日时分秒： yyyy-MM-dd'T'HH:mm:ss'Z' */
    public static final String UTC_TIME_STYLE = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /** 年月日时分秒毫秒： yyyy-MM-dd'T'HH:mm:ss.SSS'Z' */
    public static final String UTC_TIME_MS_STYLE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /** 年月日： yyyy-MM-dd */
    public static final String DATE_STYLE = "yyyy-MM-dd";

    /**
     * 获取UTC时间,时间还是GMT时区的时间，即不计算了时区
     *
     * @param timestamp 时间戳
     * @return Date
     */
    public static Date getDateUTC(Long timestamp) {
        Calendar calendar = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        calendar.setTimeZone(timeZone);
        calendar.setTimeInMillis(timestamp);
        return calendar.getTime();
    }

    /**
     * UTC时间字符串转Date  -->解析出对应到本地时间
     *
     * @param utcTimeStr 时间
     * @param pattern    格式
     * @return Date
     */
    public static Date parseToUTC(String utcTimeStr, String pattern) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            df.setTimeZone(new SimpleTimeZone(0, "UTC"));
            return df.parse(utcTimeStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前 utc【毫秒级】时间字符串，计算了时区，即-8小时
     *
     * @return yyyy-MM-dd'T'HH:mm:ss.zzz'Z'
     */
    public static String getNowUTCStrMs() {
        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime ldtZoned = ldt.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));
        return utcZoned.toString().replace("[UTC]", "");
    }

    /**
     * 时间字符串转UTC【秒级】字符串，计算了时区，比如-8小时
     *
     * @return yyyy-MM-dd'T'HH:mm:ss'Z'
     */
    public static String getNowUTCStr() {
        return getUTCStr(new Date(), UTC_TIME_STYLE);
    }

    /**
     * 时间字符串转UTC【秒级】字符串，计算了时区，比如-8小时
     *
     * @return yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     */
    public static String getUTCStrMs(Date date) {
        return getUTCStr(date, UTC_TIME_MS_STYLE);
    }


    /**
     * 时间字符串转UTC字符串，计算了时区，比如-8小时
     *
     * @param formatStyle 要格式化的时间
     * @return formatStyle格式的时间
     */
    public static String getUTCStr(Date date, String formatStyle) {
        LocalDateTime ldt = asLocalDateTime(date);

        ZonedDateTime ldtZoned = ldt.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern(formatStyle);
        LocalDateTime localDateTime = utcZoned.toLocalDateTime();
        return localDateTime.format(formatter2);

    }

    /**
     * 时间字符串转UTC字符串，计算了时区，比如-8小时.
     *
     * @param formatStyle 要格式化的时间
     * @return formatStyle格式的时间
     */
    public static String getUTCStr(String date, String formatStyle) {
        if (date == null) {
            return null;
        }
        String zone = "UTC";
        // 如果时间本身是UTC格式，需要减去原来的8小时后再转相应utc时间
        if (date.length() == 20 && date.contains("T")) {
            zone = "UTC+8";
        }
        LocalDateTime ldt = strToLocalDateTime(date);

        ZonedDateTime ldtZoned = ldt.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of(zone));
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern(formatStyle);
        LocalDateTime localDateTime = utcZoned.toLocalDateTime();
        try {
            return localDateTime.format(formatter2);
        } catch (Exception e) {
            LOG.error("{}格式化为{}错误:", date, formatStyle, e);
            return null;
        }

    }

    /**
     * 时间Date转String
     *
     * @param date 时间
     * @return Date
     */
    public static String dateToStr(Date date) {
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern(DATE_TIME_STYLE);
        return asLocalDateTime(date).format(formatter2);
    }

    /**
     * 时间String转LocalDate
     *
     * @param date 时间
     * @return Date
     */
    public static LocalDate strToLocalDate(String date) {
        DateTimeFormatter formatter = getTimeFormatter(date);
        return LocalDate.parse(date, formatter);
    }

    /**
     * 判断时间个格式类型
     *
     * @param date 时间
     * @return 格式类型
     */
    private static DateTimeFormatter getTimeFormatter(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_STYLE);
        int length = date.length();
        if (length == 19) {
            formatter = DateTimeFormatter.ofPattern(DATE_TIME_STYLE);
        } else if (length == 23 && date.contains(".")) {
            formatter = DateTimeFormatter.ofPattern(DATE_TIME_STYLE_7);
        } else if (length == 20 && date.contains("T")) {
            formatter = DateTimeFormatter.ofPattern(UTC_TIME_STYLE);
        } else if (length == 12 && !date.contains("-")) {
            formatter = DateTimeFormatter.ofPattern(DATE_TIME_STYLE_0);
        } else if (length == 14 && !date.contains("-")) {
            formatter = DateTimeFormatter.ofPattern(DATE_TIME_STYLE_1);
        }
        return formatter;
    }

    /**
     * 时间String转LocalDateTime
     *
     * @param date 时间
     * @return LocalDateTime
     */
    public static LocalDateTime strToLocalDateTime(String date) {
        DateTimeFormatter formatter = getTimeFormatter(date);
        try {
            return LocalDateTime.parse(date, formatter);
        } catch (Exception e) {
            LOG.error("{}格式化错误:", date, e);
            return null;
        }
    }

    /**
     * 时间String格式化时间格式
     *
     * @param date 时间
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String parseDate(String date) {
        return parseDate(date, DateUtil.DATE_TIME_STYLE);
    }

    /**
     * 时间String格式化时间格式
     *
     * @param date        时间
     * @param formatStyle 时间格式
     * @return formatStyle格式的字符串
     */
    public static String parseDate(String date, String formatStyle) {
        LocalDateTime ldt = strToLocalDateTime(date);
        if (ldt == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatStyle);
        try {
            return ldt.format(formatter);
        } catch (Exception e) {
            LOG.error("{}格式化错误:", date, e);
            return null;
        }

    }

    /**
     * 中文时间替换标准时间
     *
     * @param date        时间
     * @param formatStyle 时间格式
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String parseChDate(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        int yIdx = date.indexOf("年");
        int mIdx = date.indexOf("月");
        int dIdx = date.indexOf("日");
        String replaceY = "-";
        if (mIdx - yIdx == 2) {
            replaceY = "-0";
        }
        String replaceM = "-";
        if (dIdx - mIdx == 2) {
            replaceM = "-0";
        }
        String newDate = date.replace("年", replaceY).replace("月", replaceM)
                .replace("日", "").replace("下午", " ").replace("上午", " ")
                .replace("时", ":").replace("分", ":").replace("秒", "");
        return newDate;

    }

    /**
     * String 转 Date
     *
     * @return Date
     */
    public static Date strToDate(String date) {
        LocalDateTime localDateTime = DateUtil.strToLocalDateTime(date);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取当前时间
     *
     * @param formatStyle 要格式化的时间
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getNowTime(String formatStyle) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern(formatStyle);
        return localDateTime.format(formatter2);
    }

    /**
     * 获取当前时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getNowTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern(DATE_TIME_STYLE);
        return localDateTime.format(formatter2);
    }

    /**
     * 获取当前时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static Date getNow() {
        LocalDateTime ldt = LocalDateTime.now();
        return asDate(ldt);
    }

    /**
     * 获取N天前或N天后的时间
     *
     * @param offsetDays 正数：往后N天，负数：往前N天
     * @return yyyy-MM-dd
     */
    public static String getOffsetDay(long offsetDays) {
        LocalDate yesterday = LocalDate.now().plusDays(offsetDays);
        return yesterday.toString();
    }

    /**
     * LocalDate 转 Date
     *
     * @return Date
     */
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDateTime 转 Date
     *
     * @return Date
     */
    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDate 转 Date
     *
     * @return Date
     */
    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Date 转 LocalDateTime
     *
     * @return Date
     */
    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 北京时间转UTC时间，计算了时区，比如-8小时
     *
     * @param localTime 要转的北京时间yyyy-MM-dd HH:mm:ss
     * @return UTC的yyyy-MM-dd'T'HH:mm:ss'Z'
     */
    public static String formatToUTC(String localTime) {
        try {
            // 传过来的可能是null字符串
            if (StringUtils.isEmpty(localTime) || localTime.equalsIgnoreCase("null")) {
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(UTC_TIME_STYLE);
            SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_STYLE);
            Date date = df.parse(localTime);
            Calendar cal = Calendar.getInstance();
            cal.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            cal.setTime(date);
            cal.add(Calendar.HOUR_OF_DAY, -8);
            date = cal.getTime();
            return sdf.format(date);
        } catch (Exception e) {
            LOG.info("北京时间转UTC时间异常", e);
        }
        return null;
    }
}