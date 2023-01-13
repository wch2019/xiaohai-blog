package com.xiaohai.common.daomain;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


/**
 * 分页返回数据公共类
 *
 * @author wangchenghai
 * @date  2023/01/10 14:24:48
 * @param
 */

@Data
public class ReturnPageData<T> {
    @Schema(description = "查询列表总记录数")
    private long total;
    @Schema(description = "每页显示条数，默认 10")
    private long size;
    @Schema(description = "当前页")
    private long current;
    @Schema(description = "查询数据列表")
    private List<T> records;

    public static <T> ReturnPageData<T> fillingData(IPage<T> iPage) {
        ReturnPageData<T> returnPageData = new ReturnPageData<T>();
        returnPageData.setTotal(iPage.getTotal());
        returnPageData.setSize(iPage.getSize());
        returnPageData.setCurrent(iPage.getCurrent());
        returnPageData.setRecords(iPage.getRecords());
        return returnPageData;
    }
}
