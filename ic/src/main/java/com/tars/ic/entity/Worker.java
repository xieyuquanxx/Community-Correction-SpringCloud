package com.tars.ic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("worker_tbl")
public class Worker {
    // 行政人员信息
    String rybm; //人员编号
    String xm; // 姓名
    String team; // 所属的小队

}
