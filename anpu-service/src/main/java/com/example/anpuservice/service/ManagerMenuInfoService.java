package com.example.anpuservice.service;


import com.example.anpuservice.model.ManagerMenuInfo;
import com.example.anpuservice.vo.ManagerMenuInfoVO;
import com.example.anpuservice.vo.MenuInfoVO;
import com.example.anpuservice.vo.MenuPowerVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ManagerMenuInfoService {
    /**
     * 所有菜单树结构
     *
     * @return
     */
    List<MenuInfoVO> findMenuTree();

    /**
     * 查看当前登录的权限菜单
     *
     * @param userId
     * @return
     */
    MenuPowerVO selectCurrentLoginMenuList(Integer userId);

    /**
     * 根据已知子菜单id查询父菜单
     *
     * @param menuIdList
     * @return
     */
    List<Integer> queryParentMenuIds(List<Integer> menuIdList);

    /**
     * 查询可用目录类型的菜单信息
     *
     * @return
     */
    List<ManagerMenuInfo> findParentMenuTreeList();

    PageInfo<ManagerMenuInfo> selectManagerMenuList(Integer currentPage, Integer pageSize, String menuName,
                                                    String parentMenuId, Integer menuType, Integer status);

    /**
     * 根据菜单ID查找菜单信息详情
     *
     * @param id
     * @return
     */
    ManagerMenuInfoVO getManagerMenuID(Integer id);

    ManagerMenuInfoVO getRiskManagerMenuBeforeInfo(String paramValue);

}
