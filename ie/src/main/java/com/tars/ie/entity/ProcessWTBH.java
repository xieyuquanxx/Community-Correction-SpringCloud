package com.tars.ie.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@TableName("process_wtbh_tbl")
public class ProcessWTBH {
    private String processId;
    private String wtbh;

    public ProcessWTBH(String processId, String wtbh) {
        this.processId = processId;
        this.wtbh = wtbh;
    }
}
