package com.tars.ic.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("recvcrp_worker")
public class Worker {
    // 行政人员信息
    @TableId
    String id; //人员编号
    String gmt_create;
    String gmt_modified;
    
    String dxbh;
    String xm; // 姓名
    String xb;
    String sj;
    String team; // 所属的小队

}
