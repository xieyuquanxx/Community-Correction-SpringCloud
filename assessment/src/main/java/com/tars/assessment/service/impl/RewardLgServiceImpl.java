package com.tars.assessment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.assessment.entity.RewardLgInfo;
import com.tars.assessment.mapper.RewardLgMapper;
import com.tars.assessment.service.RewardLgService;
import org.springframework.stereotype.Service;

@Service
public class RewardLgServiceImpl extends ServiceImpl<RewardLgMapper
        , RewardLgInfo>
        implements RewardLgService {
}
