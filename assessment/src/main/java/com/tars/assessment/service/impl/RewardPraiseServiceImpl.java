package com.tars.assessment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.assessment.entity.RewardInfo;
import com.tars.assessment.entity.RewardPraiseInfo;
import com.tars.assessment.mapper.RewardInfoMapper;
import com.tars.assessment.mapper.RewardPraiseMapper;
import com.tars.assessment.service.RewardInfoService;
import com.tars.assessment.service.RewardPraiseService;
import org.springframework.stereotype.Service;

@Service
public class RewardPraiseServiceImpl extends ServiceImpl<RewardPraiseMapper, RewardPraiseInfo>
        implements RewardPraiseService {
}
