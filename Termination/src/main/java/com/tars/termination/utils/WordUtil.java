package com.tars.termination.utils;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class WordUtil {
    private static final String basePath = "/Users/xieyuquan/IdeaProjects/ccorrection/termination/src/main/resources";
    private static final String basePackagePath = "/templates";

    // todo fix it
    public static MultipartFile getMultipartFile(File file) throws IOException {
        System.out.println(file.getAbsolutePath());
        FileInputStream fileInputStream = new FileInputStream(file);
        return new MockMultipartFile(file.getName(), file.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(),
                fileInputStream);
    }


    public static File create(Map<String, Object> dataModel, String templateName, String filePath) {

        try {
            filePath = basePath + filePath;
            File file = new File(filePath);

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            BufferedWriter bufWrite = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));

            // 创建配置实例
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
            // 设置编码
            configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
            // ftl模板文件
            configuration.setClassForTemplateLoading(WordUtil.class, basePackagePath);

            configuration.getTemplate(templateName).process(dataModel, bufWrite);

            bufWrite.flush();
            bufWrite.close();

            return file;
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static File exportAnnounceWord(String fileName, Map<String, Object> dataMap) {
        String ftlName = "announce.xml"; // 使用的模板文件
        File file = create(dataMap, ftlName, fileName);
        if (file != null) {
//                return getMultipartFile(file);
            return file;
        }
        return null;
    }

    public static void test1() {
        String fileName = "/word/test.doc"; // 输出word文件名字
        String ftlName = "announce.xml"; // 使用的模板文件

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("dxbh", "00000001");
        dataMap.put("xgrq", "2023-5-7");
        dataMap.put("xm", "谢xx");
        dataMap.put("finish", "完成");
        dataMap.put("date", "2023-5-7");

        File file = create(dataMap, ftlName, fileName);
        System.out.println(file.getName());
    }

}
