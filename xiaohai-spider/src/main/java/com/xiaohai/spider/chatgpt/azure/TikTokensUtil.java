package com.xiaohai.spider.chatgpt.azure;

import cn.hutool.core.text.CharSequenceUtil;
import com.azure.ai.openai.models.ChatMessage;
import com.azure.ai.openai.models.ChatRole;
import com.azure.ai.openai.models.FunctionCall;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.ModelType;
import com.xiaohai.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * token计算工具类
 *
 * @author wangchenghai
 * @date 2023/08/22 16:00:18
 */
@Slf4j
public class TikTokensUtil {
    public static final String NOT_MODEL="当前模型不存在,无法计算tokens";

    /**
     * registry实例
     */
    private static final EncodingRegistry REGISTRY = Encodings.newDefaultEncodingRegistry();

    /**
     * 通过模型名称计算messages获取编码数组
     * 参考官方的处理逻辑：
     * <a href=https://github.com/openai/openai-cookbook/blob/main/examples/How_to_count_tokens_with_tiktoken.ipynb>https://github.com/openai/openai-cookbook/blob/main/examples/How_to_count_tokens_with_tiktoken.ipynb</a>
     *
     * @param modelName 模型名称
     * @param messages  消息体
     * @return tokens数量
     */
    public static int tokens(@NotNull String modelName, @NotNull List<ChatMessage> messages) throws ServiceException {
        Encoding encoding = REGISTRY.getEncodingForModel(modelName).orElseThrow(() -> new ServiceException(NOT_MODEL));
        int tokensPerMessage = 0;
        int tokensPerName = 0;
        if (modelName.equals("gpt-3.5-turbo-0613") ||
                modelName.equals("gpt-3.5-turbo-16k-0613") ||
                modelName.equals("gpt-4-0314") ||
                modelName.equals("gpt-4-32k-0314") ||
                modelName.equals("gpt-4-0613") ||
                modelName.equals("gpt-4-32k-0613")
        ) {
            tokensPerMessage = 3;
            tokensPerName = 1;
        } else if (modelName.equals("gpt-3.5-turbo-0301")) {
            tokensPerMessage = 4;
            tokensPerName = -1;
        } else if (modelName.contains("gpt-3.5-turbo")) {
            //"gpt-3.5-turbo" in model:
            log.warn("Warning: gpt-3.5-turbo may update over time. Returning num tokens assuming gpt-3.5-turbo-0613.");
            tokensPerMessage = 3;
            tokensPerName = 1;
        } else if (modelName.contains("gpt-4")) {
            log.warn("Warning: gpt-4 may update over time. Returning num tokens assuming gpt-4-0613.");
            tokensPerMessage = 3;
            tokensPerName = 1;
        } else {
            log.warn("不支持的model {}. See https://github.com/openai/openai-python/blob/main/chatml.md 更多信息.", modelName);
        }
        int sum = 0;
        for (ChatMessage msg : messages) {
            sum += tokensPerMessage;
            sum += encoding.countTokens(msg.getContent());
            sum += encoding.countTokens(msg.getRole().toString());
            sum += encoding.countTokens(msg.getName());
            FunctionCall functionCall = msg.getFunctionCall();
            sum += Objects.isNull(functionCall) ? 0 : encoding.countTokens(functionCall.toString());
            if (CharSequenceUtil.isNotBlank(msg.getName())) {
                sum += tokensPerName;
            }
        }
        //every reply is primed with <|start|>assistant<|message|>
        sum += 3;
        return sum;
    }

    public static int tokenCount(@NotNull String modelName, @NotNull String text) throws ServiceException {
        Encoding encoding = REGISTRY.getEncodingForModel(modelName).orElseThrow(() -> new ServiceException(NOT_MODEL));
        return encoding.countTokens(text);
    }


    public static void main(String[] args) throws ServiceException {
        List<ChatMessage> chatMessage = new ArrayList<>();
        chatMessage.add(new ChatMessage(ChatRole.USER, "请介绍一下你自己"));
        System.out.println(tokens(ModelType.GPT_3_5_TURBO_16K.getName(), chatMessage));
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "你是一个乐于助人的助手.你会像海盗一样说话."));
        chatMessages.add(new ChatMessage(ChatRole.USER, "你可以帮我吗?"));
        chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, "当然，亲爱的!我能为你做什么?"));
        chatMessages.add(new ChatMessage(ChatRole.USER, "训练一只鹦鹉最好的方法是什么?"));
        System.out.println(tokens(ModelType.GPT_3_5_TURBO_16K.getName(), chatMessages));
        String text="嗯，听起来你想要训练一只鹦鹉啊!啊哈哈，这可正是我的长项之一，就像航海中的掌舵一样。让我来为你指引一下正确的航线吧!\n" +
                "\n" +
                "首先，要记住，训练鹦鹉需要耐心和恒心。鹦鹉是聪明的鸟类，但训练可能需要一些时间。你愿意与我一同探索吗?\n" +
                "\n" +
                "1. 建立良好的关系：与鹦鹉建立互信和强烈的联系非常重要。花时间与它相处，让它逐渐熟悉你的声音和存在。\n" +
                "\n" +
                "2. 基本指令训练：从基本指令开始，如“坐下”、“飞起来”等。使用手势和声音命令来帮助鹦鹉理解你的意图。\n" +
                "\n" +
                "3. 奖励系统：表扬和奖励是鹦鹉训练的关键。使用喜欢的零食或鹦鹉喜欢的食物来奖励它，以积极正面的方式加强它的行为。\n" +
                "\n" +
                "4. 重复训练：鹦鹉需要不断的重复和强化来巩固训练成果。每天进行几次短时间的训练会带来更好的效果。\n" +
                "\n" +
                "5. 社交训练：让鹦鹉与人和其他动物进行互动有助于增加它的社交能力和适应性。\n" +
                "\n" +
                "6. 避免惩罚：避免使用过度惩罚或伤害鹦鹉的方法，这对于建立良好的训练关系不利。\n" +
                "\n" +
                "记住，每只鹦鹉都有自己的个性和学习速度，所以要根据鹦鹉的需求和进展调整训练计划。希望这些建议能帮你顺利驾驭你的鹦鹉船，啊哈哈!";
        int tokenCount = tokenCount(ModelType.GPT_3_5_TURBO_16K.getName(), text);
        System.out.println(tokenCount);

    }
}
