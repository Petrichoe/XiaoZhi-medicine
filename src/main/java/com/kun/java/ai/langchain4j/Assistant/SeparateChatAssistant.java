package com.kun.java.ai.langchain4j.Assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * 记忆且分区对话
 */
@AiService(
 wiringMode = EXPLICIT, 
 chatModel = "openAiChatModel",
 chatMemoryProvider = "chatMemoryProvider",
 tools = "calculatorTools"
 )
 public interface SeparateChatAssistant {

    @SystemMessage(fromResource = "my-project-template.txt") //系统提示词
    String chat(@MemoryId int memoryId, @UserMessage String userMessage);


   @SystemMessage(fromResource = "my-prompt-template3.txt")
   String chat3(
           @MemoryId int memoryId,
           @UserMessage String userMessage, //用户提示词
           @V("username") String username,
           @V("age") int age
   );
 }
