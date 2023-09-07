package com.example.gptvideohelper.gptapi;

import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.listener.ConsoleStreamListener;
import com.plexpt.chatgpt.util.ChatContextHolder;
import com.plexpt.chatgpt.util.Proxys;

import java.net.Proxy;
import java.util.*;

import static com.example.gptvideohelper.common.ConfigParameter.GPT_API_KEY;
import static com.example.gptvideohelper.common.ConfigParameter.PROXY_IP;
import static com.example.gptvideohelper.common.ConfigParameter.PROXY_PORT;

// 这是一个Java类，用于测试使用Java向chatGPT API发送文本信息的功能
public class MediumChatWithGPT {
    // 这是主方法，程序的入口点
    public static void main(String[] args) {
        ChatContextHolder chatContextHolder = new ChatContextHolder();
        List<Message> Messages=new ArrayList<>();
        String userId="supZziy";
        //国内需要代理 国外不需要
        Proxy proxy = Proxys.http(PROXY_IP, PROXY_PORT);
        ChatGPTStream chatGPTStream = ChatGPTStream.builder()
                .timeout(600)
                .apiKey(GPT_API_KEY)
                .proxy(proxy)
                .apiHost("https://api.openai.com/")
                .build()
                .init();
        Scanner scanner = new Scanner(System.in);
        String text="1";
        Message systeMessage = Message.of("这是一个视频提取出来的内容，可能有部分内容是同音字或者词，需要你自己辨别，在辨别后请分析后给出视频大意");//系统提示词
        chatContextHolder.add(userId,systeMessage);
        ConsoleStreamListener listener = new ConsoleStreamListener();
        while (text != null&&!text.isEmpty()) {
            System.out.print("\n");
            text = scanner.nextLine();
            System.out.print("\n");
            if (!text.equals("结束")) {
                Message message = Message.of(text);
                chatContextHolder.add(userId, message);
                Messages=chatContextHolder.get(userId);
                ChatCompletion chatCompletion = ChatCompletion.builder()
                        .messages(Messages)
                        .build();
                System.out.println("chatGPT说：");
                chatGPTStream.streamChatCompletion(chatCompletion, listener);
            } else break;
        }
    }
}
