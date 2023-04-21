package com.tars.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.business.entity.Ban.BanInfo;
import com.tars.business.mapper.ban.BanInfoMapper;
import com.tars.business.service.ban.BanInfoService;
import org.springframework.stereotype.Service;

@Service
public class BanInfoServiceImpl extends ServiceImpl<BanInfoMapper,
        BanInfo>
        implements BanInfoService {
}
