package com.xiaohai.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.*;

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

    @Select("SELECT column_name FROM information_schema.columns WHERE table_schema = #{dbName} AND table_name = #{tableName} ORDER BY ordinal_position")
    List<String> getTableColumns(@Param("dbName") String dbName, @Param("tableName") String tableName);

    @Select("SELECT * FROM ${tableName}")
    List<LinkedHashMap<String, Object>> selectAll(String tableName);
}
