package com.tars.ic.remote;

import com.tars.ic.entity.others.ScoreInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "ic-assess", url = "http://localhost:9099/assess")
public interface RemoteAssessmentService {

    @RequestMapping(method = RequestMethod.POST, value = "/score/init", consumes = "application/json")
    void saveScore(ScoreInfo scoreInfo);
}
