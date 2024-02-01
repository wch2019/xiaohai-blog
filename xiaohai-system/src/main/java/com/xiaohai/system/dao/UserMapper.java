package com.xiaohai.system.dao;

import com.xiaohai.system.pojo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author xiaohai
 * @since 2023-01-29
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 获取排除指定用户所有总容量
     * @param userId
     * @return
     */
    Long getTotalDiskSizeExcludeUserId(Integer userId);

    /**
     * 获取指定用户文章数
     * @param userId
     * @return
     */
    Long getUserArticleCount(Long userId);

}
