package com.tars.daily.entity.check;

import lombok.Data;

@Data
public class CheckInfo {
    private String dxbh;
    private String xm;
    private String gllb;
    private Integer check_count;
    private Integer count; // 当前打卡了几次
}
