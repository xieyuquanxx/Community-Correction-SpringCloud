package com.tars.ic.controller;

import com.tars.ic.api.ResponseResult;
import com.tars.ic.entity.CorrectionAnnouncement;
import com.tars.ic.service.AnnounceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ic/announce")
@CrossOrigin(origins = "*")
public class CrpAnnouncementController {

    @Autowired
    private AnnounceService service;
    @Autowired
    private CrpController crpController;

    @GetMapping("/count")
    public ResponseResult<Long> getCount() {
        return ResponseResult.success(service.count());
    }

    @GetMapping("/all")
    public ResponseResult<List<CorrectionAnnouncement>> getAll() {
        List<CorrectionAnnouncement> list = service.list();
        for (CorrectionAnnouncement item : list) {
            item.setXm(crpController.getName(item.getDxbh()));
        }
        return ResponseResult.success(list);
    }

    @PostMapping("/save")
    public ResponseResult<Boolean> save(@RequestBody CorrectionAnnouncement crp) {
        try {
            crp.setFinish(false);
            service.save(crp);
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseResult<Boolean> update(@RequestBody CorrectionAnnouncement crp) {
        try {
            service.update().eq("dxbh", crp.getDxbh()).update(crp);
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败！");
        }
    }
}
