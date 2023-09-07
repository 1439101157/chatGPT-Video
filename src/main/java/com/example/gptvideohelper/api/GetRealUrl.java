package com.example.gptvideohelper.api;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import okhttp3.*;

import java.io.IOException;

public class GetRealUrl {
    /*
     * @Author SuperZizzy
     * @Description //TODO 获取到视频的真实地址
     * @Date 19:04 2023/9/5
     * @Param [parameter1, parameter2]
     * @return java.lang.String
     **/
    public String getUrl(String parameter1,String parameter2) throws IOException {
        String realUrl="";
        String url = "你需要获取视频的真实地址的API接口" + parameter1 + "参数2：" +parameter2;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        JSONObject json = new JSONObject(responseBody);
        JSONObject data = json.getJSONObject("data");
        //获取data2数组
        JSONArray data2 = data.getJSONArray("data2");//获取嵌套在data json内的其他属性数据
        //遍历data2数组
        for (int i = 0; i < data2.size(); i++) {
            //修改：获取durl数组中的每个元素
            JSONObject durlItem = data2.getJSONObject(i);
            //修改：获取每个元素中的url字段
            realUrl = durlItem.getStr("url");
            System.out.println(realUrl);
        }
        return realUrl;
    }
}

