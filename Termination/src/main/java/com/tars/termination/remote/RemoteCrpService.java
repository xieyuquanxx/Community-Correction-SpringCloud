package com.tars.termination.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "crp", url = "http://localhost:9099/ic/crp")
public interface RemoteCrpService {
    @RequestMapping(method = RequestMethod.GET, value = "/xm/{dxbh" +
            "}", consumes = "application/json")
    String getName(@PathVariable("dxbh") String dxbh);

    @RequestMapping(method = RequestMethod.GET, value = "/random", consumes = "application/json")
    String randomDxbh();
}
