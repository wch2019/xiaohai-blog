package com.xiaohai.system.controller;

import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.system.service.BackupService;
import io.swagger.v3.oas.annotations.Operation;
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
    @PostMapping()
    public Response<Integer> add() {
        backupService.backupDatabase();
        return Response.success("系统备份成功！");
    }

    @Operation(summary = "路径还原备份", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PutMapping("/restore/path/{filePath}")
    public Response<Integer> restore(@PathVariable String filePath) {
        backupService.restore(filePath);
        return Response.success("恢复系统成功！");
    }

    @Operation(summary = "上传还原备份文件", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PostMapping(value = "/restore/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<Integer> restore(MultipartFile file) {
        backupService.backupFile(file);
        return Response.success("恢复系统成功！");
    }
}
