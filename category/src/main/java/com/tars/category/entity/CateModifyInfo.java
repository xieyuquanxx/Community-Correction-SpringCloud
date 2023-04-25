package com.tars.category.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("cate_modify_info_tbl")
public class CateModifyInfo {
    @TableId
    private String dxbh;
    private String gllb; // 变更后的类型
    private String tzyy; // 调整原因
    private String bdrq = "2000/1/1"; //变动日期

    private String sfsshr; // 受委托的司法所审核人
    private String sfsshsj = "2000/1/1"; //司法所审核时间
    private String sfsshyj; // 司法所审核意见

    private String xjsqjzjgspr; // 县级社区矫正机构审批人
    private String xjsqjzjgspsj = "2000/1/1"; // 县级社区矫正机构审批时间
    private String xjsqjzjgspyj; // 县级社区矫正机构审批意见

    private String processId; // 审批流程id
    private Integer step; // 当前步骤
}
