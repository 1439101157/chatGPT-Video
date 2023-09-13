package com.example.gptvideohelper.api;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import java.util.ArrayList;
import java.util.List;


public class GetExcel {
    // 读取Excel文件的文本信息
    public static void main(String[] args) {
        ExcelReader reader = ExcelUtil.getReader("C:\\Users\\Administrator\\Desktop\\1_华润费用(结算).xlsx");
        List<List<Object>> readAll = reader.read();
        List<String>rowStrings=new ArrayList<>();
        for(List<Object> list : readAll){
            String rowString= StrUtil.join(",",list);
            rowStrings.add(rowString);
        }
        FileWriter fileWriter=new FileWriter("xlsx.txt");
        String result= CollUtil.join(rowStrings,"\n");
        fileWriter.write(result);
    }
}
