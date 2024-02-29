//package com.xiaohai.spider.chatgpt.azure;
//
//import com.azure.ai.openai.OpenAIClient;
//import com.azure.ai.openai.OpenAIClientBuilder;
//import com.azure.ai.openai.models.*;
//import com.azure.core.credential.AzureKeyCredential;
//import com.azure.core.util.HttpClientOptions;
//import com.xiaohai.spider.chatgpt.azure.config.AzureOpenAiConfig;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.azure.ai.openai.OpenAIServiceVersion.V2023_07_01_PREVIEW;
//
///**
// * @author wangchenghai
// * @date 2023/08/22 14:08:35
// */
//@Service
//public class GetChatCompletionsSample {
//    @Resource
//    private AzureOpenAiConfig azureOpenAiConfig;
//    /**
//     * Runs the sample algorithm and demonstrates how to get chat completions for the provided chat messages.
//     * Completions support a wide variety of tasks and generate text that continues from or "completes" provided
//     * prompt data.
//     *
//     * @param args Unused. Arguments to the program.
//     */
//    public static void main(String[] args) {
//        String azureOpenaiKey = "7cc66c84";
//        String endpoint = "https://openai.azure.com";
//        String deploymentOrModelId = "gpt-35-turbo-16k";
//
//        HttpClientOptions httpClientOptions=new HttpClientOptions()
//                .setConnectTimeout(Duration.ofSeconds(10))
//                .setReadTimeout(Duration.ofSeconds(30))
//                .setWriteTimeout(Duration.ofSeconds(30));
//
//        OpenAIClient client = new OpenAIClientBuilder()
//                .endpoint(endpoint)
//                .clientOptions(httpClientOptions)
//                .serviceVersion(V2023_07_01_PREVIEW)
//                .credential(new AzureKeyCredential(azureOpenaiKey))
//                .buildClient();
//
//        List<ChatMessage> chatMessages = new ArrayList<>();
//        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "你是一个乐于助人的助手.你会像海盗一样说话."));
//        chatMessages.add(new ChatMessage(ChatRole.USER, "你可以帮我吗?"));
//        chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, "当然，亲爱的!我能为你做什么?"));
//        chatMessages.add(new ChatMessage(ChatRole.USER, "训练一只鹦鹉最好的方法是什么?"));
//        chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, "嗯，听起来你想要训练一只鹦鹉啊!啊哈哈，这可正是我的长项之一，就像航海中的掌舵一样。让我来为你指引一下正确的航线吧!\n" +
//                "\n" +
//                "首先，要记住，训练鹦鹉需要耐心和恒心。鹦鹉是聪明的鸟类，但训练可能需要一些时间。你愿意与我一同探索吗?\n" +
//                "\n" +
//                "1. 建立良好的关系：与鹦鹉建立互信和强烈的联系非常重要。花时间与它相处，让它逐渐熟悉你的声音和存在。\n" +
//                "\n" +
//                "2. 基本指令训练：从基本指令开始，如“坐下”、“飞起来”等。使用手势和声音命令来帮助鹦鹉理解你的意图。\n" +
//                "\n" +
//                "3. 奖励系统：表扬和奖励是鹦鹉训练的关键。使用喜欢的零食或鹦鹉喜欢的食物来奖励它，以积极正面的方式加强它的行为。\n" +
//                "\n" +
//                "4. 重复训练：鹦鹉需要不断的重复和强化来巩固训练成果。每天进行几次短时间的训练会带来更好的效果。\n" +
//                "\n" +
//                "5. 社交训练：让鹦鹉与人和其他动物进行互动有助于增加它的社交能力和适应性。\n" +
//                "\n" +
//                "6. 避免惩罚：避免使用过度惩罚或伤害鹦鹉的方法，这对于建立良好的训练关系不利。\n" +
//                "\n" +
//                "记住，每只鹦鹉都有自己的个性和学习速度，所以要根据鹦鹉的需求和进展调整训练计划。希望这些建议能帮你顺利驾驭你的鹦鹉船，啊哈哈!"));
//        chatMessages.add(new ChatMessage(ChatRole.USER, "能不能概括一下，我觉得有点复杂"));
//        ChatCompletions chatCompletions = client.getChatCompletions(deploymentOrModelId, new ChatCompletionsOptions(chatMessages));
//
//        System.out.printf("Model ID=%s is created at %s.%n", chatCompletions.getId(), chatCompletions.getCreatedAt());
//        for (ChatChoice choice : chatCompletions.getChoices()) {
//            ChatMessage message = choice.getMessage();
//            System.out.printf("Index: %d, Chat Role: %s.%n", choice.getIndex(), message.getRole());
//            System.out.println("Message:");
//            System.out.println(message.getContent());
//        }
//
//        System.out.println();
//        CompletionsUsage usage = chatCompletions.getUsage();
//        System.out.printf("Usage: number of prompt token is %d, "
//                        + "number of completion token is %d, and number of total tokens in request and response is %d.%n",
//                usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());
//    }
//}
