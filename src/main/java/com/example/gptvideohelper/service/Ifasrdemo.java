package com.example.gptvideohelper.service;

import cn.hutool.json.JSONUtil;
import com.example.gptvideohelper.sign.LfasrSignature;
import com.example.gptvideohelper.utils.HttpUtil;
import com.google.gson.Gson;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.security.SignatureException;
import java.util.HashMap;

import static com.example.gptvideohelper.common.ConfigParameter.APPID;
import static com.example.gptvideohelper.common.ConfigParameter.KEY_SECRET;
import static com.example.gptvideohelper.common.ConfigParameter.TXT_Path;

/*
 * @Author SuperZizzy
 * @Description //TODO 讯飞接口
 * @Date 19:04 2023/9/6
 * @Param
 * @return
 **/
public class Ifasrdemo {
    private static final String HOST = "https://raasr.xfyun.cn";
    private static String AUDIO_FILE_PATH;
    private static final String appid = APPID;
    private static final String keySecret = KEY_SECRET;

    private static final Gson gson = new Gson();

    static {
        try {
            AUDIO_FILE_PATH = Ifasrdemo.class.getResource("/").toURI().getPath() + "/audio/audio.wav";
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public void getText() throws IOException, SignatureException, InterruptedException {
        String result = upload();
        String jsonStr = StringEscapeUtils.unescapeJavaScript(result);
        String orderId = String.valueOf(JSONUtil.getByPath(JSONUtil.parse(jsonStr), "content.orderId"));
        getResult(orderId);
    }

    private static String upload() throws SignatureException, FileNotFoundException {
        HashMap<String, Object> map = new HashMap<>(16);
        File audio = new File(AUDIO_FILE_PATH);
        String fileName = audio.getName();
        long fileSize = audio.length();
        map.put("appId", appid);
        map.put("fileSize", fileSize);
        map.put("fileName", fileName);
        map.put("duration", "200");
        LfasrSignature lfasrSignature = new LfasrSignature(appid, keySecret);
        map.put("signa", lfasrSignature.getSigna());
        map.put("ts", lfasrSignature.getTs());

        String paramString = HttpUtil.parseMapToPathParam(map);
        System.out.println("upload paramString:" + paramString);

        String url = HOST + "/v2/api/upload" + "?" + paramString;
        System.out.println("upload_url:" + url);
        String response = HttpUtil.iflyrecUpload(url, new FileInputStream(audio));

        System.out.println("upload response:" + response);
        return response;
    }

    private static String getResult(String orderId) throws SignatureException, InterruptedException, IOException {
        HashMap<String, Object> map = new HashMap<>(16);
        map.put("orderId", orderId);
        LfasrSignature lfasrSignature = new LfasrSignature(appid, keySecret);
        map.put("signa", lfasrSignature.getSigna());
        map.put("ts", lfasrSignature.getTs());
        map.put("appId", appid);
        map.put("resultType", "transfer,predict");
        String paramString = HttpUtil.parseMapToPathParam(map);
        String url = HOST + "/v2/api/getResult" + "?" + paramString;
        System.out.println("\nget_result_url:" + url);
        while (true) {
            String response = HttpUtil.iflyrecGet(url);
            JsonParse jsonParse = gson.fromJson(response, JsonParse.class);
            if (jsonParse.content.orderInfo.status == 4 || jsonParse.content.orderInfo.status == -1) {
                System.out.println("订单完成:" + response);
                write(response);
                return response;
            } else {
                System.out.println("进行中...，状态为:" + jsonParse.content.orderInfo.status);
                //建议使用回调的方式查询结果，查询接口有请求频率限制
                Thread.sleep(7000);
            }
        }
    }

    public static void write(String resp) throws IOException {
        //将写入转化为流的形式
        BufferedWriter bw = new BufferedWriter(new FileWriter(TXT_Path));
        String ss = resp;
        bw.write(ss);
        //关闭流
        bw.close();
        System.out.println("写入txt成功");
    }

    class JsonParse {
        Content content;
    }

    class Content {
        OrderInfo orderInfo;
    }

    class OrderInfo {
        Integer status;
    }
}
