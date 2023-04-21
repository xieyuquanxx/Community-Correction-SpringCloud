package com.tars.daily.mapper.report;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tars.daily.entity.check.CheckInfo;
import com.tars.daily.entity.report.ReportInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReportInfoMapper extends BaseMapper<ReportInfo> {
    @Select("select t2.dxbh, xm, t2.gllb, report_count " +
            "from crp_tbl t1, crp_cate_tbl t2, gllb_code_tbl t3 " +
            "where t1.sqjzdxbh = t2.dxbh and t2.gllb = t3.gllb")
    List<ReportInfo> getAllReportInfo();
}
