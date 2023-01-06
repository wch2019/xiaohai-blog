package ${package.Controller};

import com.example.shiro.common.daomain.PageEntity;
import com.example.shiro.common.daomain.Response;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.*;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
<#if restControllerStyle>
    import org.springframework.web.bind.annotation.RestController;
<#else>
    import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
    import ${superControllerClassPackage};
</#if>

/**
* <p>
    * ${table.comment!} 前端控制器
    * </p>
*
* @author ${author}
* @since ${date}
*/
<#if restControllerStyle>
    @Api(tags = {"${table.comment!}"})
    @RestController
<#else>
    @Controller
</#if>@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>class ${table.controllerName}<#if superControllerClass??>:${superControllerClass}()</#if><#else><#if superControllerClass??>public class ${table.controllerName} extends ${superControllerClass}{<#else>public class ${table.controllerName} {</#if>

    private final Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private Response response;

    @Resource
    private ${table.serviceName} ${(table.serviceName?substring(1))?uncap_first};


    @ApiOperation(value = "新增${table.comment!}")
    @PostMapping()
    public Response add(@RequestBody ${entity} ${entity?uncap_first}){
        return  response.success("新增${table.comment!}成功！", ${(table.serviceName?substring(1))?uncap_first}.add(${entity?uncap_first}));
    }

    @ApiOperation(value = "删除${table.comment!}")
    @DeleteMapping("{id}")
    public Response delete(@PathVariable("id") Long id){
        return  response.success("删除${table.comment!}成功！",${(table.serviceName?substring(1))?uncap_first}.delete(id));
    }

    @ApiOperation(value = "更新${table.comment!}")
    @PutMapping()
    public Response update(@RequestBody ${entity} ${entity?uncap_first}){
        return  response.success("更新${table.comment!}成功！",${(table.serviceName?substring(1))?uncap_first}.updateData(${entity?uncap_first}));
    }


    @ApiOperation(value = "id查询${table.comment!}")
    @GetMapping("{id}")
    public Response findById(@PathVariable Long id){
        return  response.success("id查询${table.comment!}成功！",${(table.serviceName?substring(1))?uncap_first}.findById(id));
    }

    @ApiOperation(value = "查询${table.comment!}列表/分页数据")
    @GetMapping()
    public Response findListByPage(PageEntity page,${entity} ${entity?uncap_first}){
        return ${(table.serviceName?substring(1))?uncap_first}.findListByPage(page,${entity?uncap_first});
    }

    }
</#if>