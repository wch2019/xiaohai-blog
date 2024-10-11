package com.xiaohai.system.service;

import com.xiaohai.note.pojo.dto.UserBasicDto;
import com.xiaohai.system.pojo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.system.pojo.query.UserQuery;
import com.xiaohai.system.pojo.vo.EmailVo;
import com.xiaohai.system.pojo.vo.PasswordVo;
import com.xiaohai.system.pojo.vo.UserVo;
import com.xiaohai.system.pojo.dto.UserDto;

import java.util.Map;

/**
 *
 * 用户表 服务类
 *
 *
 * @author xiaohai
 * @since 2023-01-29
 */
public interface UserService extends IService<User> {

    /**
     * 获取用户登录信息
     * @return
     */
    Map<String,Object> findByInfo();

    /**
     * 获取用户文章信息
     * @return
     */
    UserBasicDto articleInfo();

    /**
     * 添加用户表
     *
     * @param vo 用户表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer add(UserVo vo);

    /**
     * 删除用户表
     *
     * @param ids 主键
     * @return Integer
     */
    Integer delete(Long[] ids);

    /**
     * 修改用户表
     *
     * @param vo 用户表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer updateData(UserVo vo);

    /**
     * id查询数据
     *
     * @param id id
     * @return   Integer
     */
    UserDto findById(Long id);

    /**
    * 查询用户表列表数据
    *
    * @param query 用户表 Query 数据查询对象
    * @return Response
    */
    ReturnPageData<UserDto> findListByPage(UserQuery query);

    /**
     * 修改密码
     * @param vo
     * @return
     */
    Integer updatePwd(PasswordVo vo);

    /**
     * 修改邮箱
     * @param vo
     * @return
     */
    Integer updateEmail(EmailVo vo);
}
