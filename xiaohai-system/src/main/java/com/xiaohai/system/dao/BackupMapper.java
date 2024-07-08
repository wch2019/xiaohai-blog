package com.xiaohai.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wangchenghai
 * @date 2024/07/08 17:55:24
 */
@Mapper
public interface BackupMapper {
    @Select("SHOW TABLES")
    List<String> showTables();
}
