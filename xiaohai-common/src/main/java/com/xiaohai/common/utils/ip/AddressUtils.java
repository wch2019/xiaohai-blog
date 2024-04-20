package com.xiaohai.common.utils.ip;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.xiaohai.common.utils.StringUtils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * 获取地址类
 *
 * @author xiaohai
 */
public class AddressUtils {
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    /**
     * IP地址查询
     */
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    // 未知地址
    public static final String UNKNOWN = "XX XX";

    /**
     * 第三方获取地址
     *
     * @param ip
     * @return
     */
    public static String getRealAddressByIP(String ip) {
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        try {
            String rspStr = HttpUtil.get(IP_URL + "?ip=" + ip + "&json=true");
            if (StringUtils.isEmpty(rspStr)) {
                log.error("获取地理位置异常 {}", ip);
                return UNKNOWN;
            }
            JSONObject obj = JSON.parseObject(rspStr);
            String region = obj.getString("pro");
            String city = obj.getString("city");
            return String.format("%s %s", region, city);
        } catch (Exception e) {
            log.error("获取地理位置异常 {}", ip);
        }
        return UNKNOWN;
    }


    /**
     * 通过ip2region获取地址
     *
     * @param ip
     * @return
     */
    public static String getIp2region(String ip) {
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        try {
            Resource resource = new ClassPathResource("ip2region.xdb");
            InputStream inputStream = resource.getInputStream();
            Searcher searcher = Searcher.newWithBuffer(inputStream.readAllBytes());
            long sTime = System.nanoTime();
            String region = searcher.search(ip);
            long cost = TimeUnit.NANOSECONDS.toMicros((long) (System.nanoTime() - sTime));
            log.info("region: {}, ioCount: {}, took: {} μs", region, searcher.getIOCount(), cost);
            searcher.close();
            return region;
        } catch (Exception e) {
            log.error("获取地理位置异常 {}", ip, e);
        }
        return UNKNOWN;
    }

    /**
     * 通过ip2region获取城市
     *
     * @param ip
     * @return
     */
    public static String getIp2regionCity(String ip) {
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        try {
            Resource resource = new ClassPathResource("ip2region.xdb");
            InputStream inputStream = resource.getInputStream();
            Searcher searcher = Searcher.newWithBuffer(inputStream.readAllBytes());
            long sTime = System.nanoTime();
            String region = searcher.search(ip);
            long cost = TimeUnit.NANOSECONDS.toMicros((long) (System.nanoTime() - sTime));
            log.info("region: {}, ioCount: {}, took: {} μs", region, searcher.getIOCount(), cost);
            searcher.close();
            return getDataAtIndex(region, 3);
        } catch (Exception e) {
            log.error("获取地理位置异常 {}", ip, e);
        }
        return UNKNOWN;
    }

    public static String getDataAtIndex(String input, int index) {
        // 使用 '|' 分割字符串
        String[] parts = input.split("\\|");

        // 检查索引是否有效，如果有效则返回对应位置的数据；否则返回空字符串
        if (index >= 0 && index < parts.length) {
            return parts[index];
        } else {
            return "";
        }
    }

    public static void main(String[] args) throws IOException {
        String ip = "144.0.53.222";
        System.out.println(getIp2region(ip));
        System.out.println(getRealAddressByIP(ip));
    }
}
