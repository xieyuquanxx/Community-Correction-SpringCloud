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
        try {
            List<ReportInfo> allCheckInfo = mapper.getAllReportInfo()
                                                  .stream()
                                                  .peek(e -> e.setCount(
                                                          mapper.getCheckCount(
                                                                  e.getDxbh())))
                                                  .toList();
            return ResponseResult.success(allCheckInfo);
        } catch (Exception e) {
            return ResponseResult.fail(null, e.getMessage());
        }
    }
}
