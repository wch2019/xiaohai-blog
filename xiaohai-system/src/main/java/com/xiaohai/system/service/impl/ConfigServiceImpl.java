package com.xiaohai.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.confing.FileConfig;
import com.xiaohai.system.dao.ConfigMapper;
import com.xiaohai.system.pojo.dto.ConfigDto;
import com.xiaohai.system.pojo.entity.Config;
import com.xiaohai.system.pojo.vo.ConfigVo;
import com.xiaohai.system.service.ConfigService;
import com.xiaohai.system.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 系统配置 服务实现类
 *
 * @author xiaohai
 * @since 2023-02-01
 */
@Service
@RequiredArgsConstructor
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {
    private final EmailService emailService;

    private final FileConfig fileConfig;

    @Override
    public Integer add(ConfigVo vo) {
        Config config = new Config();
        BeanUtils.copyProperties(vo, config);
        Integer count=baseMapper.insert(config);
        //刷新邮箱配置
        emailService.init();
        return count;
    }

    @Override
    public Integer updateData(ConfigVo vo) {
        Config config = new Config();
        BeanUtils.copyProperties(vo, config);
        Integer count=baseMapper.updateById(config);
        //刷新邮箱配置
        emailService.init();
        return count;
    }

    @Override
    public ConfigDto findByOne() {
        ConfigDto configDto=new ConfigDto();
        Config config=baseMapper.selectOne(new QueryWrapper<Config>().last("LIMIT 1"));
        BeanUtils.copyProperties(config,configDto);
        BeanUtils.copyProperties(fileConfig,configDto);

        return configDto;
    }
}
