package com.tars.assessment.controller;

import com.tars.assessment.api.ResponseResult;
import com.tars.assessment.entity.RewardInfo;
import com.tars.assessment.entity.RewardLgInfo;
import com.tars.assessment.entity.RewardPraiseInfo;
import com.tars.assessment.service.RewardInfoService;
import com.tars.assessment.service.remote.RemoteCrpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assess/reward")
@CrossOrigin(origins = "*")
public class RewardController {

  @Autowired
  private RewardInfoService infoService;
  @Autowired
  private RewardPraiseController praiseController;
  @Autowired
  private RewardLgController lgController;
  @Autowired
  private RemoteCrpService crpService;

  @GetMapping("/all")
  public ResponseResult<List<RewardInfo>> getAllRewards() {
    try {
      List<RewardInfo> list = infoService.list().stream()
          .peek(e -> e.setXm(
              crpService.getName(
                  e.getDxbh())))
          .toList();
      return ResponseResult.success(list);
    } catch (Exception e) {
      return ResponseResult.fail(null, e.getMessage());
    }

  }


  @PostMapping("/save")
  public ResponseResult<Boolean> saveRewardInfo(@RequestBody RewardInfo info) {
    try {
      // 根据奖励类别，生成不同的审批流程
      String jllb = info.getJllb();
      switch (jllb) {
        case "01": // 表扬
          RewardPraiseInfo praiseInfo =
              new RewardPraiseInfo();
          praiseInfo.setDxbh(info.getDxbh());
          praiseInfo.setStep(0);
          praiseController.saveRewardPraise(praiseInfo);
          info.setRewardId(praiseInfo.getId());
          break;
        case "02": // 立功
          RewardLgInfo lgInfo =
              new RewardLgInfo();
          lgInfo.setDxbh(info.getDxbh());
          lgInfo.setStep(0);
          // 开启一个进程
          lgInfo = lgController.saveLgReward(lgInfo)
              .getData();
          info.setRewardId(lgInfo.getId());
          break;
        case "03": // 重大立功
          break;
        case "04": // 减刑
          break;
      }

      infoService.save(info);
      return ResponseResult.success(true);
    } catch (Exception e) {
      return ResponseResult.fail(false, e.getMessage());
    }
  }
}
