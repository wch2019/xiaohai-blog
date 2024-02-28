package com.xiaohai.spider.iflytek.dto;

import lombok.*;

/**
 * 消息对象
 * 参数含义查看 <a href="https://www.xfyun.cn/doc/spark/Web.html#_1-%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E">...</a>
 * @author wangchenghai
 * @date 2023/08/21 16:48:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MsgDTO {
    /**
     * 角色 取值为[user,assistant]	user表示是用户的问题，assistant表示AI的回复
     */
    private String role;
    /**
     * 消息内容 所有content的累计tokens需控制8192以内 用户和AI的对话内容
     */
    private String content;
    /**
     * 结果序号，取值为[0,10]; 当前为保留字段，开发者可忽略
     */
    private Integer index;

    @Getter
    public enum Role {
        /**
         *
         */
        SYSTEM("system"),
        /**
         * user表示是用户的问题
         */
        USER("user"),
        /**
         * assistant表示AI的回复
         */
        ASSISTANT("assistant");

        private String name;

        private Role(String name) {
            this.name = name;
        }
    }
}
