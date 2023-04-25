package com.tars.ic.service.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "oss", url = "https://ccorr-bucket.oss-cn-shenzhen.aliyuncs.com")
public interface RemoteOssService {
    @RequestMapping(method = RequestMethod.POST, value = "/",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void upload(@RequestBody MultiValueMap<String, Object> params);
}
