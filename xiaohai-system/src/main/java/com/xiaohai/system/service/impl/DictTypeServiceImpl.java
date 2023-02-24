package com.xiaohai.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.exception.ServiceException;
import com.xiaohai.common.utils.DictUtils;
import com.xiaohai.system.dao.DictDataMapper;
import com.xiaohai.system.pojo.entity.DictData;
import com.xiaohai.system.pojo.entity.DictType;
import com.xiaohai.system.dao.DictTypeMapper;
import com.xiaohai.system.service.DictTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.ReturnPageData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.xiaohai.system.pojo.query.DictTypeQuery;
import com.xiaohai.system.pojo.vo.DictTypeVo;
import com.xiaohai.system.pojo.dto.DictTypeDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典类型表 服务实现类
 *
 * @author xiaohai
 * @since 2023-01-29
 */
@Service
@RequiredArgsConstructor
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType> implements DictTypeService {

    private final DictDataMapper dictDataMapper;

    @Override
    public Integer add(DictTypeVo vo) {
        long count = baseMapper.selectCount(new QueryWrapper<DictType>().eq("dict_type", vo.getDictType()));
        Assert.isTrue(count != 1, "新增字典'" + vo.getDictType() + "'失败，字典类型已存在");
        DictType dictType = new DictType();
        BeanUtils.copyProperties(vo, dictType);
        int row = baseMapper.insert(dictType);
        if (row > 0) {
            DictUtils.setDictCache(dictType.getDictType(), null);
        }
        return row;
    }

    @Override
    public Integer delete(Long[] ids) {
        for (long id : ids) {
            DictType dictType = baseMapper.selectById(id);
            long count = dictDataMapper.selectCount(new QueryWrapper<DictData>().eq("dict_type", dictType.getDictType()));
            if (count > 0) {
                throw new ServiceException("字典'" + dictType.getDictType() + "'已分配,不能删除");
            }
            baseMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    @Transactional
    public Integer updateData(DictTypeVo vo) {
        long count = baseMapper.selectCount(new QueryWrapper<DictType>().eq("dict_type", vo.getDictType()).ne("id",vo.getId()));
        Assert.isTrue(count != 1, "更新字典'" + vo.getDictType() + "'失败，字典类型已存在");
        DictType oldDictType = baseMapper.selectById(vo.getId());
        DictData dictData=new DictData();
        dictData.setDictType(vo.getDictType());
        dictDataMapper.update(dictData,new QueryWrapper<DictData>().eq("dict_type",oldDictType.getDictType()));
        DictType dictType = new DictType();
        BeanUtils.copyProperties(vo, dictType);
        return baseMapper.updateById(dictType);
    }

    @Override
    public DictType findById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<DictTypeDto> findListByPage(DictTypeQuery query) {
        DictType dictType = new DictType();
        BeanUtils.copyProperties(query, dictType);
        IPage<DictType> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<DictType> iPage = baseMapper.selectPage(wherePage, Wrappers.query(dictType));
        List<DictTypeDto> list = new ArrayList<>();
        for (DictType dictTypes : iPage.getRecords()) {
            DictTypeDto dictTypeDto = new DictTypeDto();
            BeanUtils.copyProperties(dictTypes, dictTypeDto);
            list.add(dictTypeDto);
        }
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        return ReturnPageData.fillingData(pageData, list);
    }
}
