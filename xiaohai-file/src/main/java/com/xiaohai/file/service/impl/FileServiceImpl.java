package com.xiaohai.file.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.xiaohai.common.confing.FileConfig;
import com.xiaohai.common.constant.Constants;
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
        String path = fileConfig.getAvatarPath() + StpUtil.getLoginId() + "/";
        return addFile(path, file);
    }

    @Override
    public String uploadImage(MultipartFile file) {
        //指定markdown图片上传目录,根据用户区分文件夹
        String path = fileConfig.getImagePath() + StpUtil.getLoginId() + "/";
        return addFile(path, file);
    }

    @Override
    public Integer deleteImage(String pathName) {
        String path = fileConfig.getImagePath() + StpUtil.getLoginId() + "/" + pathName;
        boolean isTrue = FileUtils.deleteFile(path);
        Assert.isTrue(isTrue, "当前图片:" + pathName + ",删除失败");
        return 1;
    }

    /**
     * 图片文件上传
     *
     * @param path
     * @param file
     * @return
     */
    public String addFile(String path, MultipartFile file) {
        //文件地址初始化
        String filePath = "";
        //首先判断不是空的文件
        if (file == null || StringUtils.isBlank(file.getOriginalFilename())) {
            throw new ServiceException("文件为空");
        }
        var filename = file.getOriginalFilename();
        //对文文件的全名进行截取然后在后缀名进行删选。
        int begin = filename.indexOf(".");
        int last = filename.length();
        //获得文件后缀名
        String a = filename.substring(begin + 1, last);
        //判断指定文件后缀类型
        if (Arrays.stream(Constants.IMAGE_EXTENSION).toList().contains(a.toLowerCase())) {
            //为了不重复，时间戳作为图片名称
            String fileNameString = System.currentTimeMillis() + "." + a;
            File savedFile = new File(path, fileNameString);
            try {
                //去掉前缀
                filePath = savedFile.getPath().replaceAll("\\\\", "/").replace(fileConfig.getProfile(), "");
                log.info("保存图片--------->" + "/" + filePath);
                FileUtils.copyInputStreamToFile(file.getInputStream(), savedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new ServiceException("请查看图片类型是否正确" + Arrays.toString(Constants.IMAGE_EXTENSION));
        }
        return "/" + filePath;
    }

    @Override
    public String upload(UploadVo vo) {
        MultipartFile file = vo.getFile();
        String path = fileConfig.getFilePath()+ StpUtil.getLoginId() + "/" + vo.getPath();
        //文件地址初始化
        String filePath = "";
        //首先判断不是空的文件
        if (file == null || StringUtils.isBlank(file.getOriginalFilename())) {
            throw new ServiceException("文件为空");
        }
        //获取文件全名
        file.getOriginalFilename();
        //对文文件的全名进行截取然后在后缀名进行删选。
        int begin = Objects.requireNonNull(file.getOriginalFilename()).indexOf(".");
        int last = file.getOriginalFilename().length();
        //获得文件后缀名
        String a = file.getOriginalFilename().substring(begin, last);
        //我这边需要的jpg文件所以说我这边直接判断就是了
        if (a.endsWith(".jpg") || a.endsWith(".png")) {
            //文件夹以年份/月份/分割
            String folder = LocalDateTime.now().getYear() + "/" + LocalDateTime.now().getMonth() + "/";
            //为了不重复，时间戳作为图片名称
            String fileNameString = System.currentTimeMillis() + a;
            File savedFile = new File(path + folder, fileNameString);
            try {
                //去掉前缀
                filePath = savedFile.getPath().replaceAll("\\\\", "/").replace(fileConfig.getProfile(), "");
                log.info("保存文件--------->" + filePath);
                FileUtils.copyInputStreamToFile(file.getInputStream(), savedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new ServiceException("文件类型不对 *.jpg,*.png");
        }
        return filePath.replaceAll("\\\\", "/");
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
