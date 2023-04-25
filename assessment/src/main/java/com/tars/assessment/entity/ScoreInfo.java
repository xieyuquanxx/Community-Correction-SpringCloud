package com.tars.assessment.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("assess_score_tbl")
public class ScoreInfo {
    @TableId
    private String dxbh;
    @TableField(exist = false)
    private String xm;
    private Integer score;
}
