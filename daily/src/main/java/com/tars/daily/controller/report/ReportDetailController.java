package com.tars.daily.controller.report;

import com.tars.daily.api.ResponseResult;
import com.tars.daily.controller.OssController;
import com.tars.daily.entity.report.ReportDetail;
import com.tars.daily.service.report.ReportDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/daily/report/detail")
@CrossOrigin(origins = "*")
public class ReportDetailController {
    @Autowired
    private ReportDetailService service;

    @Autowired
    private OssController ossController;

    @GetMapping("/{dxbh}")
    public ResponseResult<List<ReportDetail>> getReportDetailByDxbh(@PathVariable("dxbh") String dxbh) {
        try {
            List<ReportDetail> list = service.query().eq("dxbh", dxbh)
                                             .list();
            return ResponseResult.success(list);
        } catch (Exception e) {
            return ResponseResult.fail(null, e.getMessage());
        }
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


    @PostMapping("/upload")
    public ResponseResult<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            ResponseResult<String> result = ossController.uploadFile(
                    file, null);
            String url = result.getData();
            return ResponseResult.success(url);
        } catch (Exception e) {
            return ResponseResult.fail("", e.getMessage());
        }
    }
}
