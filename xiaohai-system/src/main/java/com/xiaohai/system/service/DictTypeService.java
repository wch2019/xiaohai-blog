package com.xiaohai.system.service;

import com.xiaohai.system.pojo.entity.DictType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.system.pojo.query.DictTypeQuery;
import com.xiaohai.system.pojo.vo.DictTypeVo;
import com.xiaohai.system.pojo.dto.DictTypeDto;

import java.util.List;

/**
 *
 * 字典类型表 服务类
 *
 *
 * @author xiaohai
 * @since 2023-01-29
 */
public interface DictTypeService extends IService<DictType> {


    /**
     * 添加字典类型表
     *
     * @param vo 字典类型表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer add(DictTypeVo vo);

    /**
     * 删除字典类型表
     *
     * @param ids 主键
     * @return Integer
     */
    Integer delete(Long[] ids);

    /**
     * 修改字典类型表
     *
     * @param vo 字典类型表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer updateData(DictTypeVo vo);

    /**
     * id查询数据
     *
     * @param id id
     * @return   Integer
     */
    DictType findById(Long id);

    /**
    * 查询字典类型表列表数据
    *
    * @param query 字典类型表 Query 数据查询对象
    * @return Response
    */
    ReturnPageData<DictType> findListByPage(DictTypeQuery query);

    /**
     * 重置字典缓存数据
     */
    void refreshDict();

    /**
     * 获取字典选择框列表
     * @return
     */
    List<DictTypeDto> optionSelect();
}
