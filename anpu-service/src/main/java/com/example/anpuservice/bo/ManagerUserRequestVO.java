package com.example.anpuservice.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


public class ManagerUserRequestVO {
    @ApiModelProperty(value = "当前页")
    private Integer currentPage;
    @ApiModelProperty(value = "每页显示页数")
    private Integer pageSize;
    // add
    @ApiModelProperty(value = "账号")
    private String accountNumber;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<Integer> getManagerIds() {
        return managerIds;
    }

    public void setManagerIds(List<Integer> managerIds) {
        this.managerIds = managerIds;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @ApiModelProperty(value = "用户名")
    private String name;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "用户描述")
    private String remark;
    @ApiModelProperty(value = "角色ID")
    private String roleId;

    //update
    @ApiModelProperty(value = "管理用户的Id集（批量修改时传入 如：1,2,3,4）")
    private List<Integer> managerIds;
    @ApiModelProperty(value = "密码（批量修改时传入）")
    private String password;
    @ApiModelProperty(value = "禁用状态")
    private Integer status;
    @ApiModelProperty(value = "批量修改状态 或者 批量修改密码 0：修改状态 1：修改密码")
    private Integer type;


}
