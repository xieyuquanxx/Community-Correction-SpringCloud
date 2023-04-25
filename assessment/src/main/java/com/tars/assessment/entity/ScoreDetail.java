package com.tars.assessment.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("assess_score_detail_tbl")
public class ScoreDetail {
    @TableId
    private String dxbh;
    private String reason;
    private Integer score;
    private String date;
}
