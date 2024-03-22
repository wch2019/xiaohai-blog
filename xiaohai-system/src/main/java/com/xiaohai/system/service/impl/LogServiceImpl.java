package com.xiaohai.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.constant.RedisConstants;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.utils.*;
import com.xiaohai.common.utils.Spring.SpringUtils;
import com.xiaohai.common.utils.ip.AddressUtils;
import com.xiaohai.system.dao.LogMapper;
import com.xiaohai.system.pojo.dto.LogDto;
import com.xiaohai.system.pojo.entity.Log;
import com.xiaohai.system.pojo.query.LogQuery;
import com.xiaohai.system.pojo.vo.LogVo;
import com.xiaohai.system.service.LogService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 系统日志 服务实现类
 *
 * @author xiaohai
 * @since 2023-03-30
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Override
    public Integer add(LogVo vo) {
        Log log = new Log();
        BeanUtils.copyProperties(vo, log);
        return baseMapper.insert(log);
    }

    @Override
    public Integer delete(Long[] ids) {
        for (Long id : ids) {
            baseMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    public Integer deleteAll() {
        return baseMapper.truncateTable();
    }

    @Override
    public Integer updateData(LogVo vo) {
        Log log = new Log();
        BeanUtils.copyProperties(vo, log);
        return baseMapper.updateById(log);
    }

    @Override
    public Log findById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<LogDto> findListByPage(LogQuery query) {
        Log log = new Log();
        BeanUtils.copyProperties(query, log);
        IPage<Log> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<Log> iPage = baseMapper.selectPage(wherePage, Wrappers.query(log).orderByDesc("created_time"));
        List<LogDto> list = new ArrayList<>();
        for (Log logs : iPage.getRecords()) {
            LogDto logDto = new LogDto();
            BeanUtils.copyProperties(logs, logDto);
            logDto.setOperIpAddress(AddressUtils.getIp2region(logs.getOperIp()));
            list.add(logDto);
        }
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        return ReturnPageData.fillingData(pageData, list);
    }

    @Override
    public Map<String, Object> getVisitWeek(Integer count) {
        //等于0默认查询redis
        if(count==0){
            // 从Redis中获取一周访问量
            Map<String, Object> weekVisitJson = SpringUtils.getBean(RedisUtils.class).getCacheObject(RedisConstants.WEEK_VISIT);
            if (weekVisitJson != null && !weekVisitJson.isEmpty()) {
                return MapUtils.objectToMap(weekVisitJson);
            }
        }
        // 获取当前时间结束时间
        String todayEndTime = DateUtils.getToDayEndTime();
        // 获取7天前的日期
        String sevenDays = DateUtils.getDateBefore(6);
        // 获取最近七天的数组列表
        List<String> sevenDaysList = DateUtils.getDateList(7);
        // 获得最近七天的网站访问量
        List<Map<String, Object>> pvMap = baseMapper.getPVByWeek(sevenDays, todayEndTime);
        // 获得最近七天的接口访问量
        List<Map<String, Object>> rcMap = baseMapper.getRCByWeek(sevenDays, todayEndTime);
        // 获得最近七天的独立用户
        List<Map<String, Object>> uvMap = baseMapper.getUVByWeek(sevenDays, todayEndTime);
        // 网站访问量数组
        List<Integer> pvList = new ArrayList<>();
        // 网站接口访问量
        List<Integer> rcList = new ArrayList<>();
        // 独立用户数组
        List<Integer> uvList = new ArrayList<>();
        Map<String, Object> countPVMap = new HashMap<>();
        Map<String, Object> countUVMap = new HashMap<>();
        Map<String, Object> countRCMap = new HashMap<>();

        for (Map<String, Object> item : pvMap) {
            countPVMap.put(item.get("date").toString(), item.get("count"));
        }
        for (Map<String, Object> item : rcMap) {
            countRCMap.put(item.get("date").toString(), item.get("count"));
        }
        for (Map<String, Object> item : uvMap) {
            countUVMap.put(item.get("date").toString(), item.get("count"));
        }
        Collections.reverse(sevenDaysList);
        for (String day : sevenDaysList) {
            Optional<Number> pvNumberOptional = Optional.ofNullable((Number) countPVMap.get(day));
            int pvNumber = pvNumberOptional.map(Number::intValue).orElse(0);
            pvList.add(pvNumber);

            Optional<Number> uvNumberOptional = Optional.ofNullable((Number) countUVMap.get(day));
            int uvNumber = uvNumberOptional.map(Number::intValue).orElse(0);
            uvList.add(uvNumber);

            Optional<Number> rcNumberOptional = Optional.ofNullable((Number) countRCMap.get(day));
            int rcNumber = rcNumberOptional.map(Number::intValue).orElse(0);
            rcList.add(rcNumber);
        }
        Map<String, Object> resultMap = new HashMap<>(3);
        resultMap.put("date", sevenDaysList);
        resultMap.put("pv", pvList);
        resultMap.put("uv", uvList);
        resultMap.put("rc", rcList);
        //缓存redis 10分钟
        SpringUtils.getBean(RedisUtils.class).setCacheObject(RedisConstants.WEEK_VISIT, resultMap, 10, TimeUnit.MINUTES);
        return resultMap;
    }
}
