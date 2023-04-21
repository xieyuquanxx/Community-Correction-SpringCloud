package com.tars.termination.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 终止矫正报备信息表
 */
@Data
@TableName("termination_info_tbl")
public class TerminationInfo {
    @TableId
    private String dxbh;
    @TableField(exist = false)
    private String xm;

    private Integer step;
    private String processId;
    private String spjg; // 审批结果

    private String zzjzlx; // 终止矫正类型
    private String zzjzrq; // 终止矫正日期

    private String sfsj; // 是否收监
    private String sjzxlx; // 收监执行类型
    private String sjzxrq; // 收监执行日期

    private String cxhxsjzxyy; // 撤销缓刑收监执行原因
    private String cxjssjzxyy; // 撤销假释收监执行原因
    private String sjzxyy; // 收监执行原因

    private String swsj; //死亡日期
    private String swlx; // 死亡类型
    private String jtsy; // 具体死因

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
