package com.example.anpuservice.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * FileName: com.iyingke.riskbackstage.vo.PowerInsertInfoVO.java
 * Author: Administrator
 * Description:
 */

public class PowerInsertInfoVO {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public String getPowerKey() {
        return powerKey;
    }

    public void setPowerKey(String powerKey) {
        this.powerKey = powerKey;
    }

    public Integer getPowerStatus() {
        return powerStatus;
    }

    public void setPowerStatus(Integer powerStatus) {
        this.powerStatus = powerStatus;
    }

    @ApiModelProperty(value = "菜单拥有权限名称")
    private String powerName;
    @ApiModelProperty(value = "菜单拥有权限key值")
    private String powerKey;
    @ApiModelProperty(value = "权限状态 0禁用 1可用")
    private Integer powerStatus;
}
