package com.xiaohai.common.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @description: 时间处理工具类
 * @author: xiaohai
 * @date: 2023-03-18 09:32
 **/
public class DateUtils {
    /**
     * 将毫秒数转换为年月日可以使用Java中的时间日期类，例如LocalDateTime、Instant、Date等
     * @param millis 毫秒数
     * @return
     */
    public static String millisToDateTime(long millis){
        // 转换为Instant对象
        Instant instant = Instant.ofEpochMilli(millis);
        // 转换为LocalDateTime对象
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        // 按指定格式转换为字符串
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    public static void main(String[] args) {
        System.out.println(millisToDateTime(1625686800000L));
    }
}
