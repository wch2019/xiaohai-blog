package com.xiaohai.file.service;

import org.springframework.web.multipart.MultipartFile;

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
}
