package com.xiaohai.file.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.xiaohai.common.confing.FileConfig;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.constant.FileConstants;
import com.xiaohai.common.exception.ServiceException;
import com.xiaohai.common.utils.DateUtils;
import com.xiaohai.common.utils.FileUtils;
import com.xiaohai.common.utils.StringUtils;
import com.xiaohai.file.pojo.dto.FileDto;
import com.xiaohai.file.pojo.vo.UploadVo;
import com.xiaohai.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author wangchenghai
 * @date 2023/03/16 15:01:24
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
    private final FileConfig fileConfig;

    @Override
    public String uploadAvatar(MultipartFile file) {
        //根据用户区分文件夹
        String path = fileConfig.getFilePath() + StpUtil.getLoginId() + File.separator + FileConstants.AVATAR_FILE;
        return addFile(path, file);
    }

    @Override
    public String uploadLogo(MultipartFile file) {
        String path = fileConfig.getSystemPath();
        return addLogo(path, file);
    }

    @Override
    public String uploadImage(MultipartFile file) {
        //指定markdown图片上传目录,根据用户区分文件夹
        String path = fileConfig.getFilePath() + StpUtil.getLoginId() + File.separator + FileConstants.MARKDOWN_FILE + File.separator;
        return addFile(path, file);
    }

    @Override
    public Integer deleteImage(String pathName) {
        String path = fileConfig.getFilePath() + StpUtil.getLoginId() + File.separator + FileConstants.MARKDOWN_FILE + File.separator +pathName;
        boolean isTrue = FileUtils.deleteFile(path);
        Assert.isTrue(isTrue, "当前图片:" + pathName + ",删除失败");
        return 1;
    }

    /**
     * 添加文件
     *
     * @param path 文件保存路径
     * @param file 要添加的文件
     * @return 添加的文件路径
     */
    public String addFile(String path, MultipartFile file) {
        // 判断文件是否为空
        if (file == null || file.isEmpty()) {
            throw new ServiceException("文件为空");
        }

        // 获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;

        // 获取文件后缀名
        String fileExtension = FileUtils.getFileExtension(originalFilename);

        // 判断文件类型是否为图片
        if (!FileUtils.isImageExtension(fileExtension)) {
            throw new ServiceException("请查看图片类型是否正确" + Arrays.toString(FileConstants.IMAGE_EXTENSION));
        }

        // 生成唯一的文件名
        String fileName = FileUtils.generateUniqueFileName(fileExtension);

        // 保存文件并返回文件路径
        String filePath = FileUtils.saveFile(path, fileName, file);
        filePath = File.separator + filePath.replace(fileConfig.getProfile(), "");
        log.info("保存图片--------->{}", filePath);
        //前端展示需要处理
        return filePath.replace("\\", "/");
    }

    /**
     * 添加Logo
     *
     * @param path 文件保存路径
     * @param file 要添加的文件
     * @return 添加的文件路径
     */
    public String addLogo(String path, MultipartFile file) {
        // 判断文件是否为空
        if (file == null || file.isEmpty()) {
            throw new ServiceException("文件为空");
        }

        // 获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;

        // 获取文件后缀名
        String fileExtension = FileUtils.getFileExtension(originalFilename);

        // 判断文件类型是否为图片
        if (!FileUtils.isImageExtension(fileExtension)) {
            throw new ServiceException("请查看图片类型是否正确" + Arrays.toString(FileConstants.IMAGE_EXTENSION));
        }

        // 保存文件并返回文件路径
        String filePath = FileUtils.saveFile(path, FileConstants.LOGO, file);
        filePath = File.separator + filePath.replace(fileConfig.getProfile(), "");
        log.info("保存图片--------->{}", filePath);
        //前端展示需要处理
        return filePath.replace("\\", "/");
    }

    @Override
    public String upload(UploadVo vo) {
        MultipartFile file = vo.getFile();
        String path = fileConfig.getFilePath() + vo.getPath();
        // 判断文件是否为空
        if (file == null || file.isEmpty()) {
            throw new ServiceException("文件为空");
        }

        // 获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        //文件夹以年份/月份/分割
//        String folder = LocalDateTime.now().getYear() + "/" + LocalDateTime.now().getMonth() + "/";

        // 保存文件并返回文件路径
        String filePath = FileUtils.saveFile(path, originalFilename, file);

        filePath = File.separator + filePath.replace(fileConfig.getProfile(), "");
        log.info("保存图片--------->{}", filePath);
        //前端展示需要处理
        return filePath.replace("\\", "/");
    }

    @Override
    public List<FileDto> getPathList(String path) {
        List<FileDto> list = new ArrayList<>();
        // 指定文件夹路径
        String folderPath = fileConfig.getProfile() + path;
        File folder = new File(folderPath);
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            for (File file : files) {
                FileDto fileDto = new FileDto();
                fileDto.setNameSuffix("");
                fileDto.setPath(file.getPath().replaceAll("\\\\", "/").replace(fileConfig.getProfile(), ""));
                if (!file.isDirectory()) {
                    fileDto.setUpdateTime(DateUtils.millisToDateTime(file.lastModified()));
                    fileDto.setSize(FileUtils.formatFileSize(file.length()));
                    fileDto.setNameSuffix(FileUtils.getFileExtension(file.getName()));
                }
                fileDto.setCreateTime(FileUtils.fileCreationTime(file.getPath()));
                fileDto.setName(file.getName());
                list.add(fileDto);
            }
        }

        return list.stream().sorted(Comparator.comparing(FileDto::getNameSuffix)).toList();
    }

    @Override
    public Integer deletePath(String path) {
        path = fileConfig.getProfile() + path;
        boolean isTrue = FileUtils.deleteFile(path);
        Assert.isTrue(isTrue, "当前路径:" + path + ",删除失败");
        return 1;
    }


}
