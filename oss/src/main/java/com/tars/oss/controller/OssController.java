package com.tars.oss.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.tars.oss.api.ResponseResult;
import com.tars.oss.entity.OssPolicyResult;
import com.tars.oss.service.OssService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/oss")
@CrossOrigin(origins = "*")
public class OssController {

    @Autowired
    private OssService ossService;
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

    public PutObjectRequest getPutObject(OssPolicyResult policy,
                                         File file,
                                         ObjectMetadata metadata) {

        return
                new PutObjectRequest(bucketName,
                        policy.getDir() + "/" + file.getName(),
                        file, metadata);
    }


    @PostMapping("/upload")
    public ResponseResult<String> uploadFile(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            System.out.println(file.toString());
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

    @PostMapping("/upload_f")
    public ResponseResult<String> uploadFile(
            @RequestParam("file") File file
    ) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentEncoding("utf-8");

            OssPolicyResult policy = ossService.policy();
            String type =
                    Objects.requireNonNull(file.getName())
                            .split("\\.")[1];
            System.out.println(type);
            policy.setDir(policy.getDir() + "/" + type);

            PutObjectRequest putObjectRequest =
                    getPutObject(policy, file, metadata);

            ossClient.putObject(putObjectRequest);

            String url = "https://ccorr-bucket.oss-cn-shenzhen" +
                    ".aliyuncs.com/" +
                    policy.getDir() + "/" + file.getName();
            System.out.println(url);
            return ResponseResult.success(url);
        } catch (Exception e) {
            return ResponseResult.fail("", e.getMessage());
        }
    }
}
