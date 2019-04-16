package com.example.anpuservice.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * FileName: com.iyingke.riskbackstage.vo.TreeVO.java
 * Author: Administrator
 * Description:
 */
@Data
public class TreeVO {
    @ApiModelProperty(value = "key")
    private String key;
    @ApiModelProperty(value = "value")
    private String value;

    public TreeVO() {
    }

    public TreeVO(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
