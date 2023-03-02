package com.xiaohai.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * list工具
 *
 * @author wangchenghai
 * @date 2023/03/02 11:18:37
 */
@Slf4j
public class ListUtils {
    public static final String error = "【数据转换出错】";

    public static <T> T copyProperties(Object source, Class<T> target) {
        try {
            T t = target.newInstance();
            BeanUtils.copyProperties(source, t);
            return t;
        } catch (Exception e) {
            log.error(error, target.getName(), e);
            return null;
        }
    }

    /**
     * List类型转换
     * @param sourceList
     * @param target
     * @return
     * @param <T>
     */
    public static <T> List<T> copyWithCollection(List<?> sourceList, Class<T> target) {
        try {
            return sourceList.stream().map(s -> copyProperties(s, target)).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(error, target.getName(), e);
            return null;
        }
    }

    /**
     * Set类型转换
     * @param sourceList
     * @param target
     * @return
     * @param <T>
     */
    public static <T> Set<T> copyWithCollection(Set<?> sourceList, Class<T> target) {
        try {
            return sourceList.stream().map(s -> copyProperties(s, target)).collect(Collectors.toSet());
        } catch (Exception e) {
            log.error(error, target.getName(), e);
            return null;
        }
    }
}
