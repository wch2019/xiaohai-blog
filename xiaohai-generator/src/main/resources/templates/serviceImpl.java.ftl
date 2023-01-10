package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import com.example.shiro.common.daomain.PageEntity;
import com.example.shiro.common.daomain.Response;
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
    public Response findListByPage(PageEntity page, ${entity} ${entity?uncap_first}){
        QueryWrapper<${entity}> wrapper = new QueryWrapper<>();
        if (page.isPaginated()) {
            IPage<${entity}> wherePage = new Page<>(page.getPageNum(),page.getPageSize());
            return new Response().success("查询角色权限分页数据成功！", baseMapper.selectPage(wherePage, wrapper));
        }else{
            return new Response().success("查询角色权限数据列表成功！", baseMapper.selectList(wrapper));
        }
    }
}
</#if>
