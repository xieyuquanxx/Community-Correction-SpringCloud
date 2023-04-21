package com.tars.daily.entity.check;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("daily_check_detail_tbl")
public class CheckDetail {
    private String dxbh;
    private String date;
}
