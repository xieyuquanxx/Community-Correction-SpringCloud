package com.tars.ic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@TableName("crt_tbl")
@Builder
public class CorrectionTeamTemp {
    @TableId(type = IdType.AUTO)
    Integer id; // 小组id
    
    String teamName; // 小组名
    String monitor; // 小组队长
    Integer teamNumber; // 小组成员个数

}
