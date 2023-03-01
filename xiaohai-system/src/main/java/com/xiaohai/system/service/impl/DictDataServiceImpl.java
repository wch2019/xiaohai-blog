package com.xiaohai.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.constant.RedisConstants;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.utils.DictUtils;
import com.xiaohai.common.utils.PageUtils;
import com.xiaohai.common.utils.RedisUtils;
import com.xiaohai.common.utils.Spring.SpringUtils;
import com.xiaohai.common.utils.StringUtils;
import com.xiaohai.system.dao.DictDataMapper;
import com.xiaohai.system.dao.DictTypeMapper;
import com.xiaohai.system.pojo.entity.DictData;
import com.xiaohai.system.pojo.query.DictDataQuery;
import com.xiaohai.system.pojo.vo.DictDataVo;
import com.xiaohai.system.service.DictDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 字典数据表 服务实现类
 *
 * @author xiaohai
 * @since 2023-02-03
 */
@Service
@RequiredArgsConstructor
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements DictDataService {

    @Override
    public Integer add(DictDataVo vo) {
        DictData dictData = new DictData();
        BeanUtils.copyProperties(vo, dictData);
        int row = baseMapper.insert(dictData);
        if (row > 0) {
            List<com.xiaohai.common.daomain.DictData> dictDatas = baseMapper.selectDictTypeList(dictData.getDictType());
            DictUtils.setDictCache(dictData.getDictType(), dictDatas);
        }
        return row;
    }

    @Override
    public Integer delete(Long[] ids) {
        for (Long id : ids) {
            DictData dictData = findById(id);
            baseMapper.deleteById(id);
            List<com.xiaohai.common.daomain.DictData> dictDatas = baseMapper.selectDictTypeList(dictData.getDictType());
            DictUtils.setDictCache(dictData.getDictType(), dictDatas);
        }
        return ids.length;
    }

    @Override
    public Integer updateData(DictDataVo vo) {
        DictData dictData = new DictData();
        BeanUtils.copyProperties(vo, dictData);
        int row = baseMapper.updateById(dictData);
        if (row > 0) {
            List<com.xiaohai.common.daomain.DictData> dictDatas = baseMapper.selectDictTypeList(dictData.getDictType());
            DictUtils.setDictCache(dictData.getDictType(), dictDatas);
        }
        return row;
    }

    @Override
    public DictData findById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<DictData> findListByPage(DictDataQuery query) {
        DictData dictData = new DictData();
        BeanUtils.copyProperties(query, dictData);
        IPage<DictData> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<DictData> iPage = baseMapper.selectPage(wherePage, Wrappers.query(dictData).last(" order by dict_sort asc"));
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        return ReturnPageData.fillingData(pageData, iPage.getRecords());
    }

    @Override
    public List<com.xiaohai.common.daomain.DictData> dictType(String dictType) {
        List<com.xiaohai.common.daomain.DictData> dictDatas = DictUtils.getDictCache(dictType);
        if (StringUtils.isNotEmpty(dictDatas)) {
            return dictDatas;
        }
        dictDatas = baseMapper.selectDictTypeList(dictType);
        if (StringUtils.isNotEmpty(dictDatas)) {
            DictUtils.setDictCache(dictType, dictDatas);
            return dictDatas;
        }
        return Collections.emptyList();
    }

    @Override
    public Map<String, List<com.xiaohai.common.daomain.DictData>> dictAll() {
        Map<String, List<com.xiaohai.common.daomain.DictData>> map = new HashMap<>();
        Collection<String> keys = SpringUtils.getBean(RedisUtils.class).keys(RedisConstants.SYS_DICT_KEY + "*");
        for (String key : keys) {
            key=key.replace(RedisConstants.SYS_DICT_KEY,"");
            List<com.xiaohai.common.daomain.DictData> dictDatas = DictUtils.getDictCache(key);
            if (StringUtils.isNotEmpty(dictDatas)) {
                map.put(key, dictDatas);
            } else {
                dictDatas = baseMapper.selectDictTypeList(key);
                if (StringUtils.isNotEmpty(dictDatas)) {
                    DictUtils.setDictCache(key, dictDatas);
                    map.put(key, dictDatas);
                }
            }
        }

        return map;
    }
}
