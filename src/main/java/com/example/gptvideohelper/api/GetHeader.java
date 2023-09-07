package com.example.gptvideohelper.api;

import cn.hutool.json.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.Scanner;

public class GetHeader {
    /*
     * @Author SuperZizzy
     * @Description //TODO 获取标头需要的参数
     * @Date 19:03 2023/9/5
     * @Param [parameter]
     * @return java.lang.String
     **/
    public String getCid(String parameter) throws IOException {
        Scanner scanner =new Scanner(System.in);
        String url="你需要获取的参数的API接口"+parameter;
        OkHttpClient client = new OkHttpClient();
        Request request=new Request.Builder().url(url).build();
        Response response =client.newCall(request).execute();
        String responseBody=response.body().string();
        JSONObject json=new JSONObject(responseBody);
        JSONObject data=json.getJSONObject("data");
        String id=data.getStr("id");//拿到文件的id
        System.out.println(id);
        return id;
    }
}
