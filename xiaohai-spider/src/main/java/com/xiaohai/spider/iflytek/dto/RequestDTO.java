package com.xiaohai.spider.iflytek.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 请求参数
 * 参数含义查看 <a href="https://www.xfyun.cn/doc/spark/Web.html#_1-%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E">...</a>
 * @author wangchenghai
 * @date 2023/08/21 16:59:57
 */
@NoArgsConstructor
@Data
public class RequestDTO {
    /**
     * header部分
     */
    @JsonProperty("header")
    private HeaderDTO header;
    /**
     * parameter.chat部分
     */
    @JsonProperty("parameter")
    private ParameterDTO parameter;
    /**
     * payload.message.text部分
     */
    @JsonProperty("payload")
    private PayloadDTO payload;

    @NoArgsConstructor
    @Data
    @AllArgsConstructor
    public static class HeaderDTO {
        /**
         * 应用appid，从开放平台控制台创建的应用中获取
         */
        @JSONField(name = "app_id")
        private String appId;
        /**
         * 最大长度32	每个用户的id，用于区分不同用户
         */
        @JSONField(name = "uid")
        private String uid;
    }

    @NoArgsConstructor
    @Data
    @AllArgsConstructor
    public static class ParameterDTO {
        private ChatDTO chat;

        @NoArgsConstructor
        @Data
        public static class ChatDTO {
            /**
             * 指定访问的领域,general指向V1.5版本 generalv2指向V2版本。注意：不同的取值对应的url也不一样！
             */
            @JsonProperty("domain")
            private String domain = "general";
            /**
             * 核采样阈值。用于决定结果随机性，取值越高随机性越强即相同的问题得到的不同答案的可能性越高
             */
            @JsonProperty("temperature")
            private Double temperature = 0.5;
            /**
             * 模型回答的tokens的最大长度
             */
            @JSONField(name = "max_tokens")
            private Integer maxTokens = 2048;
        }
    }

    @NoArgsConstructor
    @Data
    @AllArgsConstructor
    public static class PayloadDTO {
        @JsonProperty("message")
        private MessageDTO message;

        @NoArgsConstructor
        @Data
        @AllArgsConstructor
        public static class MessageDTO {
            @JsonProperty("text")
            private List<MsgDTO> text;


        }
    }
}
