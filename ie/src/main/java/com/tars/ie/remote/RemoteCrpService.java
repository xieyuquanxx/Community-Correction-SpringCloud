package com.tars.ie.remote;

import com.tars.ie.api.ResponseResult;
import com.tars.ie.entity.other.CorrectionPeople;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "ie-ic", url = "http://localhost:9099/ic/crp")
public interface RemoteCrpService {

    @RequestMapping(method = RequestMethod.POST, value = "/register"
            , consumes = "application/json")
    ResponseResult<Boolean> register(@RequestBody CorrectionPeople crp);

    @RequestMapping(method = RequestMethod.POST, value = "/dcpg/{dxbh}"
            , consumes = "application/json")
    ResponseResult<Boolean> finishDcpg(@PathVariable("dxbh") String dxbh);


}
