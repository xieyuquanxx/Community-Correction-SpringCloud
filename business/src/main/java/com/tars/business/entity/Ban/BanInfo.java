package com.tars.business.entity.Ban;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("bus_ban_info_tbl")
public class BanInfo {
    private String dxbh;
    @TableField(exist = false)
    private String xm;

    private String sqjrcs;
    private String sqrq;
    private String sqjrsj;
    private String sqjssj;
    private String sqly;

    private String sfsshr;
    private String sfsshsj;
    private String sfsshyj;

    private String xjsqjzjgspr;
    private String xjsqjzjgspsj;
    private String xjsqjzjgspyj;
    private String spjg;

    private Integer step;
    private String processId;
}
