package com.xiaohai.system.service;

import com.xiaohai.system.pojo.entity.Log;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.system.pojo.query.LogQuery;
import com.xiaohai.system.pojo.vo.LogVo;
import com.xiaohai.system.pojo.dto.LogDto;

import java.util.Map;

/**
 *
 * 系统日志 服务类
 *
 *
 * @author xiaohai
 * @since 2023-03-30
 */
public interface LogService extends IService<Log> {


    /**
     * 添加系统日志
     *
     * @param vo 系统日志 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer add(LogVo vo);

    /**
     * 删除系统日志
     *
     * @param ids 主键
     * @return Integer
     */
    Integer delete(Long[] ids);

    /**
     * 清空系统日志
     * @return
     */
    Integer deleteAll();

    /**
     * 修改系统日志
     *
     * @param vo 系统日志 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer updateData(LogVo vo);

    /**
     * id查询数据
     *
     * @param id id
     * @return   Log
*/
    Log findById(Long id);

    /**
    * 查询系统日志列表数据
    *
    * @param query 系统日志 Query 数据查询对象
    * @return Response
    */
    ReturnPageData<LogDto> findListByPage(LogQuery query);

    /**
     * 获取最近一周用户独立IP数和访问量
     *
     * @return {
     * date: ['2023-07-01', '2023-07-02', '2023-07-03', '2023-07-04', '2023-07-05', '2023-07-06', '2023-07-07'],
     * pv: [100, 120, 161, 134, 105, 160, 165],
     * uv: [120, 82, 91, 154, 162, 140, 145]
     * }
     */
    Map<String, Object> getVisitWeek(Integer count);
}
