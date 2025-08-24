package com.kun.java.ai.langchain4j;

import com.kun.java.ai.langchain4j.Assistant.SeparateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
 public class PromptTest {
 @Autowired
 private SeparateChatAssistant separateChatAssistant;

 @Test
 public void testSystemMessage() {
  String answer = separateChatAssistant.chat(3, "我是谁");
  System.out.println(answer);

 }

 @Test
 public void testUserInfo() {
  String answer = separateChatAssistant.chat3(4, "我是谁，我多大了", "翠花", 18);
  System.out.println(answer);
 }

}
 