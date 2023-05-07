package com.tars.termination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.termination.entity.TermInfo;
import com.tars.termination.mapper.TermInfoMapper;
import com.tars.termination.service.TermInfoService;
import org.springframework.stereotype.Service;

@Service
public class TermInfoServiceImpl
        extends ServiceImpl<TermInfoMapper, TermInfo>
        implements TermInfoService {
}