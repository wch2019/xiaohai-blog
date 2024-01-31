package com.xiaohai.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohai.file.pojo.entity.FileManager;

import java.util.List;

/**
 * <p>
 * 文件管理 Mapper 接口
 * </p>
 *
 * @author xiaohai
 * @since 2023-12-09
 */
public interface FileManagerMapper extends BaseMapper<FileManager> {

    /**
     * 根据id往下递归
     *
     * @param id
     * @return
     */
    List<FileManager> selectChildHierarchy(Integer id);

    /**
     * 获取所有文件数据信息
     *
     * @return
     */
    Long selectAllFileSize();

    /**
     * 获取指定用户总容量
     *
     * @param userId
     * @return
     */
    Long getTotalDiskSizeByUserId(Integer userId);

    /**
     * 获取指定用户文件数据信息
     * @param userId
     * @return
     */
    Long getUsedDiskSizeByUserId(Integer userId);


}
