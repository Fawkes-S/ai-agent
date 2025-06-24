package com.sjc.aiagent.demo.invoke;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.ai.chat.messages.AssistantMessage;

/**
 * Spring Ai 框架调用AI 大模型
 */
@Component
public class SpringAiInvoke implements CommandLineRunner {

    @Resource
    private ChatModel dashscopeChatModel;  // 灵积的chatModel

    @Override
    public void run(String... args) throws Exception {

        AssistantMessage assistantMessage = dashscopeChatModel.call(new Prompt("今天星期几"))
                .getResult()
                .getOutput();
        System.out.println("========================");
        System.out.println(assistantMessage.getText());
    }
}
