package com.tars.word;

import com.tars.word.api.ResponseResult;
import com.tars.word.remote.RemoteOssService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/word")
@CrossOrigin(origins = "*")
public class WordController {
    @Value("${export_word.outDirectory}")
    private String basePath;
    @Value("${export_word.basePackagePath}")
    private String basePackagePath;
    @Autowired
    private RemoteOssService ossService;

    public File create(Map<String, Object> dataModel, String templateName, String filePath) {

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
            configuration.setClassForTemplateLoading(WordController.class, basePackagePath);

            configuration.getTemplate(templateName).process(dataModel, bufWrite);

            bufWrite.flush();
            bufWrite.close();

            return file;
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public File exportAnnounceWord(Map<String, Object> dataMap) {
        String ftlName = "announce.xml"; // 使用的模板文件
        String fileName = ftlName.split("\\.")[0] + new Random().nextInt() + ".doc";
        return create(dataMap, ftlName, "/word/" + fileName);
    }

    @PostMapping("/export")
    public ResponseResult<String> export(@RequestBody Map<String, Object> dataMap) {
        try {
//            String filename = announce.getDxbh() + "的终止矫正宣告书.doc";
            File file = exportAnnounceWord(dataMap);
            // 根据宣告信息生成word，上传到oss上后将url返回
            return ossService.upload(file);
        } catch (Exception e) {
            return ResponseResult.fail("", e.getMessage());
        }
    }

}
