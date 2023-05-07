package com.tars.termination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.termination.entity.TermAnnounce;
import com.tars.termination.mapper.TermAnnounceMapper;
import com.tars.termination.service.TermAnnounceService;
import org.springframework.stereotype.Service;

@Service
public class TermAnnounceServiceImpl
        extends ServiceImpl<TermAnnounceMapper, TermAnnounce>
        implements TermAnnounceService {
}