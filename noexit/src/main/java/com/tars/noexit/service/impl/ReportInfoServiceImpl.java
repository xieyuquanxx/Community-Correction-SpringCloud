package com.tars.noexit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.noexit.entity.ReportInfo;
import com.tars.noexit.mapper.ReportInfoMapper;
import com.tars.noexit.service.bb.ReportInfoService;
import org.springframework.stereotype.Service;

@Service
public class ReportInfoServiceImpl
    extends ServiceImpl<ReportInfoMapper, ReportInfo>
    implements ReportInfoService {

}