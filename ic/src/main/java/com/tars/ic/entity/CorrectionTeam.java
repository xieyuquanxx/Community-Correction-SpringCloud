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

  Integer id; // 小组id
  String teamName; // 小组名
  String monitor; // 小组队长
  Integer teamNumber; // 小组成员个数
  List<String> workers; // 小组成员

  public CorrectionTeam(CorrectionTeamTemp temp) {
    workers = new ArrayList<>();
    id = temp.id;
    teamName = temp.teamName;
    monitor = temp.monitor;
    teamNumber = temp.teamNumber;
  }
}
