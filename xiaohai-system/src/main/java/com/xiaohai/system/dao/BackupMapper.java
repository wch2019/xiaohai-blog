package com.xiaohai.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author wangchenghai
 * @date 2024/07/08 17:55:24
 */
@Mapper
public interface BackupMapper {
    @Select("SHOW TABLES")
    List<String> showTables();

    @Select("SHOW CREATE TABLE ${tableName}")
    Map<String, String> showCreateTable(String tableName);

    @Select("SELECT * FROM ${tableName}")
    List<Map<String, Object>> selectAll(String tableName);
}
