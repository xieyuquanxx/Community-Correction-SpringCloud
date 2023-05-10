package com.tars.ic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.ic.entity.CorrectionInfo;
import com.tars.ic.mapper.CrpInfoMapper;
import com.tars.ic.service.CrpInfoService;
import org.springframework.stereotype.Service;

@Service
public class CrpInfoServiceImpl
    extends ServiceImpl<CrpInfoMapper, CorrectionInfo>
    implements CrpInfoService {

}
