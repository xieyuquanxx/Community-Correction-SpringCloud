package com.tars.noexit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.noexit.entity.Exit;
import com.tars.noexit.mapper.ExitMapper;
import com.tars.noexit.service.ExitService;
import org.springframework.stereotype.Service;

@Service
public class ExitServiceImpl
        extends ServiceImpl<ExitMapper, Exit>
        implements ExitService {
}