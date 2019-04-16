package com.example.anpuservice.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * FileName: com.iyingke.riskbackstage.vo.ManagerUserLoginVO.java
 * Author: Administrator
 * Description:
 */

public class ManagerUserLoginVO {
    @ApiModelProperty(value = "账号")
    private String accountNum;
    @ApiModelProperty(value = "密码")
    private String password;

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
