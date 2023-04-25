package com.tars.daily.controller.report;

import com.tars.daily.api.ResponseResult;
import com.tars.daily.entity.check.CheckDetail;
import com.tars.daily.entity.report.ReportDetail;
import com.tars.daily.service.report.ReportDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/daily/report/detail")
@CrossOrigin(origins = "*")
public class ReportDetailController {
    @Autowired
    private ReportDetailService service;

    @GetMapping("/{dxbh}")
    public ResponseResult<List<ReportDetail>> getReportDetailByDxbh(@PathVariable("dxbh") String dxbh) {
        List<ReportDetail> list = service.query().eq("dxbh", dxbh).list();
        return ResponseResult.success(list);
    }

    @PostMapping
    public ResponseResult<Boolean> saveReport(@RequestBody ReportDetail detail) {
        try {
            service.save(detail);
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, e.getMessage());
        }
    }
}
