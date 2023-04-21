package com.tars.category.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("cate_info_tbl")
public class CategoryInfo {
    private String dxbh; // 对象编号
    @TableField(exist = false)
    private String xm; // 姓名
    private String gllb; // 管理类别
}
