package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.utils.MapUtils;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

/**
 *
 * ${table.comment!} 服务实现类
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Override
    public Integer add(${entity} ${entity?uncap_first}){
        return baseMapper.insert(${entity?uncap_first});
    }

    @Override
    public Integer delete(Long id){
        return  baseMapper.deleteById(id);
    }

    @Override
    public Integer updateData(${entity} ${entity?uncap_first}){
        return  baseMapper.updateById(${entity?uncap_first});
    }

    @Override
    public  ${entity} findById(Long id){
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<${entity}> findListByPage(${entity} ${entity?uncap_first}){
        IPage<${entity}> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<${entity}> iPage = baseMapper.selectPage(wherePage,Wrappers.query(${entity?uncap_first}));
        return ReturnPageData.fillingData(iPage);
    }
}
</#if>
