package com.tars.assessment.controller;

import com.tars.assessment.api.ResponseResult;
import com.tars.assessment.entity.ScoreDetail;
import com.tars.assessment.entity.ScoreInfo;
import com.tars.assessment.service.ScoreDetailService;
import com.tars.assessment.service.ScoreInfoService;
import com.tars.assessment.service.remote.RemoteCrpService;
import com.tars.assessment.utils.CrpHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/assess/score")
@CrossOrigin(origins = "*")
@Slf4j
public class ScoreController {
    @Autowired
    private ScoreInfoService infoService;
    @Autowired
    private ScoreDetailService detailService;
    @Autowired
    private RemoteCrpService crpService;

    @GetMapping("/all")
    public ResponseResult<List<ScoreInfo>> getAllScores() {
        List<ScoreInfo> list = infoService.list()
                .stream().peek(e -> e.setXm(
                        crpService.getName(e.getDxbh())))
                .toList();
        return ResponseResult.success(list);
    }

    @GetMapping("/{id}")
    public ResponseResult<List<ScoreDetail>> getScoreDetailById(@PathVariable("id") String id) {
        try {
            List<ScoreDetail> list = detailService.query().eq("dxbh", id).list();
            return ResponseResult.success(list);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return ResponseResult.fail(null, e.getMessage());
        }
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
