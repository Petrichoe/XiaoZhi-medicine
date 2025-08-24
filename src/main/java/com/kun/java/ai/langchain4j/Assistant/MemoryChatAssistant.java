package com.kun.java.ai.langchain4j.Assistant;

import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * 记忆对话
 */
@AiService(
 wiringMode = EXPLICIT,
 chatModel = "openAiChatModel",
 chatMemory = "chatMemory"
 )
 public interface MemoryChatAssistant {
   String chat(String message);
 }