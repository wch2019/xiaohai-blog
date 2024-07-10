package com.xiaohai.file.service;

import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.file.pojo.dto.FileManagerDto;
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
     *
     * @param file
     * @return
     */
    String uploadAvatar(MultipartFile file);

    /**
     * logo上传
     *
     * @param file
     * @return
     */
    String uploadLogo(MultipartFile file);

    /**
     * markdown图片上传
     *
     * @param file
     * @return
     */
    String uploadImage(MultipartFile file);

    //    /**
    //     * markdown图片删除
    //     *
    //     * @param path
    //     * @return
    //     */
    //    Integer deleteImage(String path);

    /**
     * Bing图片上传
     *
     * @param file
     * @return
     */
    String uploadBing(MultipartFile file, String path, String fileName);

    /**
     * 上传文件
     *
     * @param vo
     * @return
     */
    String upload(UploadVo vo);

    /**
     * 获取用户路径
     *
     * @return
     */
    String userPath();

    /**
     * 文件列表
     *
     * @param path
     * @return
     */
    ReturnPageData<FileManagerDto> getPathList(String path);

    /**
     * markdown图片列表
     *
     * @return
     */
    ReturnPageData<FileManagerDto> getMarkdownImageListByPage();

    /**
     * 仅支持markdown图片路径删除
     *
     * @param path
     * @return
     */
    Integer deletePathMarkdownImage(String path);

    /**
     * 如果文件夹不存在，则创建它，并存入数据库
     *
     * @param path
     * @return
     */
    void createFolderIfNotExists(String path);

    /**
     * 将图片复制到指定位置并重命名
     *
     * @param sourcePath 文件
     * @param newPath    新位置
     * @return
     */
    String getCopyImage(String sourcePath, String newPath);

    /**
     * markdown文件导入列表
     *
     * @return
     */
    List<FileManagerDto> getImportFiles();

    /**
     * markdown文件导出列表
     *
     * @return
     */
    List<FileManagerDto> getExportFiles();

    /**
     * 系统备份
     *
     * @return
     */
    List<FileManagerDto> getBackupFiles();
}
