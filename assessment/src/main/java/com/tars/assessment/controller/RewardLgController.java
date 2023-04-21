package com.tars.assessment.controller;

import com.tars.assessment.api.ResponseResult;
import com.tars.assessment.entity.RewardLgInfo;
import com.tars.assessment.entity.ScoreDetail;
import com.tars.assessment.service.RewardLgService;
import com.tars.assessment.utils.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assess/reward/lg")
@CrossOrigin(origins = "*")
public class RewardLgController {
    @Autowired
    private RewardLgService lgService;
    @Autowired
    private ScoreController scoreController;
    @Autowired
    private RewardLgTaskController taskController;

    private ScoreDetail lgScoreDetail(String dxbh) {
        ScoreDetail detail = new ScoreDetail();
        detail.setDxbh(dxbh);
        detail.setScore(20); // 立功+10分
        detail.setReason("立功加分");
        detail.setDate(DateHelper.getNow());
        return detail;
    }

    @GetMapping("/{id}")
    public ResponseResult<RewardLgInfo> getRewardLgInfo(@PathVariable("id") String id) {
        RewardLgInfo info = lgService.query()
                                     .eq("id", id)
                                     .one();
        if (info == null)
            return ResponseResult.fail(null, "没有找到立功信息!");
        return ResponseResult.success(info);
    }

    @PostMapping("/update")
    public ResponseResult<Boolean> updateRewardPraise(@RequestBody RewardLgInfo info) {
        try {
            info = taskController.xjComplete(info);
            lgService.update().eq("id", info.getId())
                     .update(info);

            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, e.getMessage());
        }
    }

    // 市局模拟审批
    @PostMapping("/impl")
    public ResponseResult<Boolean> implSj(@RequestBody RewardLgInfo info) {
        try {
            info = taskController.sjComplete(info);
            System.out.println(info);
            lgService.update().eq("id", info.getId())
                     .update(info);
            //  审批通过才加分
            if (info.getSpjg().equals("01")) {
                ScoreDetail detail = lgScoreDetail(info.getDxbh());
                scoreController.saveScore(detail);
            }
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseResult<RewardLgInfo> saveLgReward(@RequestBody RewardLgInfo info) {
        try {
            info.setProcessId(taskController.startProcessInstance());
            lgService.save(info);
            return ResponseResult.success(info);
        } catch (Exception e) {
            return ResponseResult.fail(null, e.getMessage());
        }
    }
}
