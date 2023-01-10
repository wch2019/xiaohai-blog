package com.xiaohai.common.daomain;

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
    @Schema(description = "数据总数")
    private long total;
    @Schema(description = "返回列表")
    private List<T> records;

    public static <T> ReturnPageData<T> fillingData(long total, List<T> records) {
        ReturnPageData<T> returnPageData = new ReturnPageData<T>();
        returnPageData.setTotal(total);
        returnPageData.setRecords(records);
        return returnPageData;
    }
}
