package com.xiaohai.common.utils;

import java.util.Collection;
import java.util.UUID;

/**
 * @author wangchenghai
 * @date 2023/02/24 17:33:38
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * 判断字符串是否为空
     *
     * @param str  字符串
     * @return boolean
     * @author sc
     * @date 2021/12/21 10:21
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     * @author wangchenghai
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     * @author wangchenghai
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * * 判断一个Collection是否为空， 包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * * 判断一个Collection是否非空，包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj)
    {
        return (T) obj;
    }

    /**
     * 生成没有-的uuid
     * @return java.lang.String
     * @author xiaohai
     * @since 2023/7/8 8:32
     */
    public static String generateUUIDWithoutHyphens() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        return uuidString.replace("-", "");
    }
}
