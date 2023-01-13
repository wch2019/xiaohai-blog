package com.xiaohai.common.utils;

import com.alibaba.fastjson.JSON;
import com.xiaohai.common.daomain.BaseEntity;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author wangchenghai
 * @date 2023/01/13 14:27:04
 */
public class MapUtils {

    /**
     * 对象转map
     *
     * @param object
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author wangchenghai
     * @date 2023/01/13 14:40:10
     */
    public static Map<String, Object> objectToMap(Object object) {
        return JSON.parseObject(JSON.toJSONString(object), Map.class);
    }

    /**
     * 去掉map中value为空的值
     * @author wangchenghai
     * @date  2023/01/13 15:08:55
     * @param param
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    public static Map<String, Object> removeMapKey(Map<String, Object> param) {
        Set set = param.keySet();

        for (Iterator iterator = set.iterator(); iterator.hasNext(); ) {
            String obj = String.valueOf(iterator.next());
            Object value = param.get(obj);
            if (value == null || value.equals("") || value.equals("null") || obj.length() == 0) {
                iterator.remove();
            }
        }

        return param;
    }

    public static void main(String[] args) {
        long s1 = System.currentTimeMillis();
        BaseEntity baseEntity=new BaseEntity();
        baseEntity.setUpdatedBy(2);
        Map<String, Object> dataMap = objectToMap(baseEntity);
        long s2 = System.currentTimeMillis();
        System.out.println("花费时间毫秒：" + (s2 - s1));
        System.out.println(removeMapKey(dataMap));
    }

}
