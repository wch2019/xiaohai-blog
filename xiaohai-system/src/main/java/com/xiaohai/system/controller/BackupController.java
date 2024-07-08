package com.xiaohai.system.controller;

import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.system.service.BackupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangchenghai
 * @date 2024/07/08 17:57:06
 */
@Tag(name = "系统备份", description = "系统备份")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/backup")
public class BackupController {
    private final BackupService backupService;
    @Operation(summary = "备份", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PostMapping()
    public Response<Integer> add() {
        backupService.add();
        return Response.success("系统备份成功！");
    }
}
