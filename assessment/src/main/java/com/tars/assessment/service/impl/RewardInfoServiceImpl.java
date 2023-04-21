package com.tars.assessment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.assessment.entity.RewardInfo;
import com.tars.assessment.entity.ScoreInfo;
import com.tars.assessment.mapper.RewardInfoMapper;
import com.tars.assessment.mapper.ScoreInfoMapper;
import com.tars.assessment.service.RewardInfoService;
import com.tars.assessment.service.ScoreInfoService;
import org.springframework.stereotype.Service;

@Service
public class RewardInfoServiceImpl extends ServiceImpl<RewardInfoMapper, RewardInfo>
        implements RewardInfoService {
}
