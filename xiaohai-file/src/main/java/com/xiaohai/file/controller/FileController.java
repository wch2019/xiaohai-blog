package com.xiaohai.file.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangchenghai
 * @date 2023/03/16 14:58:46
 */
@Tag(name = "文件操作", description = "文件操作")
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

}
