package com.example.gptvideohelper.api;

import java.io.*;
import java.util.Arrays;

import static com.example.gptvideohelper.common.ConfigParameter.PROJECT_PATH;

public class GetFileTree {
    public static void crawlFiles(File file, BufferedWriter writer, int level) throws IOException {

        // 获取文件夹下的所有文件或子文件夹
        File[] files = file.listFiles();
        // 遍历数组
        for (File f : files) {
            // 如果是文件，就判断是否是Java代码文件
            if (f.isFile()) {
                // 获取文件名
                String fileName = f.getName();
                // 获取文件后缀名
                String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                // 定义一个数组，存储Java开发中会用到的各种文件后缀
                String[] suffixes = {"java", "xml", "properties", "sql", "jsp", "html", "css", "js"};
                // 判断文件后缀名是否在数组中
                boolean isJavaFile = Arrays.asList(suffixes).contains(suffix);
                // 如果是Java代码文件，就将其绝对路径写入到txt文件中，并根据层级深度添加缩进
                if (isJavaFile) {
                    for (int i = 0; i < level; i++) {
                        writer.write("\t");
                    }
                    // 替换\为\\
                    String path = f.getAbsolutePath().replace("\\", "\\\\");
                    writer.write(path);
                    writer.newLine();
                }
            } else if (f.isDirectory()) {
                // 如果是子文件夹，就判断是否是.git或者.idea文件夹
                if (!f.getName().equals(".git") && !f.getName().equalsIgnoreCase(".idea")&&!f.getName().equals(".mvn")) {
                    // 如果不是.git或者.idea文件夹，就继续递归遍历，并将层级深度加一
                    crawlFiles(f, writer, level + 1);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File folder = new File(PROJECT_PATH);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("filetree.txt"), "UTF-8"));
        crawlFiles(folder, writer, 0);
        writer.close();

    }
}
