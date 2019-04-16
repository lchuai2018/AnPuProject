package com.example.anpuservice.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lichenghuai
 * @version 1.0
 * @description
 * @date 2019/3/12 11:12
 */

@Data
public class CommonChangStatusVO {

    @ApiModelProperty(value = "主键id")
    private Integer  id;
    @ApiModelProperty("状态：1可用；2停用")
    private Integer status;;



}
