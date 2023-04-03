package com.xiaohai.note.service;

import com.xiaohai.note.pojo.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.note.pojo.query.CategoryQuery;
import com.xiaohai.note.pojo.vo.CategoryVo;
import com.xiaohai.note.pojo.dto.CategoryDto;

/**
 *
 * 分类表 服务类
 *
 *
 * @author xiaohai
 * @since 2023-04-03
 */
public interface CategoryService extends IService<Category> {


    /**
     * 添加分类表
     *
     * @param vo 分类表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer add(CategoryVo vo);

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
    Integer updateData(CategoryVo vo);

    /**
     * id查询数据
     *
     * @param id id
     * @return   Category
*/
    Category findById(Long id);

    /**
    * 查询分类表列表数据
    *
    * @param query 分类表 Query 数据查询对象
    * @return Response
    */
    ReturnPageData<CategoryDto> findListByPage(CategoryQuery query);
}
