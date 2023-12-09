package com.xiaohai.file.service;

import com.xiaohai.file.pojo.entity.FileManager;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.file.pojo.query.FileManagerQuery;
import com.xiaohai.file.pojo.vo.FileManagerVo;
import com.xiaohai.file.pojo.dto.FileManagerDto;

/**
 *
 * 文件管理 服务类
 *
 *
 * @author xiaohai
 * @since 2023-12-09
 */
public interface FileManagerService extends IService<FileManager> {


    /**
     * 添加文件管理
     *
     * @param vo 文件管理 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer add(FileManagerVo vo);

    /**
     * 删除文件管理
     *
     * @param ids 主键
     * @return Integer
     */
    Integer delete(Long[] ids);

    /**
     * 修改文件管理
     *
     * @param vo 文件管理 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer updateData(FileManagerVo vo);

    /**
     * id查询数据
     *
     * @param id id
     * @return   FileManager
*/
    FileManager findById(Long id);

    /**
    * 查询文件管理列表数据
    *
    * @param query 文件管理 Query 数据查询对象
    * @return Response
    */
    ReturnPageData<FileManagerDto> findListByPage(FileManagerQuery query);
}
