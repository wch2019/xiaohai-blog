package ${package.Controller};

import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import ${other.query}.${entity}Query;
import ${other.vo}.${entity}Vo;
import ${other.dto}.${entity}Dto;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
    import ${superControllerClassPackage};
</#if>

/**
*
* ${table.comment!}Controller
*
* @author ${author}
* @since ${date}
*/
<#if restControllerStyle>
@Tag(name = "${table.comment!}",description = "${table.comment!}")
@RestController
@RequiredArgsConstructor
<#else>
    @Controller
</#if>@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??>:${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
    public class ${table.controllerName} extends ${superControllerClass}{
<#else>public class ${table.controllerName} {
</#if>

    private final ${table.serviceName} ${(table.serviceName?substring(1))?uncap_first};


    @Operation(summary = "新增${table.comment!}",security = {@SecurityRequirement(name = "authentication")})
    @PostMapping()
    public Response<Integer> add(@RequestBody ${entity}Vo vo){
        return  Response.success("新增${table.comment!}成功！", ${(table.serviceName?substring(1))?uncap_first}.add(vo));
    }

    @Operation(summary = "删除${table.comment!}",security = {@SecurityRequirement(name = "authentication")})
    @DeleteMapping("{id}")
    public Response<Integer> delete(@PathVariable("id") Long id){
        return  Response.success("删除${table.comment!}成功！",${(table.serviceName?substring(1))?uncap_first}.delete(id));
    }

    @Operation(summary = "更新${table.comment!}",security = {@SecurityRequirement(name = "authentication")})
    @PutMapping()
    public Response<Integer> update(@RequestBody ${entity}Vo vo){
        return  Response.success("更新${table.comment!}成功！",${(table.serviceName?substring(1))?uncap_first}.updateData(vo));
    }


    @Operation(summary = "id查询${table.comment!}",security = {@SecurityRequirement(name = "authentication")})
    @GetMapping("{id}")
    public Response<${entity}> findById(@PathVariable Long id){
        return  Response.success("id查询${table.comment!}成功！",${(table.serviceName?substring(1))?uncap_first}.findById(id));
    }

    @Operation(summary = "查询${table.comment!}列表数据",security = {@SecurityRequirement(name = "authentication")})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @GetMapping()
    public Response<ReturnPageData<${entity}Dto>> findListByPage(${entity}Query query){
        return Response.success("查询${table.comment!}列表成功！",${(table.serviceName?substring(1))?uncap_first}.findListByPage(query));
    }

    }
</#if>