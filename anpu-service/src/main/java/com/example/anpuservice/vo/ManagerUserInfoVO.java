package com.example.anpuservice.vo;


import com.example.anpuservice.model.ManagerUserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * FileName: com.iyingke.riskbackstage.vo.ManagerUserInfoVO.java
 * Author: Administrator
 * Description:
 */

public class ManagerUserInfoVO extends ManagerUserInfo {
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;
    @ApiModelProperty(value = "登录token")
    private String loginToken;
}
