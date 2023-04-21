package com.tars.ic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tars.ic.entity.CorrectionTeam;
import com.tars.ic.entity.CorrectionTeamTemp;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CrtMapper extends BaseMapper<CorrectionTeamTemp> {
}
