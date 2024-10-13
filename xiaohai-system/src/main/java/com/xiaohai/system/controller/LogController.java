package com.xiaohai.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.system.pojo.dto.LogDto;
import com.xiaohai.system.pojo.entity.Log;
import com.xiaohai.system.pojo.query.LogQuery;
import com.xiaohai.system.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

/**
 * 系统日志Controller
 *
 * @author xiaohai
 * @since 2023-03-30
 */
@Tag(name = "系统日志", description = "系统日志")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/log")
public class LogController {

    private final LogService logService;


    @Operation(summary = "删除系统日志", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("system:log:delete")
    @DeleteMapping("{ids}")
    public Response<Integer> delete(@PathVariable Long[] ids) {
        return Response.success("删除系统日志成功！", logService.delete(ids));
    }

    @Operation(summary = "清空系统日志", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("system:log:clean")
    @DeleteMapping("/all")
    public Response<Integer> deleteAll() {
        return Response.success("清空系统日志成功！", logService.deleteAll());
    }

    @Operation(summary = "id查询系统日志", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("{id}")
    public Response<Log> findById(@PathVariable Long id) {
        return Response.success("id查询系统日志成功！", logService.findById(id));
    }

    @Operation(summary = "查询系统日志列表数据", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @SaCheckPermission("system:log:list")
    @GetMapping()
    public Response<ReturnPageData<LogDto>> findListByPage(@ParameterObject LogQuery query) {
        return Response.success("查询系统日志列表成功！", logService.findListByPage(query));
    }

}
