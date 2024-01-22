package com.xiaohai.file.controller;

import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.file.pojo.dto.FileManagerDto;
import com.xiaohai.file.pojo.vo.FileManagerNameVo;
import com.xiaohai.file.pojo.vo.FileManagerVo;
import com.xiaohai.file.pojo.vo.FolderVO;
import com.xiaohai.file.pojo.vo.UploadVo;
import com.xiaohai.file.service.FileManagerService;
import com.xiaohai.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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

    private final FileManagerService fileManagerService;

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

    @Operation(summary = "文件上传", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<String> upload(@ModelAttribute UploadVo vo) {
        return Response.success("文件上传成功！", fileService.upload(vo));
    }

    @Operation(summary = "文件列表", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "path", description = "路径", required = false)
    @GetMapping()
    public Response<ReturnPageData<FileManagerDto>> getPathList(String path) {
        return Response.success("获取文件列表成功！", fileService.getPathList(path));
    }

    //    @Operation(summary = "文件夹详情", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    //    @Parameter(name = "path", description = "路径", required = false)
    //    @GetMapping()
    //    public Response<ReturnPageData<FileManagerDto>> getPathList(String path) {
    //        return Response.success("获取文件列表成功！", fileService.getPathList(path));
    //    }
    @Operation(summary = "markdown图片列表", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @GetMapping(value = "/markdownImage")
    public Response<ReturnPageData<FileManagerDto>> getMarkdownImageListByPage() {
        return Response.success("获取markdown图片列表成功！", fileService.getMarkdownImageListByPage());
    }

    @Operation(summary = "文件删除", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "path", description = "路径", required = true)
    @DeleteMapping()
    public Response<Integer> deletePath(String path) {
        return Response.success("删除成功！", fileService.deletePath(path));
    }

    @Operation(summary = "根据id删除文件", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @DeleteMapping("{ids}")
    public Response<Integer> deleteFile(@PathVariable Long[] ids) {
        return Response.success("删除成功！", fileManagerService.deleteFile(ids));
    }

    @Operation(summary = "重命名文件", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PutMapping("/renameFile")
    public Response<Integer> renameFile(@RequestBody @Validated FileManagerNameVo vo) {
        return Response.success("重命名文件成功！", fileManagerService.renameFile(vo));
    }

    @Operation(summary = "新建文件夹", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PostMapping(value = "/newFolder")
    public Response<Integer> newFolder(@RequestBody @Validated FolderVO vo) {
        var path = vo.getPath().replace("/", File.separator);
        fileService.createFolderIfNotExists(path.substring(1));
        return Response.success("新建文件夹成功！", 1);
    }
}
