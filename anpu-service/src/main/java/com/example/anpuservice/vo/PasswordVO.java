package com.example.anpuservice.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * FileName: com.iyingke.riskbackstage.vo.PasswordVO.java
 * Author: Administrator
 * Description:
 */

public class PasswordVO {
    @ApiModelProperty(value = "原密码,如重置密码时，不传")
    private String password;
    @ApiModelProperty(value = "新密码")
    private String newPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @ApiModelProperty(value = "确认密码")
    private String confirmPassword;
}
