package com.kun.java.ai.langchain4j.Assistant;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * 普通状态下对话
 */
@AiService(wiringMode = EXPLICIT,
        chatModel = "openAiChatModel"
)   //有这个注解使用时直接调用即可

public interface Assistant {
 String chat(String userMessage);
 }