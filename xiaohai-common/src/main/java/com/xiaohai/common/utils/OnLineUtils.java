package com.xiaohai.common.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.xiaohai.common.constant.RedisConstants;
import com.xiaohai.common.daomain.DictDataEntity;
import com.xiaohai.common.daomain.OnLineUser;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.utils.Spring.SpringUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 在线用户工具类
 *
 * @author xiaohai
 */
public class OnLineUtils {

    /**
     * 设置在线用户缓存
     *
     * @param key       参数键
     * @param onLineUser 在线用户数据
     */
    public static void setOnLineCache(String key, OnLineUser onLineUser) {
        SpringUtils.getBean(RedisUtils.class).setCacheObject(getCacheKey(key), onLineUser,onLineUser.getTime(),TimeUnit.SECONDS);
    }

    /**
     * 获取在线用户缓存
     *
     * @param key 参数键
     * @return onLineUser 在线用户数据
     */
    public static OnLineUser getOnLineCache(String key) {
        Object cacheObj = SpringUtils.getBean(RedisUtils.class).getCacheObject(getCacheKey(key));
        if (StringUtils.isNotNull(cacheObj)) {
            return StringUtils.cast(cacheObj) ;
        }
        return null;
    }

    /**
     * 获取所有用户数据(手动分页)
     * @return
     */
    public static ReturnPageData<OnLineUser>getOnLineUserList() {
        Collection<String> keys = SpringUtils.getBean(RedisUtils.class).keys(RedisConstants.ON_LINE_USER + "*");
        PageData pageData = new PageData();
        long pageNo=PageUtils.getPageNo();
        if(pageNo==0){
            pageNo=1;
        }
        pageData.setTotal(pageNo);
        pageData.setSize(PageUtils.getPageSize());
        pageData.setCurrent(keys.size());
        //java8 使用stream api进行分页
        List<String> list=keys.stream().skip((pageNo-1)*pageData.getSize()).limit(pageData.getSize()).toList();
        List<OnLineUser> onLineUsers=new ArrayList<>();
        for (String key : list) {
            key=key.replace(RedisConstants.ON_LINE_USER,"");
            OnLineUser onLineUser = getOnLineCache(key);
            if (onLineUser!=null) {
                onLineUsers.add(onLineUser);
            }
        }
        return ReturnPageData.fillingData(pageData, onLineUsers);
    }

    /**
     * 删除指定在线用户
     *
     * @param key 在线用户键
     */
    public static void removeOnLineCache(String key) {
        SpringUtils.getBean(RedisUtils.class).deleteObject(getCacheKey(key));
    }

    /**
     * 踢人下线
     * @param key
     */
    public static void kickOut(String key) {
        StpUtil.kickout(key);
        removeOnLineCache(key);
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    public static String getCacheKey(String configKey) {
        return RedisConstants.ON_LINE_USER + configKey;
    }
}
