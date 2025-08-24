package com.kun.java.ai.langchain4j;

import com.kun.java.ai.langchain4j.Assistant.Assistant;
import com.kun.java.ai.langchain4j.Assistant.MemoryChatAssistant;
import com.kun.java.ai.langchain4j.Assistant.SeparateChatAssistant;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class ChatMemoryTest {

    @Autowired
    private OpenAiChatModel  openAiChatModel;

    @Test
    public void testChatMemory1() {
        //第一轮对话
        UserMessage userMessage1 = UserMessage.userMessage("我是环环");
        ChatResponse chatResponse1 = openAiChatModel.chat(userMessage1);
        AiMessage aiMessage1 = chatResponse1.aiMessage();
        //输出大语言模型的回复
        System.out.println(aiMessage1.text());
        //第二轮对话
        UserMessage userMessage2 = UserMessage.userMessage("你知道我是谁吗");
        ChatResponse chatResponse2 = openAiChatModel.chat(Arrays.asList(userMessage1,
                aiMessage1, userMessage2));
        AiMessage aiMessage2 = chatResponse2.aiMessage();
        //输出大语言模型的回复
        System.out.println(aiMessage2.text());
    }


    @Test
    public void testChatMemory2() {
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        Assistant assistant=AiServices
                .builder(Assistant.class)
                .chatModel(openAiChatModel)
                .chatMemory(chatMemory)
                .build();

        String result = assistant.chat("我是欢欢");
        System.out.println(result);
        String chat = assistant.chat("你知道我是谁吗");
        System.out.println(chat);

    }

    @Autowired
    private MemoryChatAssistant memoryChatAssistant;

    @Test
    public void testChatMemory3() {
        String chat = memoryChatAssistant.chat("我是欢欢");
        System.out.println(chat);
        String chat1 = memoryChatAssistant.chat("你知道我是谁吗");
        System.out.println(chat1);
    }

    @Autowired
    private SeparateChatAssistant separateChatAssistant;

    @Test
    public void testChatMemory4() {
        String chat = separateChatAssistant.chat(1,"我是欢欢");
        System.out.println("chat:"+chat);
        String chat1 = separateChatAssistant.chat(1,"你知道我是谁吗");
        System.out.println("chat1:"+chat1);
        String chat2 = separateChatAssistant.chat(2,"你知道我是谁吗");
        System.out.println("chat2:"+chat2);
    }


}
