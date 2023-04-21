package com.tars.assessment.controller;

import com.tars.assessment.api.ResponseResult;
import com.tars.assessment.entity.ScoreDetail;
import com.tars.assessment.entity.ScoreInfo;
import com.tars.assessment.service.ScoreDetailService;
import com.tars.assessment.service.ScoreInfoService;
import com.tars.assessment.utils.CrpHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/assess/score")
@CrossOrigin(origins = "*")
public class ScoreController {
    @Autowired
    private ScoreInfoService infoService;
    @Autowired
    private ScoreDetailService detailService;

    @GetMapping("/all")
    public ResponseResult<List<ScoreInfo>> getAllScores() {
        List<ScoreInfo> list = infoService.list()
                                          .stream().peek(e -> e.setXm(
                        CrpHelper.getXm(e.getDxbh())))
                                          .toList();
        return ResponseResult.success(list);
    }

    @GetMapping("/{id}")
    public ResponseResult<List<ScoreDetail>> getScoreDetailById(@PathVariable("id") String id) {
        return ResponseResult.success(detailService.query()
                                                   .eq("dxbh", id)
                                                   .list());
    }

    @PostMapping("/save")
    public ResponseResult<Boolean> saveScore(@RequestBody ScoreDetail detail) {
        try {
            infoService.update()
                       .eq("dxbh", detail.getDxbh())
                       .setSql("score = score + (" + detail.getScore() + ")")
                       .update();
            detailService.save(detail);
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, e.getMessage());
        }
    }

    @PostMapping("/init")
    public void initScore(@RequestBody ScoreInfo detail) {
        infoService.save(detail);
    }
}
