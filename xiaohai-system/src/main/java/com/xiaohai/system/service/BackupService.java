package com.xiaohai.system.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author wangchenghai
 * @date 2024/07/08 17:39:46
 */
public interface BackupService {
    /**
     *数据备份
     */
    void backupDatabase();

    /**
     * 还原备份
     */
    void restoreFileName(String fileName);

    /**
     * 备份文件还原
     * @param file
     */
    void restoreBackupFile(MultipartFile file);
}
