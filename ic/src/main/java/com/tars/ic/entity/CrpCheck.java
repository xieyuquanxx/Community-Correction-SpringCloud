package com.tars.ic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("crp_check_tbl")
public class CrpCheck {

  @TableId(type = IdType.AUTO)
  private Long id;

  String gmt_create;
  String gmt_modified;

  private String dxbh;
  @TableField(exist = false)
  private String xm;
  private String date;
  private String status;


  public CrpCheck(String dxbh, String xm, String date, String status) {
    this.dxbh = dxbh;
    this.xm = xm;
    this.date = date;
    this.status = status;
  }
}
