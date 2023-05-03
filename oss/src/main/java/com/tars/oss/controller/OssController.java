package com.tars.oss.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.tars.oss.api.ResponseResult;
import com.tars.oss.entity.OssCallbackResult;
import com.tars.oss.entity.OssPolicyResult;
import com.tars.oss.service.OssServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/oss")
@CrossOrigin(origins = "*")
public class OssController {

  @Autowired
  private OssServiceImpl ossService;
  @Autowired
  private OSSClient ossClient;

  @Value("${aliyun.oss.bucketName}")
  private String bucketName;


  public PutObjectRequest getPutObject(OssPolicyResult policy,
      MultipartFile file,
      ObjectMetadata metadata) throws IOException {

    return
        new PutObjectRequest(bucketName,
            policy.getDir() + "/" + file.getOriginalFilename(),
            file.getInputStream(), metadata);

  }

  @PostMapping("/upload")
  public ResponseResult<String> uploadFile(
      MultipartFile file
  ) {
    try {
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentEncoding("utf-8");

      OssPolicyResult policy = ossService.policy();
      String type =
          Objects.requireNonNull(file.getContentType())
              .split("/")[0];
      System.out.println(type);
      policy.setDir(policy.getDir() + "/" + type);

      PutObjectRequest putObjectRequest =
          getPutObject(policy, file, metadata);

      ossClient.putObject(putObjectRequest);

      String url = "https://ccorr-bucket.oss-cn-shenzhen" +
          ".aliyuncs.com/" +
          policy.getDir() + "/" + file.getOriginalFilename();
      System.out.println(url);
      return ResponseResult.success(url);
    } catch (Exception e) {
      return ResponseResult.fail("", e.getMessage());
    }
  }


  //@ApiOperation(value = "oss上传成功回调")
  @RequestMapping(value = "/callback", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult<OssCallbackResult> callback(HttpServletRequest request) {
    OssCallbackResult ossCallbackResult = ossService.callback(
        request);
    return ResponseResult.success(ossCallbackResult);
  }
}
