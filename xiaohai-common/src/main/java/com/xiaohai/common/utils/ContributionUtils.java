package com.xiaohai.common.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.xiaohai.common.constant.RedisConstants;
import com.xiaohai.common.daomain.Contribution;
import com.xiaohai.common.daomain.OnLineUser;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.utils.Spring.SpringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 持续创作管理户工具类
 *
 * @author xiaohai
 */
public class ContributionUtils {

    /**
     * 设置持续创作管理缓存
     *
     */
    public static void setContribution() {
        //获取今年有没有
        Contribution contribution=getContribution();
        if(contribution.getYear()==0){
            //没有就初始化一个数据
            contribution.setYear(LocalDate.now().getYear());
            contribution.setTime(LocalDate.now());
            contribution.setLongest(1);
            contribution.setContinuous(1);
        }else{
            //已经记录过跳出
            if(contribution.getTime().equals(LocalDate.now())){
                return;
            }
            // 一天前
            LocalDate beforeTenDays = LocalDate.now().minus(1, ChronoUnit.DAYS);
            if(contribution.getTime()==beforeTenDays){
                contribution.setContinuous(contribution.getContinuous()+1);
                if(contribution.getLongest()<contribution.getContinuous()){
                    contribution.setLongest(contribution.getContinuous());
                }
            }
        }
        SpringUtils.getBean(RedisUtils.class).setCacheObject(getCacheKey(String.valueOf(contribution.getYear())), contribution);
    }

    /**
     * 获取持续创作管理缓存
     *
     * @return onLineUser 持续创作管理数据
     */
    public static Contribution getContribution() {
        int year=LocalDate.now().getYear();
        Object cacheObj = SpringUtils.getBean(RedisUtils.class).getCacheObject(getCacheKey(String.valueOf(year)));
        if (StringUtils.isNotNull(cacheObj)) {
            return StringUtils.cast(cacheObj) ;
        }
        return new Contribution();
    }

    /**
     * 删除指定持续创作管理
     *
     * @param key 持续创作管理键
     */
    public static void removeContribution(String key) {
        SpringUtils.getBean(RedisUtils.class).deleteObject(getCacheKey(key));
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    public static String getCacheKey(String configKey) {
        return RedisConstants.CONTRIBUTION + configKey;
    }
}
