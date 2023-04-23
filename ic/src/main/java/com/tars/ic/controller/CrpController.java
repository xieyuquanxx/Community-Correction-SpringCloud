package com.tars.ic.controller;

import com.tars.ic.api.ResponseResult;
import com.tars.ic.entity.*;
import com.tars.ic.entity.others.BBInfo;
import com.tars.ic.entity.others.Exit;
import com.tars.ic.entity.others.ScoreInfo;
import com.tars.ic.service.CrpCheckService;
import com.tars.ic.service.CrpService;
import com.tars.ic.service.remote.RemoteAssessmentService;
import com.tars.ic.service.remote.RemoteCateService;
import com.tars.ic.service.remote.RemoteNoExitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/ic/crp")
@CrossOrigin(origins = "*")
public class CrpController {
    @Autowired
    private CrpService service;
    @Autowired
    private CrpCheckService checkService;

    @Autowired
    private RemoteCateService cateService;
    @Autowired
    private RemoteAssessmentService assessmentService;
    @Autowired
    private RemoteNoExitService exitService;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("/xm/{dxbh}")
    public String getName(@PathVariable("dxbh") String dxbh) {
        return service.query()
                .eq("dxbh", dxbh)
                .select("xm")
                .one().getXm();
    }

    @GetMapping("/random")
    public String randomDxbh() {
        List<CorrectionPeople> list = service.list();
        Random random = new Random();
        int i = random.nextInt(list.size());
        return list.get(i).getDxbh();
    }


    @GetMapping("/test")
    public String hello() {
        return "Hello CorrectionPeople";
    }

    @GetMapping("/count")
    public ResponseResult<Long> getCount() {
        return ResponseResult.success(service.count());
    }

    @GetMapping("/all")
    public ResponseResult<List<CorrectionPeople>> getAll() {
        return ResponseResult.success(service.list());
    }

    @GetMapping("/nocheck/all")
    public ResponseResult<List<CrpCheck>> getNoCheckAll() {
        try {
            List<CrpCheck> list = checkService.query()
                    .eq("status", "0")
                    .list().stream()
                    .peek(e -> e.setXm(getName(e.getDxbh()))).toList();
            return ResponseResult.success(list);
        } catch (Exception e) {
            return ResponseResult.fail(null, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseResult<CorrectionPeople> getCrp(@PathVariable(
            "id") String id) {
        CorrectionPeople temp = service.query().eq("dxbh", id)
                .one();
        if (temp == null) {
            return ResponseResult.fail(null,
                    "没有找到对象编号为" + id + "的矫正对象");
        } else {
            return ResponseResult.success(temp);
        }
    }

    void initNoExit(CorrectionPeople crp) {
        BBInfo info = new BBInfo();
        info.setDxbh(crp.getDxbh());
        info.setXm(crp.getXm());
        exitService.saveBBInfo(info);

        Exit exit = new Exit();
        exit.setDxbh(crp.getDxbh());
        exit.setXm(crp.getXm());
        exitService.saveExitInfo(exit);
    }

    void initCategory(CorrectionPeople crp) {
        CrpCategory cate = new CrpCategory(crp.getDxbh(),
                crp.getXm(), "01");
        cateService.saveCategory(cate);
    }

    void initAssessmentScore(CorrectionPeople crp) {
        ScoreInfo scoreInfo = new ScoreInfo(crp.getDxbh(),
                crp.getXm(), 10);
        assessmentService.saveScore(scoreInfo);
    }

    @PostMapping("/register")
    public ResponseResult<Boolean> register(@RequestBody CorrectionPeople crp) {
        try {
            crp.setStatus("待入矫");
            crp.setSfdcpg("0");
            service.save(crp);
            // todo 还需要在 不准出境 模块生成出入境信息
            // todo 在分类管理模块下生成管理信息
            // todo 在奖励惩罚模块 记录 分数信息
            initNoExit(crp);
            initCategory(crp);
            initAssessmentScore(crp);
            // todo 创建未报到信息
            CrpCheck check = new CrpCheck(crp.getDxbh(),
                    crp.getXm(), formatter.format(new Date()), "0");
            checkService.save(check);

            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, e.getMessage());
        }
    }

    @PostMapping("/nocheck/update")
    public ResponseResult<Boolean> firstCheck(@RequestBody String dxbh) {
        try {
            checkService.update().eq("dxbh", dxbh)
                    .set("status", "1")
                    .update();
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "首次报到失败！");
        }
    }

    @PostMapping("/update")
    public ResponseResult<Boolean> update(@RequestBody CorrectionPeople crp) {
        try {
            service.update().eq("dxbh", crp.getDxbh())
                    .update(crp);
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败！");
        }
    }

    @PostMapping("/recv")
    public ResponseResult<Boolean> recv(@RequestBody CorrectionPeople crp) {
        try {
            service.update().eq("dxbh", crp.getDxbh())
                    .set("status", "在矫")
                    .update();

            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败！");
        }
    }
}
