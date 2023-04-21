package com.tars.daily.entity.report;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("daily_report_detail_tbl")
public class ReportDetail {
    private String dxbh;
    private String bg; // 报告
    private String date;
}
