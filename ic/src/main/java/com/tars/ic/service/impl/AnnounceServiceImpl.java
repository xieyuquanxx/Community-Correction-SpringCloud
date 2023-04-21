package com.tars.ic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.ic.entity.CorrectionAnnouncement;
import com.tars.ic.mapper.AnnounceMapper;
import com.tars.ic.service.AnnounceService;
import org.springframework.stereotype.Service;

@Service
public class AnnounceServiceImpl
        extends ServiceImpl<AnnounceMapper, CorrectionAnnouncement>
        implements AnnounceService {
}
