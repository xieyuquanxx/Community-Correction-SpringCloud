package com.tars.ic.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("crp_announcement_tbl")
public class CorrectionAnnouncement {
    private String dxbh;
    @TableField(exist = false)
    private String xm;
    private String xgrq;
    private String audio;
    private Boolean finish;
}
