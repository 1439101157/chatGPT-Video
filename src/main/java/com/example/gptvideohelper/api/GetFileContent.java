package com.example.gptvideohelper.api;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.io.file.FileReader;
public class GetFileContent {

    public static void main(String[] args) throws IOException {
        File file=new File("filetree.txt");
        // 创建一个BufferedReader对象，用来读取txt文件中的每一行路径
        FileReader reader =new FileReader(file.getAbsolutePath());
        // 创建一个BufferedWriter对象，用来写入另一个txt文本中
        BufferedWriter writer = new BufferedWriter(new FileWriter("filecontent.txt"));
        // 定义一个字符串变量，用来存储每一行路径
        List<String> lineList = reader.readLines();
        // 使用循环，逐行读取路径，并判断是否为空
        for(String line : lineList) {
            // 如果路径不为空，就创建一个FileInputStream对象，用来读取该路径对应的文件内容
            FileReader fis = new FileReader(line);
            // 使用BufferedWriter.write()方法，将字节数组转换成字符串，并写入到另一个txt文本中
            writer.write(fis.readString());
            // 在每个文件内容之后，添加一个换行符，以便区分不同的文件
            writer.newLine();
        }
        writer.close();
    }
}