package com.example.gptvideohelper.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

import java.io.*;

import static com.example.gptvideohelper.common.ConfigParameter.VIDEO_PATH;

public class VideoDownloader {
    /*
     * @Author SuperZizzy
     * @Description //TODO 视频文件下载
     * @Date 19:02 2023/9/5
     * @Param [videoUrl]
     * @return void
     **/
    public void download(String videoUrl) {
        File file = new File(VIDEO_PATH);
        if (file.exists()) {
            throw new RuntimeException("文件已存在，程序终止");
        }

        // 其他代码

        String savePath = VIDEO_PATH;
        long fileSize = 0; // 存储文件总大小

        // 循环下载文件块
        while (true) {
            HttpResponse response = HttpRequest.get(videoUrl)
                    .header("Accept", "改成你的Accept值")
                    .header("Accept-Encoding", "改成你的请求头编码方式")
                    .header("Accept-Language", "改成你的语言偏好的值")//语言偏好
                    .header("Origin", "改成你的视频链接地址")
                    .header("Range", "bytes=0-1048575")//请求数据范围 改成你要的值
                    .header("Referer", "改成你的视频链接地址")
                    .header("User-Agent", "改成你的用户标识")
                    .header("Upgrade-Insecure-Requests", "1")
                    .header("Range", "bytes=" + fileSize + "-") // 设置Range头部，从已下载的字节后开始下载
                    .execute();

            if (response.isOk()) {
                byte[] blockBytes = response.bodyBytes();
                int blockSize = blockBytes.length;

                if (blockSize == 0) {
                    // 如果已下载的文件块大小为0，说明已下载完整个文件
                    System.out.println("下载完成!");
                    break;
                }

                // 追加写入文件块
                try (FileOutputStream outputStream = new FileOutputStream(savePath, true)) {
                    outputStream.write(blockBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fileSize += blockSize;

                System.out.println("已下载的文件大小: " + blockSize);
            } else {
                if(response.getStatus()==416)
                {
                    System.out.println("下载完成或者有416错误");
                }
                System.err.println("下载完成请检查或者文件块下载失败. HTTP状态码为: " + response.getStatus());
                break;
            }
        }
    }
}