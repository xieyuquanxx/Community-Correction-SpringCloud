package com.tars.uncorrected.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.uncorrected.entity.UnCorrAnnouncement;
import com.tars.uncorrected.mapper.UnCorrAnnouncementInfoMapper;
import com.tars.uncorrected.service.UnCorrAnnouncementInfoService;
import org.springframework.stereotype.Service;

@Service
public class UnCorrAnnouncementServiceImpl
        extends ServiceImpl<UnCorrAnnouncementInfoMapper, UnCorrAnnouncement>
        implements UnCorrAnnouncementInfoService {
}