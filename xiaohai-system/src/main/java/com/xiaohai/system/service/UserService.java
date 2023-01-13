package com.xiaohai.system.service;

import com.xiaohai.system.pojo.entity.UserEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.common.daomain.ReturnPageData;

/**
 *
 * 用户表 服务类
 *
 *
 * @author xiaohai
 * @since 2023-01-13
 */
public interface UserService extends IService<UserEntity> {


    /**
     * 添加用户表
     *
     * @param userEntity 用户表
     * @return Integer
     */
    Integer add(UserEntity userEntity);

    /**
     * 删除用户表
     *
     * @param id 主键
     * @return Integer
     */
    Integer delete(Long id);

    /**
     * 修改用户表
     *
     * @param userEntity 用户表
     * @return Integer
     */
    Integer updateData(UserEntity userEntity);

    /**
     * id查询数据
     *
     * @param id id
     * @return   Integer
     */
    UserEntity findById(Long id);

    /**
    * 查询用户表列表数据
    *
    * @param userEntity 用户表
    * @return          Response
    */
    ReturnPageData<UserEntity> findListByPage(UserEntity userEntity);
}
