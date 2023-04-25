package com.tars.ic.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@TableName("crp_cate_tbl")
@Builder
@AllArgsConstructor
public class CrpCategory {
    @TableId
    private String dxbh; // 对象编号
    @TableField(exist = false)
    private String xm; // 姓名
    private String gllb; // 管理类别

    public CrpCategory() {
    }
}
