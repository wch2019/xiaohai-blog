package com.xiaohai.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohai.common.daomain.DictDataEntity;
import com.xiaohai.system.pojo.entity.DictData;

import java.util.List;

/**
 * <p>
 * 字典数据表 Mapper 接口
 * </p>
 *
 * @author xiaohai
 * @since 2023-02-03
 */
public interface DictDataMapper extends BaseMapper<DictData> {
    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    List<DictDataEntity> selectDictTypeList(String dictType);

}
