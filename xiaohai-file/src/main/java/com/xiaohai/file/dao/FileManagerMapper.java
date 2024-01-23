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
     * @param id
     * @return
     */
    List<FileManager> selectChildHierarchy(Integer id);
}
