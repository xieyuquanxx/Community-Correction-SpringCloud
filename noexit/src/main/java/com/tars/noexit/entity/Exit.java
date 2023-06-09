package com.tars.noexit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 出入境情况
 */
@Data
@TableName("exit_info_tbl")
public class Exit {
    @TableId
    private String dxbh = ""; // 对象编号
    @TableField(exist = false)
    private String xm; // 矫正对象姓名

    private String bb = "0"; // 报备
    private String zj = "06"; //证件状态 代管/归还/收缴/吊销/作废
    private String bk = "0"; // 边控
}
