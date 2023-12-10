package com.xiaohai.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.file.pojo.entity.FileManager;
import com.xiaohai.file.dao.FileManagerMapper;
import com.xiaohai.file.service.FileManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.ReturnPageData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.xiaohai.file.pojo.query.FileManagerQuery;
import com.xiaohai.file.pojo.vo.FileManagerVo;
import com.xiaohai.file.pojo.dto.FileManagerDto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 文件管理 服务实现类
 *
 * @author xiaohai
 * @since 2023-12-09
 */
@Service
public class FileManagerServiceImpl extends ServiceImpl<FileManagerMapper, FileManager> implements FileManagerService {

    @Override
    public Integer add(FileManagerVo vo){
        FileManager fileManager=new FileManager();
        BeanUtils.copyProperties(vo,fileManager);
        return baseMapper.insert(fileManager);
    }

    @Override
    public Integer delete(Long[] ids){
        for (Long id : ids) {
            baseMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    public Integer updateData(FileManagerVo vo){
        FileManager fileManager=new FileManager();
        BeanUtils.copyProperties(vo,fileManager);
        return baseMapper.updateById(fileManager);
    }

    @Override
    public FileManager findByHash(String hash){
        return baseMapper.selectOne(new LambdaQueryWrapper<FileManager>().eq(FileManager::getFileHash,hash));
    }

    @Override
    public ReturnPageData<FileManagerDto> findListByPage(FileManagerQuery query){
        FileManager fileManager=new FileManager();
        BeanUtils.copyProperties(query,fileManager);
        IPage<FileManager> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<FileManager> iPage = baseMapper.selectPage(wherePage,Wrappers.query(fileManager));
        List<FileManagerDto> list=new ArrayList<>();
        for(FileManager fileManagers:iPage.getRecords()){
            FileManagerDto fileManagerDto=new FileManagerDto();
            BeanUtils.copyProperties(fileManagers,fileManagerDto);
            list.add(fileManagerDto);
        }
        PageData pageData=new PageData();
        BeanUtils.copyProperties(iPage,pageData);
        return ReturnPageData.fillingData(pageData,list);
    }

    @Override
    public FileManager findByPath(String path) {
        return baseMapper.selectOne(new LambdaQueryWrapper<FileManager>().eq(FileManager::getFilePath,path));
    }
}
