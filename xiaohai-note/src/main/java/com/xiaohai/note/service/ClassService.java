package com.xiaohai.note.service;

import com.xiaohai.note.pojo.entity.Class;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.note.pojo.query.ClassQuery;
import com.xiaohai.note.pojo.vo.ClassVo;
import com.xiaohai.note.pojo.dto.ClassDto;

/**
 *
 * 分类表 服务类
 *
 *
 * @author xiaohai
 * @since 2023-03-31
 */
public interface ClassService extends IService<Class> {


    /**
     * 添加分类表
     *
     * @param vo 分类表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer add(ClassVo vo);

    /**
     * 删除分类表
     *
     * @param ids 主键
     * @return Integer
     */
    Integer delete(Long[] ids);

    /**
     * 修改分类表
     *
     * @param vo 分类表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer updateData(ClassVo vo);

    /**
     * id查询数据
     *
     * @param id id
     * @return   Class
*/
    Class findById(Long id);

    /**
    * 查询分类表列表数据
    *
    * @param query 分类表 Query 数据查询对象
    * @return Response
    */
    ReturnPageData<ClassDto> findListByPage(ClassQuery query);
}
