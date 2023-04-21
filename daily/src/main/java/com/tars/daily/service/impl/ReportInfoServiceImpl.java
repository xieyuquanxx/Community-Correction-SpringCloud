package com.tars.daily.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.daily.entity.report.ReportInfo;
import com.tars.daily.mapper.report.ReportInfoMapper;
import com.tars.daily.service.report.ReportInfoService;
import org.springframework.stereotype.Service;

@Service
public class ReportInfoServiceImpl extends ServiceImpl<ReportInfoMapper, ReportInfo>
        implements ReportInfoService {
}
