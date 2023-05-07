package com.tars.word.remote;

import com.tars.word.api.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@FeignClient(value = "oss", url = "http://localhost:9099/oss")
public interface RemoteOssService {

    @RequestMapping(method = RequestMethod.POST, value = "/upload_f")
    ResponseResult<String> upload(@RequestParam("file") File file);

    @RequestMapping(method = RequestMethod.POST, value = "/upload",
            produces = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    ResponseResult<String> upload(@RequestParam("file") MultipartFile file);
}