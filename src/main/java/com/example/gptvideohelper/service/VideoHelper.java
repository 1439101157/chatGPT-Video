package com.example.gptvideohelper.service;

import ws.schild.jave.*;

import java.io.File;

import static com.example.gptvideohelper.common.ConfigParameter.AUDIO_PATH;
import static com.example.gptvideohelper.common.ConfigParameter.VIDEO_PATH;

public class VideoHelper {
    /*
     * @Author SuperZizzy
     * @Description //TODO 视频转音频
     * @Date 19:02 2023/9/5
     * @Param []
     * @return void
     **/

    public void getAudio(){
        // 创建一个视频文件对象，指定视频文件的路径
        File videoFile = new File(VIDEO_PATH);
        MultimediaObject videoObject = new MultimediaObject(videoFile);

// 创建一个音频文件对象，指定音频文件的路径和格式
        File audioFile = new File(AUDIO_PATH);

// 创建一个音频属性对象，指定音频的编码器、比特率、采样率和声道数
        AudioAttributes audioAttr = new AudioAttributes();
        audioAttr.setCodec("pcm_s16le");//无损音频编码 同时也是讯飞要求的格式 想要音频文件变小一点可以在这里改
        audioAttr.setBitRate(128000);
        audioAttr.setSamplingRate(44100);
        audioAttr.setChannels(2);

        // 创建一个编码属性对象，指定音频属性和空的视频属性
        EncodingAttributes encodingAttr = new EncodingAttributes();
        encodingAttr.setFormat("wav");
        encodingAttr.setAudioAttributes(audioAttr);
        encodingAttr.setVideoAttributes(null);

        // 创建一个编码器对象，用于执行转换操作
        Encoder encoder = new Encoder();

        try {
            // 调用编码器的encode方法，将视频文件转换成音频文件
            encoder.encode(videoObject, audioFile, encodingAttr);
            // 打印转换成功的信息
            System.out.println("视频转音频成功!");
        } catch (Exception e) {
            // 捕获并打印转换失败的异常信息
            e.printStackTrace();
        }
    }
}
