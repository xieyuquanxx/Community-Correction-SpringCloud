package com.tars.assessment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.assessment.entity.ScoreDetail;
import com.tars.assessment.mapper.ScoreDetailMapper;
import com.tars.assessment.service.ScoreDetailService;
import org.springframework.stereotype.Service;

@Service
public class ScoreDetailServiceImpl extends ServiceImpl<ScoreDetailMapper, ScoreDetail>
        implements ScoreDetailService {
}
