package com.tars.category.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tars.category.entity.CateModifyInfo;
import com.tars.category.entity.CategoryInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CateModifyMapper extends BaseMapper<CateModifyInfo> {
}
