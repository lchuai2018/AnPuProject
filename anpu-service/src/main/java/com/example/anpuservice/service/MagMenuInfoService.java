package com.example.anpuservice.service;


import com.example.anpuservice.vo.ManagerMenuInfoVO;

/**
 * FileName: com.iyingke.riskbackstage.service.MagMenuInfoService.java
 * Author: Administrator
 * Description:
 */
public interface MagMenuInfoService {

    void insertMenuInfo(ManagerMenuInfoVO managerMenuInfoVO);

    /**
     *编辑菜单
     * @param managerMenuInfoVO
     */
    void updateMenuInfo(ManagerMenuInfoVO managerMenuInfoVO);
    /**
     * 根据菜单id修改菜单状态
     * @param id
     * @param status
     */
    void updateMenuStatus(Integer id, Integer status);

}
