package com.tars.ic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("recvcrp_plan")
public class CorrectionPlan {

  @TableId(type = IdType.AUTO)
  private Long id;// 方案id
  String gmt_create;
  String gmt_modified;

  private Long team; // 矫正小组的id
  private String dxbh; // 对象编号

  @TableField(exist = false)
  private String xm;

  private String famc; // 方案名称

  @TableField(exist = false)
  private String jzlb;// 矫正类别

  private String plan; // 方案docx的url
}
