package com.xiaohai.note.service;

import com.xiaohai.note.pojo.entity.Tags;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.note.pojo.query.TagsQuery;
import com.xiaohai.note.pojo.vo.TagsVo;
import com.xiaohai.note.pojo.dto.TagsDto;

import java.util.List;

/**
 *
 * 标签表 服务类
 *
 *
 * @author xiaohai
 * @since 2023-03-31
 */
public interface TagsService extends IService<Tags> {


    /**
     * 添加标签表
     *
     * @param vo 标签表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer add(TagsVo vo);

    /**
     * 删除标签表
     *
     * @param ids 主键
     * @return Integer
     */
    Integer delete(Long[] ids);

    /**
     * 修改标签表
     *
     * @param vo 标签表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer updateData(TagsVo vo);

    /**
     * id查询数据
     *
     * @param id id
     * @return   Tags
*/
    Tags findById(Long id);

    /**
    * 查询标签表列表数据
    *
    * @param query 标签表 Query 数据查询对象
    * @return Response
    */
    ReturnPageData<TagsDto> findListByPage(TagsQuery query);

    /**
     * 查询标签选择列表
     * @return
     */
    List<TagsDto> optionSelect();

    /**
     * 查询标签展示列表(包含对应文章数)
     * @return
     */
    List<TagsDto> getTagsArticleCount();
}
