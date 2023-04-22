package com.tars.uncorrected.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("uncorrected_announcement_tbl")
public class UnCorrAnnouncement {
    @TableId
    private String dxbh;
    @TableField(exist = false)
    private String xm;
    private String xgrq; // 宣告日期
    private String finish = "02"; // 是否结束
}
