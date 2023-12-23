package com.xiaohai.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaohai.common.confing.FileConfig;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.utils.FileUtils;
import com.xiaohai.file.pojo.entity.FileManager;
import com.xiaohai.file.dao.FileManagerMapper;
import com.xiaohai.file.service.FileManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.ReturnPageData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.xiaohai.file.pojo.query.FileManagerQuery;
import com.xiaohai.file.pojo.vo.FileManagerVo;
import com.xiaohai.file.pojo.dto.FileManagerDto;
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
            String pathFile = FileUtils.systemFilePath(fileConfig.getProfile() + fileManager.getFilePath());
            boolean isTrue = FileUtils.deleteFile(pathFile);
            Assert.isTrue(isTrue, "当前路径:" + fileManager.getFilePath() + ",删除失败");
            baseMapper.deleteById(id);
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
    public FileManager findByHash(String hash) {
        return baseMapper.selectOne(new LambdaQueryWrapper<FileManager>().eq(FileManager::getFileHash, hash));
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
        IPage<FileManager> iPage = baseMapper.selectPage(wherePage, new LambdaQueryWrapper<FileManager>().like(FileManager::getParentId, parentId));
        List<FileManagerDto> list = new ArrayList<>();
        for (FileManager fileManagers : iPage.getRecords()) {
            FileManagerDto fileManagerDto = new FileManagerDto();
            BeanUtils.copyProperties(fileManagers, fileManagerDto);
            fileManagerDto.setFileSize(FileUtils.formatFileSize(fileManagers.getFileSize()));
            list.add(fileManagerDto);
        }
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        return ReturnPageData.fillingData(pageData, list);
    }
}
