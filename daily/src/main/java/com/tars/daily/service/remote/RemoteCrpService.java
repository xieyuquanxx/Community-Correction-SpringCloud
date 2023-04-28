package com.tars.daily.service.remote;

import com.tars.daily.api.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "crp", url = "http://localhost:9007/ic/crp")
public interface RemoteCrpService {
    @RequestMapping(method = RequestMethod.GET, value = "/xm/{dxbh" +
            "}", consumes = "application/json")
    String getName(@PathVariable("dxbh") String dxbh);

    @RequestMapping(method = RequestMethod.POST, value = "/nocheck" +
            "/update", consumes = "application/json")
    void firstCheck(@RequestBody String dxbh);

}
