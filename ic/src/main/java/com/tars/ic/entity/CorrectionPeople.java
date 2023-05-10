package com.tars.ic.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
@TableName("recvcrp_people")
@Builder
@Accessors(chain = true)
public class CorrectionPeople {

  @TableId(type = IdType.AUTO)
  Long id;
  String gmt_create;
  String gmt_modified;

  String dxbh;


}
