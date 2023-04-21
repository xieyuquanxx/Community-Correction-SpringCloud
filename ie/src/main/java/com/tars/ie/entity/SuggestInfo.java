package com.tars.ie.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ie_suggest_tbl")
public class SuggestInfo {
    private String wtbh;
    private String yjs;
    private String dcyjshr;//调查评估意见审核人
}
