package com.tars.uncorrected.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 解除矫正报备信息表
 */
@Data
@TableName("uncorrected_info_tbl")
public class UnCorrInfo {
    @TableId
    private String dxbh;
    @TableField(exist = false)
    private String xm;

    private Integer step;
    private String processId;
    private String spjg; // 审批结果

    private String jcjzlx; // 解除矫正类型
    private String jcjzrq; // 解除矫正日期

    private String jzqjbx; // 矫正期间表现
    private String rztd; // 认罪态度
    private String sfcjzyjnpx; // 矫正期间是否参加职业技能培训
    private String sfhdzyjnzs; //矫正期间是否获得职业技能证书
    private String jstcjdj; // 技术特长及等级
    private String wxxpg; // 危险性评估
    private String jtgx; // 家庭关系
    private String tsqkbzjbjjy; // 矫正期间特殊情况备注及帮教建议
    private String bz; // 备注
}
