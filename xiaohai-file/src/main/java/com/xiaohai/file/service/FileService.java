package com.xiaohai.file.service;

import com.xiaohai.file.pojo.dto.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author wangchenghai
 * @date 2023/03/16 15:00:30
 */
public interface FileService {
    /**
     * 上传文件
     * @param file
     * @param type
     * @return
     */
    String upload(MultipartFile file, Integer type);

    /**
     * 文件列表
     * @param path
     * @return
     */
    List<FileDto> getPathList(String path);
}
