package com.tars.noexit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.noexit.entity.ZJInfo;
import com.tars.noexit.mapper.ZJInfoMapper;
import com.tars.noexit.service.zj.ZJInfoService;
import org.springframework.stereotype.Service;

@Service
public class ZJInfoServiceImpl
        extends ServiceImpl<ZJInfoMapper, ZJInfo>
        implements ZJInfoService {
}