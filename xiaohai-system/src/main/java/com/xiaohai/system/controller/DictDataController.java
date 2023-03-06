package com.xiaohai.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.DictDataEntity;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.system.pojo.entity.DictData;
import com.xiaohai.system.pojo.query.DictDataQuery;
import com.xiaohai.system.pojo.vo.DictDataVo;
import com.xiaohai.system.service.DictDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 字典数据表Controller
 *
 * @author xiaohai
 * @since 2023-02-03
 */
@Tag(name = "字典数据表", description = "字典数据表")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/dict-data")
public class DictDataController {

    private final DictDataService dictDataService;


    @Operation(summary = "新增字典数据表", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("dict:data:add")
    @PostMapping()
    public Response<Integer> add(@RequestBody DictDataVo vo) {
        return Response.success("新增字典数据表成功！", dictDataService.add(vo));
    }

    @Operation(summary = "删除字典数据表", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("dict:data:delete")
    @DeleteMapping("{ids}")
    public Response<Integer> delete(@PathVariable Long[] ids) {
        return Response.success("删除字典数据表成功！", dictDataService.delete(ids));
    }

    @Operation(summary = "更新字典数据表", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("dict:data:update")
    @PutMapping()
    public Response<Integer> update(@RequestBody DictDataVo vo) {
        return Response.success("更新字典数据表成功！", dictDataService.updateData(vo));
    }


    @Operation(summary = "id查询字典数据表", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("{id}")
    public Response<DictData> findById(@PathVariable Long id) {
        return Response.success("id查询字典数据表成功！", dictDataService.findById(id));
    }

    @Operation(summary = "查询字典数据表列表数据", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @SaCheckPermission("dict:data:list")
    @GetMapping()
    public Response<ReturnPageData<DictData>> findListByPage(DictDataQuery query) {
        return Response.success("查询字典数据表列表成功！", dictDataService.findListByPage(query));
    }

    @Operation(summary = " 根据字典类型查询字典数据信息", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping(value = "/type/{dictType}")
    public Response<List<DictDataEntity>> dictType(@PathVariable String dictType) {
        return Response.success("根据字典类型查询字典数据信息成功！", dictDataService.dictType(dictType));
    }
    @Operation(summary = "获取所有字典数据信息", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping(value = "/data/dictAll")
    public Response<Map<String, List<DictDataEntity>>> dictAll() {
        return Response.success("获取字典数据信息成功！", dictDataService.dictAll());
    }

}
