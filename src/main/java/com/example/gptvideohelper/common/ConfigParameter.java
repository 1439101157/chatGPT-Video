package com.example.gptvideohelper.common;

public interface ConfigParameter {
    /*
     * @Author SuperZizzy
     * @Description //TODO 配置参数
     * @Date 19:04 2023/9/6
     * @Param
     * @return
     **/

    String PROXY_IP ="";//代理的IP地址 列如127.0.0.1
    int PROXY_PORT=7880;//代理走的端口号 列如7080 要改成自己的

    String TXT_Path = "src\\main\\resources\\output\\test.txt";
    String VIDEO_PATH = "src\\main\\resources\\video\\video.mp4";
    String APPID="";//讯飞API的接口
    String KEY_SECRET="";//讯飞API接口
    String AUDIO_PATH = "src\\main\\resources\\audio\\audio.wav";
    String GPT_API_KEY="";//chatGPT的API密钥
}
