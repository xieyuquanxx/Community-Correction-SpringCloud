package com.tars.ic.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@TableName("crp_check_tbl")
@Builder
@AllArgsConstructor
public class CrpCheck {
    private String dxbh;
    @TableField(exist = false)
    private String xm;
    private String date;
    private String status;

    public CrpCheck() {
    }
}
