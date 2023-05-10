package com.tars.ic.remote;

import com.tars.ic.entity.CorrectionPeople;
import com.tars.ic.entity.others.BBInfo;
import com.tars.ic.entity.others.Exit;
import com.tars.ic.entity.others.ZJInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "ic-noexit", url = "http://localhost:9099/noexit")
public interface RemoteNoExitService {

  @RequestMapping(method = RequestMethod.POST, value = "/bb/save"
      , consumes = "application/json")
  void saveBBInfo(BBInfo bbInfo);

  @RequestMapping(method = RequestMethod.POST, value = "/zj/save"
      , consumes = "application/json")
  void saveZJInfo(ZJInfo bbInfo);

  @RequestMapping(method = RequestMethod.POST, value = "/info" +
      "/save", consumes = "application/json")
  void saveExitInfo(Exit exit);

}
