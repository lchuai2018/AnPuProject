package com.example.anpuservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * FileName: cn.iyingke.platform.credit.utils.DateUtils.java
 * Author: Administrator
 * Description:
 */
@Slf4j
public class DateUtils {

    /**
     * yyyy/MM/dd
     */
    public static final String YMD_SLASH = "yyyy/MM/dd";

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String YMD_HMS = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String YMD_HM = "yyyy-MM-dd HH:mm";

    /**
     * yyyy-MM-dd
     */
    public static final String YMD_DASH = "yyyy-MM-dd";

    /**
     * yyyyMMddHHmmss
     */
    public static final String YMDHMS = "yyyyMMddHHmmss";
    /**
     * yyyyMMddHHmm
     */
    public static final String YMDHM = "yyyyMMddHHmm";

    /**
     * yyyyMMdd
     */
    public static final String YMD = "yyyyMMdd";

    /**
     * 格式化时间的原始方法
     *
     * @param date      日期
     * @param formatStr 格式
     * @return
     */
    public static String formatDate(Date date, String formatStr) {
        if (!StringUtils.hasText(formatStr) || date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        String result = null;
        try {
            result = format.format(date);
        } catch (Exception e) {

            return "";
        }
        return result;
    }

    public static Date parse(String dateStr, String formatStr) {
        if (dateStr == null || dateStr == "") {
            return null;
        }

        try {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            return format.parse(dateStr);
        } catch (ParseException e) {
//            log.error("date time parse error=======>");
//            return null;
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将日期的字符串形式按格式转换成日期
     *
     * @param source  日期格式字符串
     * @param pattern 日期格式
     * @return Date
     */
    public static Date parseDateTime(String source, String pattern) {

        if (source == null) {
            return null;
        }
        Date date;
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            date = format.parse(source);
        } catch (ParseException e) {
            e.getStackTrace();
            return null;
        }
        return date;
    }

    /**
     * 获取当前时间之前或之后几小时 hour
     *
     * @param hour
     * @return
     */
    public static String getTimeByHour(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    /**
     * <p>
     * Description: 将日期加上某些天或减去天数 <br />
     * </p>
     *
     * @param dateParam
     * @param day
     */
    public static Date dateFormatReturnDate(Date dateParam, int day) {
        Date retDate = null;
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(dateParam); // 设置当前日期
            c.add(Calendar.DAY_OF_MONTH, day); // -1天
            retDate = c.getTime(); // 结果
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retDate;

    }

    /**
     * 获取指定时间的时间戳（精确到毫秒）
     *
     * @param date
     * @return Long
     */
    public static Long getTimeStampsByDate(Date date) {
        return date.getTime();
    }

    /**
     * 将指定的时间戳字符串转换为日期格式
     *
     * @param timestampStr 时间戳字符串（精确到秒）
     * @return Date
     */
    public static Date timeStampToDate(String timestampStr) {
        Long timestamp = Long.parseLong(timestampStr) * 1000;
        return new Date(timestamp);
    }

    public static Date mStrTimeStampToDate(String millisecondStr) {
        if (millisecondStr == null) {
            return null;
        }
        Long timestamp = Long.parseLong(millisecondStr);
        return new Date(timestamp);
    }

    /**
     * 昨天的开始 时间戳  Sun Oct 22 00:00:00 CST 2017
     *
     * @return
     */
    public static Date yesterdayBeginTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }


    /**
     * 昨天的结束 时间戳
     *
     * @return
     */
    public static Date yesterdayEndTime() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DATE, -1);
        calendar1.set(Calendar.HOUR_OF_DAY, 23);
        calendar1.set(Calendar.MINUTE, 59);
        calendar1.set(Calendar.SECOND, 59);
        return calendar1.getTime();
    }

    /**
     * 将utc日期转换成年月日
     *
     * @param UTCString
     * @return
     */
    public static String UTCStringToDefaultString(String UTCString) {
        if (UTCString == null) {
            return null;
        }

        try {
            UTCString = UTCString.replace("Z", " UTC");
            SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = utcFormat.parse(UTCString);
            return defaultFormat.format(date);
        } catch (Exception pe) {
            pe.printStackTrace();
            return DateUtils.formatDate(new Date(),DateUtils.YMD_HMS);
        }
    }

    /**
     * 获取时间第一秒或最后一秒
     * @param dateTime
     * @param isFirstTime
     * @return
     */
    public static String getBeginTimeOrEndTime(String dateTime,Boolean isFirstTime){
        String dateStr = null;
        try {
            if (dateTime != null) {
                dateTime = DateUtils.formatDate(DateUtils.parse(dateTime,DateUtils.YMD_DASH),DateUtils.YMD_DASH);
                if (isFirstTime) {
                    dateStr = dateTime.concat(" 00:00:00");
                } else {
                    dateStr = dateTime.concat(" 23:59:59");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }



}

