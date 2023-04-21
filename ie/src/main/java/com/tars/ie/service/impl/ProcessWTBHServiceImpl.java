package com.tars.ie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tars.ie.entity.ProcessWTBH;

import com.tars.ie.mapper.ProcessWTBHMapper;

import com.tars.ie.service.ProcessWTBHService;
import org.springframework.stereotype.Service;

@Service
public class ProcessWTBHServiceImpl
        extends ServiceImpl<ProcessWTBHMapper, ProcessWTBH>
        implements ProcessWTBHService {
}
