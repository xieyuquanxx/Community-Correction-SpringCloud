package com.tars.termination.remote;

import com.tars.termination.api.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(value = "word", url = "http://localhost:9099/word")
public interface RemoteWordService {

    @RequestMapping(method = RequestMethod.POST, value = "/export"
    )
    ResponseResult<String> export(@RequestBody Map<String, Object> dataMap);
}
