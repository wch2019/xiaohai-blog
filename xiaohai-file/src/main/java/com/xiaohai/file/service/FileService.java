package com.xiaohai.file.service;

import com.xiaohai.file.pojo.dto.FileDto;
import com.xiaohai.file.pojo.dto.FileMarkdownDto;
import com.xiaohai.file.pojo.vo.UploadVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author wangchenghai
 * @date 2023/03/16 15:00:30
 */
public interface FileService {
    /**
     * 头像上传
     * @param file
     * @return
     */
    String uploadAvatar(MultipartFile file);

    /**
     * logo上传
     * @param file
     * @return
     */
    String uploadLogo(MultipartFile file);
    /**
     * markdown图片上传
     * @param file
     * @return
     */
    String uploadImage(MultipartFile file);

    /**
     * markdown图片删除
     * @param path
     * @return
     */
    Integer deleteImage(String path);
    /**
     * 上传文件
     * @param vo
     * @return
     */
    String upload(UploadVo vo);

    /**
     * 文件列表
     * @param path
     * @return
     */
    List<FileDto> getPathList(String path);

    /**
     * markdown图片列表
     * @return
     */
    List<FileMarkdownDto> getMarkdownImage();

    /**
     * 删除文件
     * @param path
     * @return
     */
    Integer deletePath(String path);
}
