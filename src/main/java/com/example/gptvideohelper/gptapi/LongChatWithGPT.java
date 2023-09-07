package com.example.gptvideohelper.gptapi;

import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.listener.ConsoleStreamListener;
import com.plexpt.chatgpt.util.ChatContextHolder;
import com.plexpt.chatgpt.util.Proxys;

import java.lang.reflect.Field;
import java.net.Proxy;
import java.util.*;

import static com.example.gptvideohelper.common.ConfigParameter.GPT_API_KEY;
import static com.example.gptvideohelper.common.ConfigParameter.PROXY_IP;
import static com.example.gptvideohelper.common.ConfigParameter.PROXY_PORT;

// 这是一个Java类，用于测试使用Java向chatGPT API发送文本信息的功能
public class LongChatWithGPT {
    // 这是主方法，程序的入口点
    public static void main(String[] args) {
        String video="";
        System.out.printf(video);
        ChatContextHolder chatContextHolder = new ChatContextHolder();
        List<Message> Messages=new ArrayList<>();
        String lastMessageValue="";
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
        String text = "1";
        ConsoleStreamListener listener = new ConsoleStreamListener();
        try {
            // 获取AbstractStreamListener类的Class对象
            Class<?> abstractStreamListenerClass = Class.forName("com.plexpt.chatgpt.listener.AbstractStreamListener"); // 替换成您的类的完整包名

            // 获取lastMessage字段
            Field lastMessageField = abstractStreamListenerClass.getDeclaredField("lastMessage");

            // 设置字段为可访问
            lastMessageField.setAccessible(true);

        while (text != null&&!text.isEmpty()) {
            System.out.print("\n");
            text = scanner.nextLine();
            // 获取lastMessage字段的值
            lastMessageValue = (String) lastMessageField.get(listener);
            Message GPTMessage = Message.of("我会在每次聊天中添加我们的聊天历史 以下chatGPT回复的聊天历史内容:"+lastMessageValue);
            lastMessageField.set(listener,"");
            System.out.print("\n");
            if (!text.equals("结束")) {
                Message message = Message.of(text);
                chatContextHolder.add(userId, message);
                chatContextHolder.add(userId,GPTMessage);
                Messages=chatContextHolder.get(userId);
                ChatCompletion chatCompletion = ChatCompletion.builder()
                        .messages(Messages)
                        .build();
                System.out.println("chatGPT说：");
                chatGPTStream.streamChatCompletion(chatCompletion, listener);
            } else break;
        }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
