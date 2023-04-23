package com.tars.ic.service.remote;

import com.tars.ic.entity.CrpCategory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "cate", url = "http://localhost:9009/cate")
public interface RemoteCateService {

    @RequestMapping(method = RequestMethod.POST, value = "/info/save", consumes = "application/json")
    void saveCategory(CrpCategory category);
}
