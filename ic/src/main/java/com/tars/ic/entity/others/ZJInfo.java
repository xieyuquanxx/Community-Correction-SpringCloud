package com.tars.ic.entity.others;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 证件代管信息表
 */
@Data
@TableName("exit_zj_tbl")
public class ZJInfo {
    private String dxbh;
    @TableField(exist = false)
    private String xm;
    private String zj;

    private Integer step = 0;
    private String processId;
}
