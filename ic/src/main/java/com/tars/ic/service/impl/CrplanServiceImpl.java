package com.tars.ic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.ic.entity.CorrectionPlan;
import com.tars.ic.mapper.CrplanMapper;
import com.tars.ic.service.CrplanService;
import org.springframework.stereotype.Service;

@Service
public class CrplanServiceImpl
        extends ServiceImpl<CrplanMapper, CorrectionPlan>
        implements CrplanService {
}
