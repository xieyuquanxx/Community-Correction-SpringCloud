package com.tars.daily.mapper.check;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tars.daily.entity.check.CheckInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CheckInfoMapper extends BaseMapper<CheckInfo> {
    @Select("select t2.dxbh, xm, t2.gllb, check_count " +
            "from crp_tbl t1, cate_info_tbl t2, code_gllb_tbl t3 " +
            "where t1.dxbh = t2.dxbh and t2.gllb = t3.gllb")
    List<CheckInfo> getAllCheckInfo();

    @Select("select count(*) from daily_check_detail_tbl where dxbh" +
            " = ${dxbh};")
    Integer getCheckCount(String dxbh);
}
