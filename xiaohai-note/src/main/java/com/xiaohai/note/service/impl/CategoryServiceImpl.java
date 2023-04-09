package com.xiaohai.note.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.utils.PageUtils;
import com.xiaohai.note.dao.ArticleMapper;
import com.xiaohai.note.dao.CategoryMapper;
import com.xiaohai.note.pojo.dto.CategoryDto;
import com.xiaohai.note.pojo.entity.Article;
import com.xiaohai.note.pojo.entity.Category;
import com.xiaohai.note.pojo.query.CategoryQuery;
import com.xiaohai.note.pojo.vo.CategoryVo;
import com.xiaohai.note.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 分类表 服务实现类
 *
 * @author xiaohai
 * @since 2023-04-03
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    private final ArticleMapper articleMapper;
    @Override
    public Integer add(CategoryVo vo){
        Category category=new Category();
        BeanUtils.copyProperties(vo,category);
        return baseMapper.insert(category);
    }

    @Override
    public Integer delete(Long[] ids){
        for (Long id : ids) {
            Long codeCount = articleMapper.selectCount(new QueryWrapper<Article>().eq("category_id", id));
            Assert.isTrue(codeCount == 0, "当前分类已引用，无法删除");
            baseMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    public Integer updateData(CategoryVo vo){
        Category category=new Category();
        BeanUtils.copyProperties(vo,category);
        return baseMapper.updateById(category);
    }

    @Override
    public Category findById(Long id){
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<CategoryDto> findListByPage(CategoryQuery query){
        Category category=new Category();
        BeanUtils.copyProperties(query,category);
        IPage<Category> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<Category> iPage = baseMapper.selectPage(wherePage,Wrappers.query(category).orderByAsc("sort"));
        List<CategoryDto> list=new ArrayList<>();
        for(Category categorys:iPage.getRecords()){
            CategoryDto categoryDto=new CategoryDto();
            BeanUtils.copyProperties(categorys,categoryDto);
            list.add(categoryDto);
        }
        PageData pageData=new PageData();
        BeanUtils.copyProperties(iPage,pageData);
        return ReturnPageData.fillingData(pageData,list);
    }

    @Override
    public List<CategoryDto> optionSelect() {
        List<Category> categories=baseMapper.selectList(new QueryWrapper<Category>().orderByAsc("sort"));
        List<CategoryDto> list=new ArrayList<>();
        for(Category category:categories){
            CategoryDto categoryDto=new CategoryDto();
            BeanUtils.copyProperties(category,categoryDto);
            list.add(categoryDto);
        }
        return list;
    }
}
