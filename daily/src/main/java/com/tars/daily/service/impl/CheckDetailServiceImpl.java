package com.tars.daily.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.daily.entity.check.CheckDetail;
import com.tars.daily.mapper.check.CheckDetailMapper;
import com.tars.daily.service.check.CheckDetailService;
import org.springframework.stereotype.Service;

@Service
public class CheckDetailServiceImpl extends ServiceImpl<CheckDetailMapper, CheckDetail>
        implements CheckDetailService {
}
