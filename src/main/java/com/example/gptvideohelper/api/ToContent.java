package com.example.gptvideohelper.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

import static com.example.gptvideohelper.common.ConfigParameter.TXT_Path;

public class ToContent {
    /*
     * @Author SuperZizzy
     * @Description //TODO 音频转文本
     * @Date 19:03 2023/9/5
     * @Param []
     * @return void
     **/
    public void getContent() {

// 从txt文件中读取JSON字符串
        String json = "";
        try {
            // 创建一个BufferedReader对象，用于读取txt文件
            BufferedReader br = new BufferedReader(new FileReader(TXT_Path));

            // 创建一个StringBuffer对象，用于存储JSON字符串
            StringBuffer sb = new StringBuffer();

            // 读取txt文件中的每一行，并拼接到StringBuffer对象中
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            // 关闭BufferedReader对象
            br.close();

            // 将StringBuffer对象转换为字符串
            json = sb.toString();
        } catch (IOException e) {
            // 处理异常
            e.printStackTrace();
        }


// 使用JSONObject构造器将JSON字符串转换为JSONObject对象
        JSONObject obj = new JSONObject(json);

// 获取content字段的值，是一个JSONObject对象
        JSONObject content = obj.getJSONObject("content");

// 获取orderResult字段的值，是一个JSON字符串
        String orderResult = content.getString("orderResult");

// 使用JSONObject构造器将orderResult转换为JSONObject对象
        JSONObject result = new JSONObject(orderResult);

// 获取lattice字段的值，是一个JSONArray对象
        JSONArray lattice = result.getJSONArray("lattice");

// 创建一个StringBuffer对象，用于拼接转写结果中的主要内容
        StringBuffer text = new StringBuffer();

// 遍历lattice数组中的每个元素，是一个JSONObject对象
        for (int i = 0; i < lattice.length(); i++) {
            // 获取json_1best字段的值，是一个JSON字符串
            String json_1best = lattice.getJSONObject(i).getString("json_1best");

            // 使用JSONObject构造器将json_1best转换为JSONObject对象
            JSONObject best = new JSONObject(json_1best);

            // 获取st字段的值，是一个JSONObject对象
            JSONObject st = best.getJSONObject("st");

            // 获取rt字段的值，是一个JSONArray对象
            JSONArray rt = st.getJSONArray("rt");

            // 遍历rt数组中的每个元素，是一个JSONObject对象
            for (int j = 0; j < rt.length(); j++) {
                // 获取ws字段的值，是一个JSONArray对象
                JSONArray ws = rt.getJSONObject(j).getJSONArray("ws");

                // 遍历ws数组中的每个元素，是一个JSONObject对象
                for (int k = 0; k < ws.length(); k++) {
                    // 获取cw字段的值，是一个JSONArray对象
                    JSONArray cw = ws.getJSONObject(k).getJSONArray("cw");

                    // 获取cw数组中第一个元素（最佳候选词），是一个JSONObject对象
                    JSONObject word = cw.getJSONObject(0);

                    // 获取w字段的值，是一个字符串，表示词
                    String w = word.getString("w");

                    // 将词拼接到StringBuffer对象中，并添加空格分隔
                    text.append(w);
                }
            }
        }

// 删除最后一个多余的空格
        text.deleteCharAt(text.length() - 1);

// 将StringBuffer对象转换为字符串，并输出或保存到文件中
        System.out.println(text.toString());

    }
}
