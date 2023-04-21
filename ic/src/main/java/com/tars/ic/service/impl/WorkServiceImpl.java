package com.tars.ic.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.ic.entity.Worker;
import com.tars.ic.mapper.WorkerMapper;
import com.tars.ic.service.WorkerService;
import org.springframework.stereotype.Service;

@Service
public class WorkServiceImpl
        extends ServiceImpl<WorkerMapper, Worker>
        implements WorkerService {
}
