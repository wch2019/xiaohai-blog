package com.xiaohai.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.system.dao.ConfigMapper;
import com.xiaohai.system.pojo.entity.Config;
import com.xiaohai.system.pojo.vo.ConfigVo;
import com.xiaohai.system.service.ConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 *
 * 系统配置 服务实现类
 *
 * @author xiaohai
 * @since 2023-02-01
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    @Override
    public Integer add(ConfigVo vo){
        Config config=new Config();
        BeanUtils.copyProperties(vo,config);
        return baseMapper.insert(config);
    }

    @Override
    public Integer updateData(ConfigVo vo){
        Config config=new Config();
        BeanUtils.copyProperties(vo,config);
        return baseMapper.updateById(config);
    }

    @Override
    public Config findByOne(){
        return baseMapper.selectOne(new QueryWrapper<Config>().last("LIMIT 1"));
    }
}
