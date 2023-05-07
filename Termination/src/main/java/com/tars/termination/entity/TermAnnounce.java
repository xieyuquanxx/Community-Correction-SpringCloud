package com.tars.termination.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("term_announcement_tbl")
public class TermAnnounce {
    @TableId
    private String dxbh;
    @TableField(exist = false)
    private String xm;

    private String xgrq;
    private String audio;
    private String finish;
}
