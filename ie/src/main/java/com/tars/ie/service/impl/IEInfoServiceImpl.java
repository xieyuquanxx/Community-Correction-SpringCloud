package com.tars.ie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.ie.entity.IEInfo;
import com.tars.ie.mapper.IEInfoMapper;
import com.tars.ie.service.IEInfoService;
import org.springframework.stereotype.Service;

@Service
public class IEInfoServiceImpl
        extends ServiceImpl<IEInfoMapper, IEInfo>
        implements IEInfoService {
}
