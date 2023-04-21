package com.tars.ic.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("crp_cate_tbl")
public class CrpCategory {
    private String dxbh; // 对象编号
    @TableField(exist = false)
    private String xm; // 姓名
    private String gllb; // 管理类别

    public CrpCategory() {
    }

    public CrpCategory(String dxbh, String xm, String gllb) {
        this.dxbh = dxbh;
        this.xm = xm;
        this.gllb = gllb;
    }
}
