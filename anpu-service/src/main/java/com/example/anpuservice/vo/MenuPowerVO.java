package com.example.anpuservice.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * FileName: com.iyingke.riskbackstage.vo.MenuPowerVO.java
 * Author: Administrator
 * Description:
 */

public class MenuPowerVO {
    @ApiModelProperty(value = "菜单列表")
    private List<MenuInfoVO> menuList;
    @ApiModelProperty(value = "权限列表")
    private Set<String> permissions;

    public List<MenuInfoVO> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuInfoVO> menuList) {
        this.menuList = menuList;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
