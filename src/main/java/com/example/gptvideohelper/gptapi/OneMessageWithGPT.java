package com.example.gptvideohelper.gptapi;

import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.*;
import com.plexpt.chatgpt.util.Proxys;

import java.net.Proxy;
import java.util.Arrays;

import static com.example.gptvideohelper.common.ConfigParameter.GPT_API_KEY;
import static com.example.gptvideohelper.common.ConfigParameter.PROXY_IP;
import static com.example.gptvideohelper.common.ConfigParameter.PROXY_PORT;

public class OneMessageWithGPT {
    public static void main(String[] args) {
        //国内需要代理 国外不需要
        //创建一个HTTP代理对象，指定代理服务器的地址和端口
        Proxy proxy = Proxys.http(PROXY_IP, PROXY_PORT);

        //创建一个ChatGPT对象，用于调用OpenAI的GPT-3聊天接口
        ChatGPT chatGPT = ChatGPT.builder()
                .apiKey(GPT_API_KEY) //设置API密钥
                .proxy(proxy) //设置代理
                .timeout(900) //设置超时时间（秒）
                .apiHost("https://api.openai.com/") //设置API主机地址
                .build() //构建对象
                .init(); //初始化

        //创建一个系统消息对象，用于设置聊天话题
        Message system = Message.ofSystem("你现在是一个脱口秀达人，精通娱乐段子");
        //创建一个用户消息对象，用于输入聊天内容
        Message message = Message.of("给我讲一个与动物有关的段子");

//创建一个ChatCompletion对象，用于设置聊天参数
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName()) //设置模型名称
                .messages(Arrays.asList(system, message)) //设置消息列表
                .maxTokens(3000) //设置最大生成的单词数
                .temperature(0.9) //设置生成的随机性
                .build(); //构建对象
        //调用chatCompletion方法，传入ChatCompletion对象，返回一个ChatCompletionResponse对象
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        //从ChatCompletionResponse对象中获取第一个生成的消息对象
        Message res = response.getChoices().get(0).getMessage();
        //打印生成的消息内容
        System.out.println(res);
    }
}
