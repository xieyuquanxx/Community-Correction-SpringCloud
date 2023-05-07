package com.tars.daily.remote;

import com.tars.daily.api.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "oss", url = "http://localhost:9099/oss")
public interface RemoteOssService {

    @RequestMapping(method = RequestMethod.POST, value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseResult<String> upload(MultipartFile file);
}
