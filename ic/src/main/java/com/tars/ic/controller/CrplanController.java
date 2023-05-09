package com.tars.ic.controller;

import com.tars.ic.api.ResponseResult;
import com.tars.ic.entity.CorrectionPeople;
import com.tars.ic.entity.CorrectionPlan;
import com.tars.ic.service.CrplanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ic/plan")
@CrossOrigin(origins = "*")
public class CrplanController {

    @Autowired
    private CrplanService service;
    @Autowired
    private CrpController crpController;
    private static final SimpleDateFormat formatter =
            new SimpleDateFormat(
                    "yyyy-MM-dd");

    @GetMapping("/count")
    public ResponseResult<Long> getCount() {
        try {
            return ResponseResult.success(service.count());
        } catch (Exception e) {
            return ResponseResult.fail(-1L, e.getMessage());
        }

    }

    @PostMapping("/upload")
    public ResponseResult<String> uploadPlan(
            @RequestParam("file") MultipartFile file) {
        return crpController.uploadFile(file);
    }

    @GetMapping("/all")
    public ResponseResult<List<CorrectionPlan>> getAll() {
        try {
            List<CorrectionPlan> list =
                    service.list().stream()
                            .peek(e -> {
                                CorrectionPeople crp =
                                        crpController.getCrp(
                                                        e.getDxbh())
                                                .getData();
                                e.setXm(crp.getXm());
                                e.setJzlb(
                                        crp.getJzlb());
                            }).toList();
            return ResponseResult.success(list);
        } catch (Exception e) {
            return ResponseResult.fail(null, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseResult<CorrectionPlan> getPlanById(@PathVariable("id") String id) {
        CorrectionPlan temp = service.query().eq("id", id).one();
        if (temp == null) {
            return ResponseResult.fail(null,
                    "没有找到方案编号为" + id + "的矫正方案");
        } else {
            temp.setXm(crpController.getName(temp.getDxbh()));
            return ResponseResult.success(temp);
        }
    }

    @GetMapping("/{dxbh}")
    public ResponseResult<CorrectionPlan> getPlanByDxbh(
            @PathVariable("dxbh") String dxbh
    ) {
        CorrectionPlan temp = service.query().eq("dxbh", dxbh).one();
        if (temp == null) {
            return ResponseResult.fail(null,
                    "没有找到对象编号为" + dxbh + "的矫正方案");
        } else {
            temp.setXm(crpController.getName(temp.getDxbh()));
            return ResponseResult.success(temp);
        }
    }

    @PostMapping("/save")
    public ResponseResult<Boolean> save(@RequestBody CorrectionPlan plan) {
        try {
            plan.setGmt_create(formatter.format(new Date()));
            return ResponseResult.success(service.save(plan));
        } catch (Exception e) {
            return ResponseResult.fail(false, e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseResult<Boolean> update(@RequestBody CorrectionPlan plan) {
        try {
            service.update().eq("dxbh", plan.getDxbh())
                    .set("gmt_modified", formatter.format(new Date()))
                    .update(plan);
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败！");
        }
    }
}
