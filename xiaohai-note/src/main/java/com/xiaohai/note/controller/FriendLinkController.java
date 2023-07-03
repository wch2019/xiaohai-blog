package com.xiaohai.note.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.xiaohai.common.annotation.Log;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import com.xiaohai.note.service.FriendLinkService;
import com.xiaohai.note.pojo.entity.FriendLink;
import com.xiaohai.note.pojo.query.FriendLinkQuery;
import com.xiaohai.note.pojo.vo.FriendLinkVo;
import com.xiaohai.note.pojo.dto.FriendLinkDto;

import org.springframework.web.bind.annotation.RestController;

/**
 * 友情链接Controller
 *
 * @author xiaohai
 * @since 2023-07-01
 */
@Tag(name = "友情链接", description = "友情链接")
@RestController
@RequiredArgsConstructor
@RequestMapping("/note/friend-link")
public class FriendLinkController {

    private final FriendLinkService friendLinkService;


    @Operation(summary = "新增友情链接", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("note:link:add")
    @Log(title = "新增友情链接")
    @PostMapping()
    public Response<Integer> add(@RequestBody FriendLinkVo vo) {
        return Response.success("新增友情链接成功！", friendLinkService.add(vo));
    }

    @Operation(summary = "删除友情链接", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("note:link:delete")
    @Log(title = "删除友情链接")
    @DeleteMapping("{ids}")
    public Response<Integer> delete(@PathVariable Long[] ids) {
        return Response.success("删除友情链接成功！", friendLinkService.delete(ids));
    }

    @Operation(summary = "更新友情链接", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("note:link:update")
    @Log(title = "更新友情链接")
    @PutMapping()
    public Response<Integer> update(@RequestBody FriendLinkVo vo) {
        return Response.success("更新友情链接成功！", friendLinkService.updateData(vo));
    }


    @Operation(summary = "id查询友情链接", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("{id}")
    public Response<FriendLink> findById(@PathVariable Long id) {
        return Response.success("id查询友情链接成功！", friendLinkService.findById(id));
    }

    @Operation(summary = "查询友情链接列表数据", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @SaCheckPermission("note:link:list")
    @GetMapping()
    public Response<ReturnPageData<FriendLinkDto>> findListByPage(@ParameterObject FriendLinkQuery query) {
        return Response.success("查询友情链接列表成功！", friendLinkService.findListByPage(query));
    }

}
