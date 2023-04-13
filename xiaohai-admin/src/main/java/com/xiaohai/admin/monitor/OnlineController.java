package com.xiaohai.admin.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.xiaohai.common.annotation.Log;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.OnLineUser;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.utils.OnLineUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * 在线用户Controller
 *
 * @author xiaohai
 * @since 2023-04-13
 */
@Tag(name = "在线用户", description = "在线用户")
@RestController
@RequestMapping("/monitor/online")
public class OnlineController {

    @Operation(summary = "在线用户", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @SaCheckPermission("monitor:online:list")
    @GetMapping()
    public Response<ReturnPageData<OnLineUser>> getOnLineUser() {
        return Response.success("获取在线用户列表成功！", OnLineUtils.getOnLineUserList());
    }

    @Operation(summary = "退出在线用户", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("monitor:online:delete")
    @Log(title = "退出在线用户")
    @DeleteMapping("{id}")
    public Response<String> kickOut(@PathVariable String id) {
        OnLineUtils.kickOut(id);
        return Response.success("退出在线用户成功！", id);
    }
}