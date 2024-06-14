package com.xiaohai.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.server.Disk;
import com.xiaohai.file.pojo.dto.FileManagerDto;
import com.xiaohai.file.pojo.entity.FileManager;
import com.xiaohai.file.pojo.query.FileManagerQuery;
import com.xiaohai.file.pojo.vo.FileManagerNameVo;
import com.xiaohai.file.pojo.vo.FileManagerVo;

/**
 * 文件管理 服务类
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
    Integer deleteFile(Long[] ids);

    /**
     * 修改文件管理
     *
     * @param vo 文件管理 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer updateData(FileManagerVo vo);

    /**
     * 重命名文件
     *
     * @param vo 文件管理 VO（View Object）：显示层对象
     * @return Integer
     */
    String renameFile(FileManagerNameVo vo);

    /**
     * 根据hash和parentId查询
     *
     * @param hash hash
     * @return FileManager
     */
    FileManager findByHash(Integer parentId, String hash);

    /**
     * 查询文件管理列表数据
     *
     * @param query 文件管理 Query 数据查询对象
     * @return Response
     */
    ReturnPageData<FileManagerDto> findListByPage(FileManagerQuery query);

    /**
     * 查询指定路径
     *
     * @param path
     * @return
     */
    FileManager findByPath(String path);

    /**
     * 根据路径删除
     *
     * @param path
     * @return
     */
    Integer deletePath(String path);


    /**
     * 根据parentId分页获取
     *
     * @param parentId
     * @param isAsc    一个布尔值，表示排序方向。true 表示升序（ASC），false 表示降序（DESC）。
     * @return
     */
    ReturnPageData<FileManagerDto> getParentIdPath(Integer parentId, Boolean isAsc);

    /**
     * 获取当前系统硬盘使用情况
     *
     * @return
     */
    Disk getHardDiskSize();

    /**
     * 获取当前用户存储使用情况
     *
     * @return
     */
    Disk getUserHardDiskSize();

    /**
     * 获取指定用户存储使用情况
     *
     * @param userId
     * @return
     */
    Disk getUserHardDiskSize(Integer userId);

    /**
     * 判断用户是否存在文件
     *
     * @param userId
     * @return
     */
    Boolean getUserFileCount(Long userId);
}
