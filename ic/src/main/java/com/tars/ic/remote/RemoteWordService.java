package com.tars.ic.remote;

import com.tars.ic.api.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@FeignClient(value = "ic-word", url = "http://localhost:9099/word")
public interface RemoteWordService {

    @RequestMapping(method = RequestMethod.POST, value = "/export"
    )
    ResponseResult<String> export(@RequestBody Map<String, Object> dataMap);
}