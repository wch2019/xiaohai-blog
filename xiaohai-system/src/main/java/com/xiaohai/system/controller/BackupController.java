package com.xiaohai.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.xiaohai.common.annotation.Log;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.system.service.BackupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Operation(summary = "系统备份", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("system:tool:backup")
    @Log(title = "系统备份")
    @PostMapping()
    public Response<Integer> add() {
        backupService.backupDatabase();
        return Response.success("系统备份成功！");
    }

    @Operation(summary = "文件名称还原备份", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("system:tool:restore")
    @Log(title = "系统备份")
    @PostMapping("/restore/path/{fileName}")
    public Response<Integer> restoreFileName(@PathVariable String fileName) {
        backupService.restoreFileName(fileName);
        return Response.success("恢复系统成功！");
    }

    @Operation(summary = "上传还原备份文件", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PostMapping(value = "/restore/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<Integer> restoreBackupFile(MultipartFile file) {
        backupService.restoreBackupFile(file);
        return Response.success("恢复系统成功！");
    }
}
