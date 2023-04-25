package com.tars.business.entity.Visitor;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("bus_visitor_info_tbl")
public class VisitorInfo {
    @TableId
    private String dxbh;
    @TableField(exist = false)
    private String xm;

    private String hjrxm;
    private String hksj;
    private String hkdd;
    private String hkyy;
    private String sqrq;

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
