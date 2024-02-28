package com.xiaohai.spider.chatgpt.azure.config;

import lombok.Data;

/**
 * @author wangchenghai
 * @date 2023/08/23 16:55:09
 */
@Data
public class AzureOpenAiProperties {

    private String azureOpenaiKey;

    private String endpoint;

    private  String deploymentOrModelName;
}
