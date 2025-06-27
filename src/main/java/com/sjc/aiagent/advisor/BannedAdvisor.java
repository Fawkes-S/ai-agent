package com.sjc.aiagent.advisor;

import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.model.ModelResponse;
import reactor.core.publisher.Flux;
import org.springframework.ai.chat.model.Generation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BannedAdvisor  implements CallAroundAdvisor, StreamAroundAdvisor {

    private static final String FORBIDDEN_WORD = "中国";
    private static final String BLOCKED_MESSAGE = "您的请求包含违禁词，请修改后重试";
    private static final String BLOCKED_KEY = "banned.blocked";
    private static final String REASON_KEY = "banned.reason";

    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        String userPrompt = advisedRequest.userText();
        if(containsForbiddenWord(userPrompt)) {
            ChatResponse response = new ChatResponse(Collections.singletonList(
                    new Generation(new AssistantMessage(BLOCKED_MESSAGE))
            ));

            // 创建上下文信息
            Map<String, Object> context = new HashMap<>();
            context.put(BLOCKED_KEY, true);
            context.put(REASON_KEY, "（普通）检测到违禁词: " + FORBIDDEN_WORD);
            return new AdvisedResponse(response,context);
        }
        return chain.nextAroundCall(advisedRequest);
    }

    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        String userPrompt = advisedRequest.userText();
        if(containsForbiddenWord(userPrompt)) {
            ChatResponse response = new ChatResponse(Collections.singletonList(
                    new Generation(new AssistantMessage(BLOCKED_MESSAGE))
            ));
            // 创建上下文信息
            Map<String, Object> context = new HashMap<>();
            context.put(BLOCKED_KEY, true);
            context.put(REASON_KEY, "（流式）检测到违禁词: " + FORBIDDEN_WORD);

            Flux.just(new AdvisedResponse(response, context));
        }
        return chain.nextAroundStream(advisedRequest);
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

    private boolean containsForbiddenWord(String input) {
        // 增强检查逻辑（忽略大小写）
        return input.toLowerCase().contains(FORBIDDEN_WORD.toLowerCase());
    }
}
