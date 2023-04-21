package com.tars.assessment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("assess_reward_info_tbl")
public class RewardInfo {
    @TableId(type = IdType.AUTO)
    private Integer id; // 奖励id

    private String dxbh;
    @TableField(exist = false)
    private String xm;

    private String jllb; // 奖励类型
    private String jlyy; // 奖励原因
    private String date; // 奖励时间
    private String jlr; // 记录人

    private Integer rewardId; // 所属奖励的id
}
