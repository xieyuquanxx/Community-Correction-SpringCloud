package com.tars.ie.oss.entity;

import lombok.Data;

@Data
public class OssCallbackResult {
    //@ApiModelProperty("文件名称")
    private String filename;
    // @ApiModelProperty("文件大小")
    private String size;
    //@ApiModelProperty("文件的mimeType")
    private String mimeType;
    //@ApiModelProperty("图片文件的宽")
    private String width;
    //@ApiModelProperty("图片文件的高")
    private String height;

    //省略了所有getter,setter方法
}