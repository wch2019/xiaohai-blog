package com.xiaohai.system.service;

import com.xiaohai.system.pojo.entity.DictData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.system.pojo.query.DictDataQuery;
import com.xiaohai.system.pojo.vo.DictDataVo;
import com.xiaohai.system.pojo.dto.DictDataDto;

/**
 *
 * 字典数据表 服务类
 *
 *
 * @author xiaohai
 * @since 2023-02-03
 */
public interface DictDataService extends IService<DictData> {


    /**
     * 添加字典数据表
     *
     * @param vo 字典数据表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer add(DictDataVo vo);

    /**
     * 删除字典数据表
     *
     * @param id 主键
     * @return Integer
     */
    Integer delete(Long id);

    /**
     * 修改字典数据表
     *
     * @param vo 字典数据表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer updateData(DictDataVo vo);

    /**
     * id查询数据
     *
     * @param id id
     * @return   DictData
*/
    DictData findById(Long id);

    /**
    * 查询字典数据表列表数据
    *
    * @param query 字典数据表 Query 数据查询对象
    * @return Response
    */
    ReturnPageData<DictDataDto> findListByPage(DictDataQuery query);
}
