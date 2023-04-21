package com.tars.business.mapper.visitor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tars.business.entity.Visitor.VisitorInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VisitorInfoMapper extends BaseMapper<VisitorInfo> {
}
