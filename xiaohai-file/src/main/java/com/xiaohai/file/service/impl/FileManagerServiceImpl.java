package com.xiaohai.file.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.confing.FileConfig;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.exception.ServiceException;
import com.xiaohai.common.server.Disk;
import com.xiaohai.common.utils.FileUtils;
import com.xiaohai.common.utils.PageUtils;
import com.xiaohai.file.dao.FileManagerMapper;
import com.xiaohai.file.pojo.dto.FileManagerDto;
import com.xiaohai.file.pojo.entity.FileManager;
import com.xiaohai.file.pojo.query.FileManagerQuery;
import com.xiaohai.file.pojo.vo.FileManagerNameVo;
import com.xiaohai.file.pojo.vo.FileManagerVo;
import com.xiaohai.file.service.FileManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件管理 服务实现类
 *
 * @author xiaohai
 * @since 2023-12-09
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FileManagerServiceImpl extends ServiceImpl<FileManagerMapper, FileManager> implements FileManagerService {

    private final FileConfig fileConfig;

    @Override
    public Integer add(FileManagerVo vo) {
        FileManager fileManager = new FileManager();
        BeanUtils.copyProperties(vo, fileManager);
        return baseMapper.insert(fileManager);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteFile(Long[] ids) {
        for (Long id : ids) {
            FileManager fileManager = baseMapper.selectById(id);
            Integer userId = Integer.valueOf((String) StpUtil.getLoginId());
            if (!fileManager.getCreatedBy().equals(userId)) {
                throw new ServiceException("非当前用户数据无法删除");
            }
            List<FileManager> list = baseMapper.selectChildHierarchy(fileManager.getId());
            for (FileManager file : list) {
                if (!file.getCreatedBy().equals(userId)) {
                    throw new ServiceException("非当前用户数据无法删除");
                }
                String pathFile = FileUtils.systemFilePath(fileConfig.getProfile() + file.getFilePath());
                boolean isTrue = FileUtils.deleteFile(pathFile);
                Assert.isTrue(isTrue, "当前路径:" + file.getFilePath() + ",删除失败");
                baseMapper.deleteById(file.getId());
            }
        }
        return ids.length;
    }


    @Override
    public Integer updateData(FileManagerVo vo) {
        FileManager fileManager = new FileManager();
        BeanUtils.copyProperties(vo, fileManager);
        return baseMapper.updateById(fileManager);
    }

    @Override
    public String renameFile(FileManagerNameVo vo) {
        FileManager fileManager = baseMapper.selectById(vo.getId());
        if (fileManager.getFileName().equals(vo.getFileName())) {
            return fileManager.getFileName();
        }
        Integer userId = Integer.valueOf((String) StpUtil.getLoginId());
        if (!fileManager.getCreatedBy().equals(userId)) {
            throw new ServiceException("非当前用户数据无法重命名");
        }
        List<FileManager> list = baseMapper.selectChildHierarchy(fileManager.getId());
        var newPath = FileUtils.getLastSegmentStart(fileManager.getFilePath()) + vo.getFileName();
        //获取唯一名称
        var targetPath = FileUtils.renameFile(fileConfig.getProfile() + newPath);
        newPath = FileUtils.normalizeFilePath(targetPath.replace(fileConfig.getProfile(), ""));

        FileUtils.renamePath(fileConfig.getProfile() + fileManager.getFilePath(), fileConfig.getProfile() + newPath);
        var path = fileManager.getFilePath();
        for (FileManager file : list) {
            file.setFilePath(file.getFilePath().replace(path, newPath));
            baseMapper.updateById(file);
        }
        fileManager.setFilePath(newPath);
        fileManager.setFileName(FileUtils.getLastSegmentEnd(newPath));
        fileManager.setId(vo.getId());
        baseMapper.updateById(fileManager);
        return fileManager.getFileName();
    }

    @Override
    public FileManager findByHash(Integer parentId, String hash) {
        return baseMapper.selectOne(new LambdaQueryWrapper<FileManager>().eq(FileManager::getParentId, parentId).eq(FileManager::getFileHash, hash));
    }

    @Override
    public ReturnPageData<FileManagerDto> findListByPage(FileManagerQuery query) {
        FileManager fileManager = new FileManager();
        BeanUtils.copyProperties(query, fileManager);
        IPage<FileManager> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<FileManager> iPage = baseMapper.selectPage(wherePage, Wrappers.query(fileManager));
        List<FileManagerDto> list = new ArrayList<>();
        for (FileManager fileManagers : iPage.getRecords()) {
            FileManagerDto fileManagerDto = new FileManagerDto();
            BeanUtils.copyProperties(fileManagers, fileManagerDto);
            list.add(fileManagerDto);
        }
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        return ReturnPageData.fillingData(pageData, list);
    }

    @Override
    public FileManager findByPath(String path) {
        return baseMapper.selectOne(new LambdaQueryWrapper<FileManager>().eq(FileManager::getFilePath, path));
    }

    @Override
    public Integer deletePath(String path) {
        return baseMapper.delete(new LambdaQueryWrapper<FileManager>().eq(FileManager::getFilePath, path));
    }

    @Override
    public ReturnPageData<FileManagerDto> getParentIdPath(Integer parentId) {
        IPage<FileManager> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<FileManager> iPage = baseMapper.selectPage(wherePage, new LambdaQueryWrapper<FileManager>().eq(FileManager::getParentId, parentId));
        List<FileManagerDto> list = new ArrayList<>();
        for (FileManager fileManagers : iPage.getRecords()) {
            FileManagerDto fileManagerDto = new FileManagerDto();
            BeanUtils.copyProperties(fileManagers, fileManagerDto);
            fileManagerDto.setFileSize(fileManagers.getFileSize() == 0 ? "" : FileUtils.formatFileSize(fileManagers.getFileSize()));
            list.add(fileManagerDto);
        }
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        return ReturnPageData.fillingData(pageData, list);
    }

    @Override
    public Disk getHardDiskSize() {
        return FileUtils.getSystemDiskSize(fileConfig.getProfile());
    }

    @Override
    public Disk getUserHardDiskSize() {
        Integer userId = Integer.valueOf((String) StpUtil.getLoginId());
        Long total = baseMapper.getTotalDiskSizeByUserId(userId);
        Long used = baseMapper.getUsedDiskSizeByUserId(userId);
        Long markUsed= baseMapper.getUsedMarkdownSizeByUserId(userId);
        if (markUsed == null) {
            markUsed = 0L;
        }
        if (used == null) {
            used = 0L;
        }
        Disk disk = FileUtils.getUserDiskSize(total, used);
        Long other=used-markUsed;
        disk.setOtherUsed(FileUtils.formatFileSize(other));
        disk.setOtherUsage(NumberUtil.mul(NumberUtil.div(other, total, 4), 100).doubleValue());
        disk.setMarkUsed(FileUtils.formatFileSize(markUsed));
        disk.setMarkUsage(NumberUtil.mul(NumberUtil.div(markUsed, total, 4), 100).doubleValue());
        return disk;
    }

    @Override
    public Disk getUserHardDiskSize(Integer userId) {
        Long total = baseMapper.getTotalDiskSizeByUserId(userId);
        Long used = baseMapper.getUsedDiskSizeByUserId(userId);
        if (used == null) {
            used = 0L;
        }
        return FileUtils.getUserDiskSize(total, used);
    }

    @Override
    public Boolean getUserFileCount(Long userId) {
        Long fileCount = baseMapper.selectCount(new LambdaQueryWrapper<FileManager>().eq(FileManager::getCreatedBy, userId));
        if (fileCount != 0) {
            return true;
        }
        return false;
    }
}
