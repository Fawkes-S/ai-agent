package com.sjc.aiagent.demo.invoke;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;

public class langChainAiInvoke {

    public static void main(String[] args) {
        ChatLanguageModel chatLanguageModel = QwenChatModel.builder()
                .apiKey(TestApiKey.API_KEY)
                .modelName("qwen-max")
                .build();
        String amswer = chatLanguageModel.chat("中国2025年6月22日是星期几");
        System.out.println(amswer);
    }
}
