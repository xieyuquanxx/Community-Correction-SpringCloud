package com.tars.ic.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.ic.entity.CorrectionPeople;
import com.tars.ic.mapper.CrpMapper;
import com.tars.ic.service.CrpService;
import org.springframework.stereotype.Service;

@Service
public class CrpServiceImpl
        extends ServiceImpl<CrpMapper, CorrectionPeople>
        implements CrpService {
}
