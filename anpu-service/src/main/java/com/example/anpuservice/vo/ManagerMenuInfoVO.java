package com.example.anpuservice.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * FileName: com.iyingke.riskbackstage.vo.ManagerMenuInfoVO.java
 * Author: Administrator
 * Description:
 */

public class ManagerMenuInfoVO {
    private Integer id;
    @ApiModelProperty(value = "菜单父ID")
    private Integer parentId;
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPowerUrl() {
        return powerUrl;
    }

    public void setPowerUrl(String powerUrl) {
        this.powerUrl = powerUrl;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public List<PowerInsertInfoVO> getPowerList() {
        return powerList;
    }

    public void setPowerList(List<PowerInsertInfoVO> powerList) {
        this.powerList = powerList;
    }

    @ApiModelProperty(value = "菜单icon")
    private String icon;
    @ApiModelProperty(value = "菜单路径")
    private String powerUrl;
    @ApiModelProperty(value = "菜单类型：0目录，1菜单")
    private Integer menuType;
    @ApiModelProperty(value = "菜单状态：0不可用，1可用")
    private Integer status;
    @ApiModelProperty(value = "菜单排序")
    private Integer orderNum;
    @ApiModelProperty(value = "菜单拥有权限的列表")
    private List<PowerInsertInfoVO> powerList;
}
