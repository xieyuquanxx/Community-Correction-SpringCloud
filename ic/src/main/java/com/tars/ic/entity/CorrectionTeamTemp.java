package com.tars.ic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("crt_tbl")
public class CorrectionTeamTemp {
    String id; // 小组id
    String teamName; // 小组名
    String monitor; // 小组队长
    Integer teamNumber; // 小组成员个数

    public CorrectionTeamTemp(String id, String teamName, String monitor, Integer teamNumber) {
        this.id = id;
        this.teamName = teamName;
        this.monitor = monitor;
        this.teamNumber = teamNumber;
    }
}
