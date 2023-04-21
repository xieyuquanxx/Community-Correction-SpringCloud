package com.tars.daily.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.daily.entity.check.CheckInfo;
import com.tars.daily.mapper.check.CheckInfoMapper;
import com.tars.daily.service.check.CheckInfoService;
import org.springframework.stereotype.Service;

@Service
public class CheckInfoServiceImpl extends ServiceImpl<CheckInfoMapper, CheckInfo>
        implements CheckInfoService {
}
