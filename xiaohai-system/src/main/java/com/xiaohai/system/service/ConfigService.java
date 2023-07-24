package com.xiaohai.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.system.pojo.dto.ConfigDto;
import com.xiaohai.system.pojo.dto.ConfigShowDto;
import com.xiaohai.system.pojo.entity.Config;
import com.xiaohai.system.pojo.vo.ConfigVo;

/**
 *
 * 系统配置 服务类
 *
 *
 * @author xiaohai
 * @since 2023-02-01
 */
public interface ConfigService extends IService<Config> {


    /**
     * 添加系统配置
     *
     * @param vo 系统配置 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer add(ConfigVo vo);

    /**
     * 修改系统配置
     *
     * @param vo 系统配置 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer updateData(ConfigVo vo);

    /**
     * 查询系统配置
     *
     * @return   Integer
     */
    ConfigDto findByOne();

    /**
     * 查询展示页所需数据
     *
     * @return   Integer
     */
    ConfigShowDto findByShowOne();
}
