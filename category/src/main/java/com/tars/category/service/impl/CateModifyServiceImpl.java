package com.tars.category.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tars.category.entity.CateModifyInfo;
import com.tars.category.entity.CategoryInfo;
import com.tars.category.mapper.CateMapper;
import com.tars.category.mapper.CateModifyMapper;
import com.tars.category.service.CateModiftyService;
import com.tars.category.service.CateService;
import org.springframework.stereotype.Service;

@Service
public class CateModifyServiceImpl
        extends ServiceImpl<CateModifyMapper, CateModifyInfo>
        implements CateModiftyService {
}