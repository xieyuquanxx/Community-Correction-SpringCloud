package com.tars.assessment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("assess_reward_praise_tbl")
public class RewardPraiseInfo {
    @TableId(type = IdType.AUTO)
    private Integer id; // 奖励id

    private String dxbh;
    @TableField(exist = false)
    private String xm;
    private Integer step; // 当前审批步骤

    private String xjsqjzjgspr; // 县级社区矫正机构审批人
    private String xjsqjzjgspsj; // 县级社区矫正机构审批时间
    private String xjsqjzjgspyj; // 县级社区矫正机构审批意见

    private String spjg; // 审批结果

}
