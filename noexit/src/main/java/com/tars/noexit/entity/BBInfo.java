package com.tars.noexit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 出入境报备信息表
 * todo 审核流程
 */
@Data
@TableName("exit_bb_tbl")
public class BBInfo {
    private String dxbh;
    @TableField(exist = false)
    private String xm;
    private String crjzjzl;
    private String crjzjhm;
    private String bbsldw;
    private String bbdw;
    private String bbrq = "2000/1/1";
    private String bbksrq = "2000/1/1";
    private String bbjsrq = "2000/1/1";
    private Integer step = 0;
    private String processId;
}
