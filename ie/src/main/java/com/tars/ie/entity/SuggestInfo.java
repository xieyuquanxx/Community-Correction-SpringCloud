package com.tars.ie.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("ie_suggest_tbl")
@Schema(name = "SuggestInfo", description = "调查评估意见信息类")
public class SuggestInfo {

    @TableId
    @Schema(description = "委托编号")
    private String wtbh;

    @Schema(name = "yjs", description = "上传到OSS后的url地址")
    private String yjs;

    @Schema(description = "调查评估意见审核人")
    private String dcyjshr;
}
