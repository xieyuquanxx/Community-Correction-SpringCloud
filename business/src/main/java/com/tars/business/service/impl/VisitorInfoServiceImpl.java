package com.tars.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.business.entity.Visitor.VisitorInfo;
import com.tars.business.mapper.visitor.VisitorInfoMapper;
import com.tars.business.service.visitor.VisitorInfoService;
import org.springframework.stereotype.Service;

@Service
public class VisitorInfoServiceImpl extends ServiceImpl<VisitorInfoMapper,
        VisitorInfo>
        implements VisitorInfoService {
}
