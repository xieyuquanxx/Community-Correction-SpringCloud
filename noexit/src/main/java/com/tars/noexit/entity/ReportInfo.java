package com.tars.noexit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 出入境报备信息表
 */
@Data
@TableName("noexit_report")
public class ReportInfo {

  @TableId(type = IdType.AUTO)
  private Long id;// 方案id
  String gmt_create;
  String gmt_modified;

  private String dxbh;

  @TableField(exist = false)
  private String xm;

  private String crjzjzl;
  private String crjzjhm;
  private String bbsldw;
  private String bbdw;
  private String bbrq;
  private String bbksrq;
  private String bbjsrq;
  private Integer step = 0;
  private String processId;
}
