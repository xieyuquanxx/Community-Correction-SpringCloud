package com.tars.noexit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 证件代管信息表
 * todo 审核流程
 */
@Data
@TableName("exit_zj_tbl")
public class ZJInfo {
    @TableId
    private String dxbh;
    @TableField(exist = false)
    private String xm;
    private String zj;

    private Integer step = 0;
    private String processId;
}
