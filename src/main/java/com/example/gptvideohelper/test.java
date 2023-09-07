package com.example.gptvideohelper;

import com.example.gptvideohelper.api.*;
import com.example.gptvideohelper.service.*;

import java.io.IOException;
import java.security.SignatureException;
import java.util.Scanner;

public class test {
    public static void main(String[] args) throws IOException, SignatureException, InterruptedException {
        GetHeader getBilibiliCid = new GetHeader();
        GetRealUrl getRealUrl = new GetRealUrl();
        VideoDownloader videoDownloader = new VideoDownloader();
        Scanner scanner = new Scanner(System.in);
        ToContent toContent = new ToContent();
        Ifasrdemo ifasrdemo = new Ifasrdemo();
        VideoHelper videoHelper = new VideoHelper();

        String bv = scanner.nextLine();
        String cid = getBilibiliCid.getCid(bv);
        String realUrl = getRealUrl.getUrl(bv, cid);
        videoDownloader.download(realUrl);
        videoHelper.getAudio();
        ifasrdemo.getText();
        toContent.getContent();
    }
}
