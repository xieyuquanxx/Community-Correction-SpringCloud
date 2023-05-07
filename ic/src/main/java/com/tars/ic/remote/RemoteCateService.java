package com.tars.ic.remote;

import com.tars.ic.entity.others.CrpCategory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "cate", url = "http://localhost:9099/cate")
public interface RemoteCateService {

    @RequestMapping(method = RequestMethod.POST, value = "/info" +
            "/save", consumes = "application/json")
    void saveCategory(CrpCategory category);
}
