package com.xiaohai.system.service;

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
    void restore();
}
