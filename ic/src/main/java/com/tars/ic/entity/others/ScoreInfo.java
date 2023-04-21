package com.tars.ic.entity.others;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
public class ScoreInfo {
    private String dxbh;
    private String xm;
    private Integer score;

    public ScoreInfo(String dxbh, String xm, Integer score) {
        this.dxbh = dxbh;
        this.xm = xm;
        this.score = score;
    }
}
