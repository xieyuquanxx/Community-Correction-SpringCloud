package com.tars.ic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("recvcrp_announce")
@Accessors(chain = true)
public class CorrectionAnnounce {

  @TableId(type = IdType.AUTO)
  private Long id;

  private String dxbh;
  @TableField(exist = false)
  private String xm;
  String gmt_create;
  String gmt_modified;

  private String xgrq;
  private String audio;
  private String finish;
}
