package com.xiaohai.file.controller;

import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.file.pojo.dto.FileDto;
import com.xiaohai.file.pojo.vo.UploadVo;
import com.xiaohai.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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
    @Operation(summary = "头像上传", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<String> uploadAvatar(MultipartFile avatarFile) {
        return Response.success("头像上传成功！", fileService.uploadAvatar(avatarFile));
    }
    @Operation(summary = "logo上传", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PostMapping(value = "/logo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<String> uploadLogo(MultipartFile logoFile) {
        return Response.success("logo上传成功！", fileService.uploadLogo(logoFile));
    }
    @Operation(summary = "markdown图片上传", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<String> uploadImage(MultipartFile file) {
        return Response.success("图片上传成功！", fileService.uploadImage(file));
    }
    @Operation(summary = "markdown图片删除", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "path", description = "图片名称", required = true)
    @DeleteMapping(value = "/image")
    public Response<Integer> deleteImage(String path) {
        return Response.success("图片删除成功！", fileService.deleteImage(path));
    }
    @Operation(summary = "文件上传", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<String> upload(@ModelAttribute UploadVo vo) {
        return Response.success("文件上传成功！", fileService.upload(vo));
    }
    @Operation(summary = "文件列表", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "path", description = "路径", required = false)
    @GetMapping()
    public Response<List<FileDto>> getPathList(String path) {
        return Response.success("获取文件列表成功！", fileService.getPathList(path));
    }
    @Operation(summary = "文件删除", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "path", description = "路径", required = true)
    @DeleteMapping()
    public Response<Integer> deletePath(String path) {
        return Response.success("获取文件列表成功！", fileService.deletePath(path));
    }
}
