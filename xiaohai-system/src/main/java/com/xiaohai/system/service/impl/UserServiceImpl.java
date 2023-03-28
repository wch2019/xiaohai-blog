package com.xiaohai.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.constant.RedisConstants;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.utils.EncryptUtils;
import com.xiaohai.common.utils.PageUtils;
import com.xiaohai.common.utils.RedisUtils;
import com.xiaohai.common.utils.Spring.SpringUtils;
import com.xiaohai.system.dao.RoleMapper;
import com.xiaohai.system.dao.UserMapper;
import com.xiaohai.system.pojo.dto.UserDto;
import com.xiaohai.system.pojo.entity.User;
import com.xiaohai.system.pojo.query.UserQuery;
import com.xiaohai.system.pojo.vo.EmailVo;
import com.xiaohai.system.pojo.vo.PasswordVo;
import com.xiaohai.system.pojo.vo.UserVo;
import com.xiaohai.system.service.MenuService;
import com.xiaohai.system.service.UserRoleService;
import com.xiaohai.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * 用户表 服务实现类
 *
 * @author xiaohai
 * @since 2023-01-29
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final RoleMapper roleMapper;
    private final UserRoleService userRoleService;
    private final MenuService menuService;

    @Override
    public Map<String,Object> findByInfo() {
        Map<String,Object> map=new HashMap<>(1);
        // 获取：当前账号所拥有的角色集合
        map.put("role", StpUtil.getRoleList());
        // 获取：当前账号所拥有的权限集合
        map.put("permission",StpUtil.getPermissionList());
        //获取当前用户菜单
        map.put("menu", menuService.routers());
        User user=baseMapper.selectById((Serializable) StpUtil.getLoginId());
        user.setPassword(null);
        //获取当前用户信息
        map.put("info", user);
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer add(UserVo vo) {
        Long countEmail = baseMapper.selectCount(new QueryWrapper<User>().eq("email", vo.getEmail()));
        Assert.isTrue(countEmail == 0, "新增邮箱：" + vo.getEmail() + "失败，邮箱已存在");
        Long countUser = baseMapper.selectCount(new QueryWrapper<User>().eq("username", vo.getUsername()));
        Assert.isTrue(countUser == 0, "新增用户：" + vo.getUsername() + "失败，账号已存在");
        User user = new User();
        BeanUtils.copyProperties(vo, user);
        var count = baseMapper.insert(user);
        //新增角色
        userRoleService.add(vo.getRoleIds(), user.getId());
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer delete(Long[] ids) {
        // 当前操作用户
        Object userId = StpUtil.getLoginId();
        for (Long id : ids) {
            Assert.isTrue(Objects.equals(userId, id), "不可删除当前登录用户");
            //删除角色
            userRoleService.delete(Math.toIntExact(id));
            //删除用户
            baseMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateData(UserVo vo) {
        //清空密码，此处不更新密码
        vo.setPassword(null);
        //清空邮箱，此处不更新邮箱
        vo.setEmail(null);
        // 当前操作用户
        User nowUser =  baseMapper.selectById((Serializable) StpUtil.getLoginId());
        if (nowUser.getUsername().equals(vo.getUsername()) && nowUser.getId().equals(vo.getId())) {
            User user = new User();
            BeanUtils.copyProperties(vo, user);
            //更新角色
            userRoleService.rewriteUserRole(vo.getRoleIds(), user.getId());
            return baseMapper.updateById(user);
        } else {
            Long count = baseMapper.selectCount(new QueryWrapper<User>().eq("username", vo.getUsername()).ne("id", vo.getId()));
            Assert.isTrue(count == 0, "更新用户：" + vo.getUsername() + "失败，账号已存在");
            User user = new User();
            BeanUtils.copyProperties(vo, user);
            //更新角色
            userRoleService.rewriteUserRole(vo.getRoleIds(), user.getId());
            return baseMapper.updateById(user);
        }
    }

    @Override
    public UserDto findById(Long id) {
        User user = baseMapper.selectById(id);
        user.setPassword(null);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setRoleIds(roleMapper.listByRoleIds(id));
        return userDto;
    }

    @Override
    public ReturnPageData<UserDto> findListByPage(UserQuery query) {
        User user = new User();
        BeanUtils.copyProperties(query, user);
        IPage<User> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<User> iPage = baseMapper.selectPage(wherePage, Wrappers.query(user));
        List<UserDto> list = new ArrayList<>();
        for (User users : iPage.getRecords()) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(users, userDto);
            userDto.setRoleIds(roleMapper.listByRoleIds(users.getId()));
            list.add(userDto);
        }
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        return ReturnPageData.fillingData(pageData, list);
    }

    @Override
    public Integer updatePwd(PasswordVo vo) {
        // 当前操作用户
        User nowUser =  baseMapper.selectById((Serializable) StpUtil.getLoginId());
        Assert.isTrue(!EncryptUtils.validate(vo.getOldPassword(),nowUser.getPassword()), "旧密码不对，请重新输入");
        nowUser.setPassword(EncryptUtils.aesEncrypt(vo.getNewPassword()));
        return baseMapper.updateById(nowUser);
    }

    @Override
    public Integer updateEmail(EmailVo vo) {
        String codeNumber = SpringUtils.getBean(RedisUtils.class).getCacheObject(RedisConstants.EMAIL_CODE + vo.getNewEmail());
        Assert.isTrue(vo.getCode().equals(codeNumber), "验证码不正确!");
        // 当前操作用户
        User nowUser =  baseMapper.selectById((Serializable) StpUtil.getLoginId());
        nowUser.setEmail(vo.getNewEmail());
        return baseMapper.updateById(nowUser);
    }
}
