package com.xiaohai.system.dao;

import com.xiaohai.system.pojo.entity.Log;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 系统日志 Mapper 接口
 * </p>
 *
 * @author xiaohai
 * @since 2023-03-30
 */
public interface LogMapper extends BaseMapper<Log> {

    /**
     * 清空表数据
     * @return
     */
    int truncateTable();


}
