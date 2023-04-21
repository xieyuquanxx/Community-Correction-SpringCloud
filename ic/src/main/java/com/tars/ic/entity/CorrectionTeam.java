package com.tars.ic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CorrectionTeam {
    String id; // 小组id
    String teamName; // 小组名
    String monitor; // 小组队长
    Integer teamNumber; // 小组成员个数
    List<String> workers; // 小组成员

    public CorrectionTeam() {
        workers = new ArrayList<>();
    }

    public CorrectionTeam(CorrectionTeamTemp temp) {
        workers = new ArrayList<>();
        id = temp.id;
        teamName = temp.teamName;
        monitor = temp.monitor;
        teamNumber = temp.teamNumber;
    }

    public CorrectionTeam(String id, String teamName, String monitor, Integer teamNumber, List<String> workers) {
        this.id = id;
        this.teamName = teamName;
        this.monitor = monitor;
        this.teamNumber = teamNumber;
        this.workers = workers;
    }
}
