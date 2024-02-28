package com.xiaohai.spider.chatgpt.azure.config;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.HttpClientOptions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

import static com.azure.ai.openai.OpenAIServiceVersion.V2023_07_01_PREVIEW;

/**
 * @author wangchenghai
 * @date 2023/08/23 15:11:08
 */
@Configuration
public class AzureOpenAiConfig {
    @Bean
    @ConfigurationProperties(prefix = "azure.openai")
    public AzureOpenAiProperties azureOpenAiProperties() {
        return new AzureOpenAiProperties();
    }
    @Bean
    public OpenAIClient openAiClient() {
        AzureOpenAiProperties openAiProperties= azureOpenAiProperties();
        HttpClientOptions httpClientOptions=new HttpClientOptions()
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(30))
                .setWriteTimeout(Duration.ofSeconds(30));

       return new OpenAIClientBuilder()
                .endpoint(openAiProperties.getEndpoint())
                .clientOptions(httpClientOptions)
                .serviceVersion(V2023_07_01_PREVIEW)
                .credential(new AzureKeyCredential(openAiProperties.getAzureOpenaiKey()))
                .buildClient();
    }
}
