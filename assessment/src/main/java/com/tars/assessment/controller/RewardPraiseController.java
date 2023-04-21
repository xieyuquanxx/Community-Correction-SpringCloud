package com.tars.assessment.controller;

import com.tars.assessment.api.ResponseResult;
import com.tars.assessment.entity.RewardPraiseInfo;
import com.tars.assessment.entity.ScoreDetail;
import com.tars.assessment.service.RewardPraiseService;
import com.tars.assessment.utils.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assess/reward/praise")
@CrossOrigin(origins = "*")
public class RewardPraiseController {
    @Autowired
    private RewardPraiseService praiseService;
    @Autowired
    private ScoreController scoreController;

    private ScoreDetail praiseScoreDetail(String dxbh) {
        ScoreDetail detail = new ScoreDetail();
        detail.setDxbh(dxbh);
        detail.setScore(10); // 表扬+10分
        detail.setReason("表扬加分");
        detail.setDate(DateHelper.getNow());
        return detail;
    }

    @GetMapping("/{id}")
    public ResponseResult<RewardPraiseInfo> getRewardPraiseInfo(@PathVariable("id") String id) {
        RewardPraiseInfo info = praiseService.query()
                                             .eq("id", id)
                                             .one();
        if (info == null)
            return ResponseResult.fail(null, "没有找到表扬信息!");
        return ResponseResult.success(info);
    }

    @PostMapping("/update")
    public ResponseResult<Boolean> updateRewardPraise(@RequestBody RewardPraiseInfo info) {
        try {
            info.setStep(info.getStep() + 1);
            System.out.println(info);
            praiseService.update().eq("id", info.getId())
                         .update(info);
            // todo 加分
            ScoreDetail detail = praiseScoreDetail(info.getDxbh());
            scoreController.saveScore(detail);

            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseResult<Boolean> saveRewardPraise(@RequestBody RewardPraiseInfo info) {
        try {
            praiseService.save(info);
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, e.getMessage());
        }
    }
}
