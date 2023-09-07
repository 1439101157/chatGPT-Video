package com.example.gptvideohelper.gptapi;

import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.listener.ConsoleStreamListener;
import com.plexpt.chatgpt.util.Proxys;

import java.net.Proxy;
import java.util.Arrays;
import java.util.Scanner;

import static com.example.gptvideohelper.common.ConfigParameter.GPT_API_KEY;
import static com.example.gptvideohelper.common.ConfigParameter.PROXY_IP;
import static com.example.gptvideohelper.common.ConfigParameter.PROXY_PORT;

public class ShortChatWithGPT {
    public static void main(String[] args) {
        //国内需要代理 国外不需要
//创建一个HTTP代理对象，指定代理服务器的地址和端口
        Proxy proxy = Proxys.http(PROXY_IP,PROXY_PORT);

//创建一个ChatGPTStream对象，用于调用OpenAI的GPT-3聊天接口的流式版本
        ChatGPTStream chatGPTStream = ChatGPTStream.builder()
                .timeout(600) //设置超时时间（秒）
                .apiKey(GPT_API_KEY) //设置API密钥
                .proxy(proxy) //设置代理
                .apiHost("https://api.openai.com/") //设置API主机地址
                .build() //构建对象
                .init(); //初始化
//创建一个Scanner对象，用于从控制台读取用户输入
        Scanner scanner = new Scanner(System.in);
        String text = "1";
//创建一个ConsoleStreamListener对象，用于监听流式聊天的结果并打印到控制台
        ConsoleStreamListener listener = new ConsoleStreamListener();
//循环读取用户输入，直到输入为空或为"结束"时退出
        while (text != null&&!text.isEmpty()) {
            text = scanner.nextLine();
            if (!text.equals("结束")) {
                //创建一个用户消息对象，用于输入聊天内容
                Message message = Message.of(text);
                //创建一个ChatCompletion对象，用于设置聊天参数
                ChatCompletion chatCompletion = ChatCompletion.builder()
                        .messages(Arrays.asList(message)) //设置消息列表
                        .build(); //构建对象
                //调用streamChatCompletion方法，传入ChatCompletion对象和ConsoleStreamListener对象，开始流式聊天
                chatGPTStream.streamChatCompletion(chatCompletion, listener);
            } else break;
        }
    }
}
