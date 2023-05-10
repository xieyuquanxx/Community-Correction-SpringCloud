package com.tars.noexit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 证件代管信息表
 */
@Data
@TableName("noexit_zj")
public class ZJInfo {

  @TableId(type = IdType.AUTO)
  private Long id;// 方案id
  String gmt_create;
  String gmt_modified;

  private String dxbh;
  @TableField(exist = false)
  private String xm;
  private String zj;

  private Integer step = 0;
  private String processId;
}
