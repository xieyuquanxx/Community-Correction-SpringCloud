package com.tars.ic.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CorrectionTeam {
    Long id; // 小组id
    String name; // 小组名
    String monitor; // 小组队长
    Integer number; // 小组成员个数
    List<String> workers; // 小组成员

    public CorrectionTeam(CorrectionTeamTemp temp) {
        workers = new ArrayList<>();
        id = temp.id;
        name = temp.name;
        monitor = temp.monitor;
        number = temp.number;
    }
}
