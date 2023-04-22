package com.tars.uncorrected.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.uncorrected.entity.UnCorrInfo;
import com.tars.uncorrected.mapper.UnCorrInfoMapper;
import com.tars.uncorrected.service.UnCorrInfoService;
import org.springframework.stereotype.Service;

@Service
public class UnCorrInfoServiceImpl
        extends ServiceImpl<UnCorrInfoMapper, UnCorrInfo>
        implements UnCorrInfoService {
}