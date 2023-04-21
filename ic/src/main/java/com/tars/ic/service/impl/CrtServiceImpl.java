package com.tars.ic.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.ic.entity.CorrectionTeam;
import com.tars.ic.entity.CorrectionTeamTemp;
import com.tars.ic.mapper.CrtMapper;
import com.tars.ic.service.CrtService;
import org.springframework.stereotype.Service;

@Service
public class CrtServiceImpl
        extends ServiceImpl<CrtMapper, CorrectionTeamTemp>
        implements CrtService {
}
