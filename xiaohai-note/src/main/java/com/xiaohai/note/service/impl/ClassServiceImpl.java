package com.xiaohai.note.service.impl;

import com.xiaohai.common.daomain.PageData;
import com.xiaohai.note.pojo.entity.Class;
import com.xiaohai.note.dao.ClassMapper;
import com.xiaohai.note.service.ClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.ReturnPageData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.xiaohai.note.pojo.query.ClassQuery;
import com.xiaohai.note.pojo.vo.ClassVo;
import com.xiaohai.note.pojo.dto.ClassDto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 分类表 服务实现类
 *
 * @author xiaohai
 * @since 2023-03-31
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {

    @Override
    public Integer add(ClassVo vo){
        Class class=new Class();
        BeanUtils.copyProperties(vo,class);
        return baseMapper.insert(class);
    }

    @Override
    public Integer delete(Long[] ids){
        for (Long id : ids) {
            baseMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    public Integer updateData(ClassVo vo){
        Class class=new Class();
        BeanUtils.copyProperties(vo,class);
        return baseMapper.updateById(class);
    }

    @Override
    public Class findById(Long id){
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<ClassDto> findListByPage(ClassQuery query){
        Class class=new Class();
        BeanUtils.copyProperties(query,class);
        IPage<Class> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<Class> iPage = baseMapper.selectPage(wherePage,Wrappers.query(class));
        List<ClassDto> list=new ArrayList<>();
        for(Class classs:iPage.getRecords()){
            ClassDto classDto=new ClassDto();
            BeanUtils.copyProperties(classs,classDto);
            list.add(classDto);
        }
        PageData pageData=new PageData();
        BeanUtils.copyProperties(iPage,pageData);
        return ReturnPageData.fillingData(pageData,list);
    }
}
