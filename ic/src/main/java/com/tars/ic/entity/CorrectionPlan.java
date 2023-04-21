package com.tars.ic.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("crplan_tbl")
public class CorrectionPlan {
    private Integer id;// 方案id
    private String dxbh; // 对象编号
    @TableField(exist = false)
    private String xm;
    private String famc; // 方案名称
    @TableField(exist = false)
    private String jzlb;// 矫正类别
    private String sfcn; // 是否成年
    private String jdglcs; //监督管理措施
    private String jyjzcs; // 教育矫正措施
    private String bkfzcs; // 帮困扶助措施
    private String qtcs; // 其他措施
    private String ssjgid; // 所属机构id
    private String ssxgpg; // 实施效果评估
    private String pgsj; //评估时间
    private String pgr; // 评估人
}
