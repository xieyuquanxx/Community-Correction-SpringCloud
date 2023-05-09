package com.tars.ic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@TableName("recvcrp_team")
@Builder
public class CorrectionTeamTemp {
    @TableId(type = IdType.AUTO)
    Long id; // 小组id

    String name; // 小组名
    String monitor; // 小组队长
    Integer number; // 小组成员个数

    String gmt_create;
    String gmt_modified;
}
