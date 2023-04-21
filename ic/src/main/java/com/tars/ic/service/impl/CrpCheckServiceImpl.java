package com.tars.ic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.ic.entity.CrpCheck;
import com.tars.ic.mapper.CrpCheckMapper;
import com.tars.ic.service.CrpCheckService;
import org.springframework.stereotype.Service;

@Service
public class CrpCheckServiceImpl
        extends ServiceImpl<CrpCheckMapper, CrpCheck>
        implements CrpCheckService {
}
