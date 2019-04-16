package com.example.anpuservice.vo;

import com.example.anpuservice.model.ManagerRoleInfo;
import lombok.Data;

import javax.persistence.Transient;
import java.util.List;

/**
 * FileName: com.iyingke.riskbackstage.vo.ManagerRoleInfoVO.java
 * Author: Administrator
 * Description:
 */

public class ManagerRoleInfoVO extends ManagerRoleInfo {
    public List<Integer> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<Integer> menuIdList) {
        this.menuIdList = menuIdList;
    }

    @Transient
    private List<Integer> menuIdList;
}
