package com.tars.ie.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.tars.ie.api.ResponseResult;
import com.tars.ie.oss.entity.OssCallbackResult;
import com.tars.ie.oss.entity.OssPolicyResult;
import com.tars.ie.oss.service.OssServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
public class OssController {
    @Autowired
    private OssServiceImpl ossService;
    @Autowired
    private OSSClient ossClient;

    public ResponseResult<OssPolicyResult> policy() {
        OssPolicyResult result = ossService.policy();
        return ResponseResult.success(result);
    }


    public PutObjectRequest getPutObject(OssPolicyResult policy,
                                         MultipartFile file,
                                         ObjectMetadata metadata) throws IOException {
        if (metadata != null) {
            return
                    new PutObjectRequest("ccorr-bucket",
                            policy.getDir() + "/" + file.getOriginalFilename(),
                            new ByteArrayInputStream(
                                    file.getBytes()));
        } else {
            return
                    new PutObjectRequest("ccorr-bucket",
                            policy.getDir() + "/" + file.getOriginalFilename(),
                            file.getInputStream(), metadata);
        }
    }

    public ResponseResult<String> uploadFile(MultipartFile file,
                                             ObjectMetadata metadata) {
        try {
            System.out.println(metadata);
            OssPolicyResult policy = policy().getData();

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

//    public ResponseResult<OssCallbackResult> callback
//    (HttpServletRequest request) {
//        OssCallbackResult ossCallbackResult = ossService.callback(
//                request);
//        return ResponseResult.success(ossCallbackResult);
//    }
}
