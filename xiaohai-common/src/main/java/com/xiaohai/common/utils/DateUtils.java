package com.xiaohai.common.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 时间处理工具类
 * @author: xiaohai
 * @date: 2023-03-18 09:32
 **/
public class DateUtils {
    /**
     * 将毫秒数转换为年月日可以使用Java中的时间日期类，例如LocalDateTime、Instant、Date等
     *
     * @param millis 毫秒数
     * @return  java.lang.String
     */
    public static String millisToDateTime(long millis) {
        // 转换为Instant对象
        Instant instant = Instant.ofEpochMilli(millis);
        // 转换为LocalDateTime对象
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        // 按指定格式转换为字符串
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 获取当前时间结束时间
     *
     * @return java.lang.String
     * @author xiaohai
     * @since 2023/7/5 14:14
     */
    public static String getToDayEndTime() {
        // 获取当前日期
        LocalDate today = LocalDate.now();
        // 设置时间为当天最大时间（即23:59:59.999999999
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        // 按指定格式转换为字符串
        return endOfDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 获取几天之前的日期
     *
     * @param daysAgo 指定的天数
     * @return java.time.LocalDateTime
     * @author xiaohai
     * @since 2023/7/5 13:56
     */
    public static String getDateBefore(int daysAgo) {
        // 获取当前日期
        LocalDate today = LocalDate.now();
        // 获取指定天数之前的日期
        LocalDate daysAgoDate = today.minusDays(daysAgo);
        // 最早时间（午夜时间）
        LocalTime earliestTime = LocalTime.MIN;
        // 组合日期和时间
        LocalDateTime earliestDateTime = LocalDateTime.of(daysAgoDate, earliestTime);

        // 按指定格式转换为字符串
        return earliestDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 获取指定天数日期列表
     * @param days 天
     * @return java.util.List<java.lang.String>
     * @author xiaohai
     * @since 2023/7/5 15:01
     */
    public static List<String> getDateList(int days) {
        List<String> recentDays = new ArrayList<>();

        for (int i = 0; i < days; i++) {
            LocalDate day = LocalDate.now().minusDays(i);
            recentDays.add(day.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        return recentDays;
    }
    /**
     * 字符串转LocalDateTime yyyy-MM-dd HH:mm:ss和yyyy.MM.dd HH:mm:ss
     * @param dateString
     * @return java.time.LocalDateTime
     * @author xiaohai
     * @since 2023/7/9 15:56
     */
    public static LocalDateTime getLocalDateTimeToString(String dateString ){
        String[] patterns = {"yyyy-MM-dd HH:mm:ss", "yyyy.MM.dd HH:mm:ss"};
        for (String pattern : patterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                return LocalDateTime.parse(dateString, formatter);
            } catch (DateTimeParseException e) {
                // Ignore and try the next pattern
            }
        }
        throw new IllegalArgumentException("Invalid date format: " + dateString);
    }

    public static void main(String[] args) {
        System.out.println(millisToDateTime(1625686800000L));
        System.out.println(getToDayEndTime());
        System.out.println(getDateBefore(6));
        System.out.println(getDateList(7));
    }
}
