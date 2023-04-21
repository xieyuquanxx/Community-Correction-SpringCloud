package com.tars.ic.entity.others;

import lombok.Data;

/**
 * 出入境情况
 */
@Data
public class Exit {
    private String dxbh = ""; // 对象编号
    private String xm = ""; // 矫正对象姓名
    private String bb = ""; // 报备
    private String zj = ""; //证件状态 代管/归还/收缴/吊销/作废
    private String bk = ""; // 边控
}
