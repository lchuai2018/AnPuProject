package com.example.anpuservice.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


public class RoleVO {
    public List<Integer> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<Integer> menuIdList) {
        this.menuIdList = menuIdList;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @ApiModelProperty(value = "权限ID集")
   private List<Integer> menuIdList;
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;
}
