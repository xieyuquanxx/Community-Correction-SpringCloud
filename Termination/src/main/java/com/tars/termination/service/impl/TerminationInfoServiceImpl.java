package com.tars.termination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.termination.entity.TerminationInfo;
import com.tars.termination.mapper.TerminationInfoMapper;
import com.tars.termination.service.TerminationInfoService;
import org.springframework.stereotype.Service;

@Service
public class TerminationInfoServiceImpl
        extends ServiceImpl<TerminationInfoMapper, TerminationInfo>
        implements TerminationInfoService {
}