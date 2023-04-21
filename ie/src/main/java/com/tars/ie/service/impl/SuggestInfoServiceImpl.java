package com.tars.ie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.ie.entity.IEInfo;
import com.tars.ie.entity.SuggestInfo;
import com.tars.ie.mapper.IEInfoMapper;
import com.tars.ie.mapper.SuggestInfoMapper;
import com.tars.ie.service.IEInfoService;
import com.tars.ie.service.SuggestInfoService;
import org.springframework.stereotype.Service;

@Service
public class SuggestInfoServiceImpl
        extends ServiceImpl<SuggestInfoMapper, SuggestInfo>
        implements SuggestInfoService {
}
