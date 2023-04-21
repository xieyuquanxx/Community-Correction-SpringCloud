package com.tars.daily.controller.report;

import com.tars.daily.api.ResponseResult;
import com.tars.daily.entity.report.ReportInfo;
import com.tars.daily.mapper.report.ReportInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/daily/report")
@CrossOrigin(origins = "*")
public class ReportInfoController {
    @Autowired
    private ReportInfoMapper mapper;

    @GetMapping("/all")
    public ResponseResult<List<ReportInfo>> getAll() {
        return ResponseResult.success(mapper.getAllReportInfo());
    }
}
