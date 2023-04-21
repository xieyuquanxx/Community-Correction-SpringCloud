package com.tars.ic.controller;

import com.tars.ic.api.ResponseResult;
import com.tars.ic.entity.CorrectionPeople;
import com.tars.ic.entity.CorrectionPlan;
import com.tars.ic.service.CrplanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ic/plan")
@CrossOrigin(origins = "*")
public class CrplanController {
    @Autowired
    private CrplanService service;
    @Autowired
    private CrpController crpController;

    @GetMapping("/count")
    public ResponseResult<Long> getCount() {
        return ResponseResult.success(service.count());
    }

    @GetMapping("/all")
    public ResponseResult<List<CorrectionPlan>> getAll() {
        List<CorrectionPlan> list = service.list();
        for (CorrectionPlan item : list) {
            System.out.println(item);
            CorrectionPeople crp =
                    crpController.getCrp(
                            item.getDxbh()).getData();
            item.setXm(crp.getXm());
            item.setJzlb(crp.getJzlb());
        }
        return ResponseResult.success(list);
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
    public ResponseResult<CorrectionPlan> getPlanByDxbh(@PathVariable("dxbh") String dxbh) {
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
            service.save(plan);
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseResult<Boolean> update(@RequestBody CorrectionPlan plan) {
        try {
            service.update().eq("dxbh", plan.getDxbh())
                   .update(plan);
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败！");
        }
    }
}
