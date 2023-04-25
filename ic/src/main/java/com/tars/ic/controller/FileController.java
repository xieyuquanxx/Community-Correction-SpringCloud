package com.tars.ic.controller;

import com.tars.ic.api.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/ic/file")
@CrossOrigin(origins = "*")
@Slf4j
public class FileController {
    @Value("${file.upload.url}")
    private String uploadFilePath;

    // 先传一个文件看看
    @PostMapping("/upload")
    public ResponseResult<String> httpUpload(@RequestParam("file") MultipartFile file, HttpServletRequest req) {
        try {
            File uploadDir = new File(uploadFilePath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            log.info(uploadDir.getAbsolutePath());

            // 本地文件
            File localFile = new File(
                    uploadFilePath + File.separator + file.getOriginalFilename());

            // transfer to local
            file.transferTo(localFile);

            String url = req.getScheme() + "://" +
                    req.getServerName() + ":"
                    + req.getServerPort() + uploadFilePath + "/" + file.getOriginalFilename();
            System.out.println(url);

            return ResponseResult.success(url);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail(e.getMessage());
        }

    }
}
