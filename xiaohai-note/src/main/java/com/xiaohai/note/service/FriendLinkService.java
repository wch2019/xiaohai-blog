package com.xiaohai.note.service;

import com.xiaohai.note.pojo.entity.FriendLink;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.note.pojo.query.FriendLinkQuery;
import com.xiaohai.note.pojo.vo.FriendLinkVo;
import com.xiaohai.note.pojo.dto.FriendLinkDto;

import java.util.List;

/**
 *
 * 友情链接表 服务类
 *
 *
 * @author xiaohai
 * @since 2023-07-01
 */
public interface FriendLinkService extends IService<FriendLink> {


    /**
     * 添加友情链接表
     *
     * @param vo 友情链接表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer add(FriendLinkVo vo);

    /**
     * 删除友情链接表
     *
     * @param ids 主键
     * @return Integer
     */
    Integer delete(Long[] ids);

    /**
     * 修改友情链接表
     *
     * @param vo 友情链接表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer updateData(FriendLinkVo vo);

    /**
     * id查询数据
     *
     * @param id id
     * @return   FriendLink
*/
    FriendLink findById(Long id);

    /**
    * 查询友情链接表列表数据
    *
    * @param query 友情链接表 Query 数据查询对象
    * @return Response
    */
    ReturnPageData<FriendLinkDto> findListByPage(FriendLinkQuery query);

    /**
     * 查询友情链接数据
     * @return
     */
    List<FriendLinkDto> findList();
}
