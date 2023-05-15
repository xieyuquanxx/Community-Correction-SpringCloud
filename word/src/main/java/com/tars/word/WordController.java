package com.tars.word;

import com.tars.word.api.ResponseResult;
import com.tars.word.remote.RemoteOssService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/word")
@CrossOrigin(origins = "*")
public class WordController {

  @Autowired
  private RemoteOssService ossService;


  @Value("${export_word.outDirectory}")
  private String basePath;
  @Value("${export_word.basePackagePath}")
  private String basePackagePath;


  public File create(Map<String, Object> dataModel, String templateName,
      String filePath) {

    try {
      filePath = basePath + filePath;
      File file = new File(filePath);

      if (!file.getParentFile().exists()) {
        file.getParentFile().mkdirs();
      }

      BufferedWriter bufWrite = new BufferedWriter(
          new OutputStreamWriter(new FileOutputStream(filePath),
              StandardCharsets.UTF_8));

      // 创建配置实例
      Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
      // 设置编码
      configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
      // ftl模板文件
      configuration.setClassForTemplateLoading(WordController.class,
          basePackagePath);

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
    String fileName = ftlName.split("\\.")[0] + dataMap.get("dxbh") + ".doc";
    return create(dataMap, ftlName, "/word/" + fileName);
  }

  @PostMapping("/export")
  public ResponseResult<String> export(@RequestBody Map<String, Object> dataMap) {
    try {
      File file = exportAnnounceWord(dataMap);
      // 根据宣告信息生成word，上传到oss上后将url返回
      return ossService.upload(file);
    } catch (Exception e) {
      return ResponseResult.fail("", e.getMessage());
    }
  }

  @PostMapping("/info")
  public ResponseResult<String> exportInfo(
      @RequestBody Map<String, Object> dataMap) {
    try {
      String xmlName = (String) dataMap.get("xmlName");
      String filename = xmlName.split("\\.")[0] + dataMap.get("xm") + "信息表.doc";
      File file = create(dataMap, xmlName, "/word/" + filename);
      return ossService.upload(file);
    } catch (Exception e) {
      return ResponseResult.fail("", e.getMessage());
    }
  }

}
