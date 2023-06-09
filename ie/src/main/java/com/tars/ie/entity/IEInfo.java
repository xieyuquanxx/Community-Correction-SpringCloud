package com.tars.ie.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("ie_tbl")
@Schema(name = "IEInfo", description = "调查评估信息类")
public class IEInfo {

  @TableId
  String wtbh; // 委托编号

  Integer step;
  String processId;

  Integer finish = 10; // 完成时间
  String wtdw; // 委托单位
  String wtdch; // 调查评估委托函

  String bdcpgrdlx; // 被调查评估对象的类型
  String bgrxm; // 被调查评估对象姓名
  String bgrsfzh; // 被调查评估对象身份证号
  String bgrxb = "01"; // 被调查评估对象性别
  String bgrcsrq = "1979/1/1"; // 被调查评估对象出生日期
  String bgrjzddz; // 被调查评估对象居住地地址
  String bgrgzdw; // 被调查评估对象工作单位

  String zm; // 罪名
  String ypxq; // 原判刑期
  String ypxqksrq = "1979/1/1"; // 原判刑期开始日期
  String ypxqjsrq = "1979/1/1"; // 原判刑期结束日期
  String ypxf; // 原判刑罚

  String fjx; // 附加刑
  String pjjg; // 判决机关
  String pjrq = "1979/1/1"; // 判决日期

  String nsyjzlb; // 拟适用矫正类别
  String dcdwxqj; // 接受委托的县级社区矫正机构

  @TableLogic
  int isDelete; // 逻辑删除
}
