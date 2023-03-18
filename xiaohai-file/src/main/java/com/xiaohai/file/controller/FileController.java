package com.xiaohai.file.controller;

import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.file.pojo.dto.FileDto;
import com.xiaohai.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author wangchenghai
 * @date 2023/03/16 14:58:46
 */
@Tag(name = "文件操作", description = "文件操作")
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @Operation(summary = "文件上传", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "file", description = "上传文件", required = true)
    @Parameter(name = "type", description = "上传类型 1图片，2头像", required = true)
    @PostMapping()
    public Response<String> upload(MultipartFile file,Integer type) {
        return Response.success("文件上传成功！", fileService.upload(file,type));
    }

    @Operation(summary = "文件列表", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "path", description = "路径", required = false)
    @GetMapping()
    public Response<List<FileDto>> getPathList(String path) {
        return Response.success("获取文件列表成功！", fileService.getPathList(path));
    }
}
