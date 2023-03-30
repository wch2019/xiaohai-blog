package com.xiaohai.system.service.impl;

import com.xiaohai.common.daomain.PageData;
import com.xiaohai.system.pojo.entity.Log;
import com.xiaohai.system.dao.LogMapper;
import com.xiaohai.system.service.LogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.ReturnPageData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.xiaohai.system.pojo.query.LogQuery;
import com.xiaohai.system.pojo.vo.LogVo;
import com.xiaohai.system.pojo.dto.LogDto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 系统日志 服务实现类
 *
 * @author xiaohai
 * @since 2023-03-30
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Override
    public Integer add(LogVo vo){
        Log log=new Log();
        BeanUtils.copyProperties(vo,log);
        return baseMapper.insert(log);
    }

    @Override
    public Integer delete(Long[] ids){
        for (Long id : ids) {
            baseMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    public Integer deleteAll() {
        return baseMapper.truncateTable();
    }

    @Override
    public Integer updateData(LogVo vo){
        Log log=new Log();
        BeanUtils.copyProperties(vo,log);
        return baseMapper.updateById(log);
    }

    @Override
    public Log findById(Long id){
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<LogDto> findListByPage(LogQuery query){
        Log log=new Log();
        BeanUtils.copyProperties(query,log);
        IPage<Log> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<Log> iPage = baseMapper.selectPage(wherePage,Wrappers.query(log).orderByDesc("created_time"));
        List<LogDto> list=new ArrayList<>();
        for(Log logs:iPage.getRecords()){
            LogDto logDto=new LogDto();
            BeanUtils.copyProperties(logs,logDto);
            list.add(logDto);
        }
        PageData pageData=new PageData();
        BeanUtils.copyProperties(iPage,pageData);
        return ReturnPageData.fillingData(pageData,list);
    }
}
