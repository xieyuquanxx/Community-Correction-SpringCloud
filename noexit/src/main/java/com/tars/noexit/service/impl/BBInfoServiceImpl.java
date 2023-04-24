package com.tars.noexit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.noexit.entity.BBInfo;
import com.tars.noexit.mapper.BBInfoMapper;
import com.tars.noexit.service.bb.BBInfoService;
import org.springframework.stereotype.Service;

@Service
public class BBInfoServiceImpl
        extends ServiceImpl<BBInfoMapper, BBInfo>
        implements BBInfoService {
}