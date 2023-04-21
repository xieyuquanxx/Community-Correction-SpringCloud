package com.tars.daily.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.daily.entity.report.ReportDetail;
import com.tars.daily.mapper.report.ReportDetailMapper;
import com.tars.daily.service.report.ReportDetailService;
import org.springframework.stereotype.Service;

@Service
public class ReportDetailServiceImpl extends ServiceImpl<ReportDetailMapper, ReportDetail>
        implements ReportDetailService {
}
