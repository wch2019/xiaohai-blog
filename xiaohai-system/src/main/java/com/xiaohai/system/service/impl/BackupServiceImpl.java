package com.xiaohai.system.service.impl;

import com.xiaohai.common.daomain.Response;
import com.xiaohai.system.dao.BackupMapper;
import com.xiaohai.system.service.BackupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangchenghai
 * @date 2024/07/08 17:40:01
 */
@Service
@RequiredArgsConstructor
public class BackupServiceImpl implements BackupService {
    private static BackupMapper backupMapper;

    @Override
    public void add() {
        List<String> tableNames = backupMapper.showTables();

    }
}
