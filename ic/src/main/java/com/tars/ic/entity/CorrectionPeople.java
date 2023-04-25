package com.tars.ic.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@TableName("crp_tbl")
@Builder
public class CorrectionPeople {
    @TableId
    String dxbh;
    String sfdcpg;
    String jzlb;
    String xm;
    String xb;
    String mz;
    String gj;
    String hjlx;
    String sfzhm;
    String csrq;
    String whcd;
    String hyzk;
    String jyjxqk;
    String xzzmm;
    String xgzdw;
    String dwlxdh;
    String grlxdh;
    String ywjtcyjzyshgx;
    String zp;
    String team;
    String status;
}
