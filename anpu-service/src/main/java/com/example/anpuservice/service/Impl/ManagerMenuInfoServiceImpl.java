package com.example.anpuservice.service.Impl;


import com.alibaba.fastjson.JSONObject;
import com.example.anpuservice.constants.MenuConstant;
import com.example.anpuservice.constants.UserConstant;
import com.example.anpuservice.enums.MenuTypeEnum;
import com.example.anpuservice.exception.RiskBackStageException;
import com.example.anpuservice.mapper.ManagerMenuInfoMapper;
import com.example.anpuservice.model.ManagerMenuInfo;
import com.example.anpuservice.service.ManagerMenuInfoService;
import com.example.anpuservice.service.MenuPowerService;
import com.example.anpuservice.service.RolePowerService;
import com.example.anpuservice.service.UserRoleService;
import com.example.anpuservice.vo.ManagerMenuInfoVO;
import com.example.anpuservice.vo.MenuInfoVO;
import com.example.anpuservice.vo.MenuPowerVO;
import com.example.anpuservice.vo.PowerInsertInfoVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ManagerMenuInfoServiceImpl implements ManagerMenuInfoService {
    @Autowired
    private UserRoleService managerUserRoleService;
    @Autowired
    private RolePowerService managerRolePowerService;
    @Autowired
    private ManagerMenuInfoMapper managerMenuInfoMapper;
    @Autowired
    private MenuPowerService managerMenuPowerService;

    @Override
    public List<MenuInfoVO> findMenuTree() {
        return this.getAllMenuList(null, "menuPower");
    }

    @Override
    public MenuPowerVO selectCurrentLoginMenuList(Integer userId) {
        MenuPowerVO menuPowerVO = new MenuPowerVO();
        //系统管理员，拥有最高权限
        List<MenuInfoVO> menuList = new ArrayList<>();
        if (userId == UserConstant.SUPER_ADMIN_USER_ID) {
            menuList = getAllMenuList(null, "false");
        } else {
            List<Integer> menuIdList = managerRolePowerService.findCurrentLoginMenuIdList(userId);
            if (menuIdList == null) {
                return null;
            }
            menuList = getAllMenuList(menuIdList, "false");
        }
        menuPowerVO.setMenuList(menuList);
        //用户权限列表
        Set<String> permsSet = managerUserRoleService.getUserPermissions(userId);
        menuPowerVO.setPermissions(permsSet);
        return menuPowerVO;
    }

    @Override
    public List<Integer> queryParentMenuIds(List<Integer> menuIdList) {
        return managerMenuInfoMapper.selectParentIdList(menuIdList);
    }


    @Override
    public List<ManagerMenuInfo> findParentMenuTreeList() {
        ManagerMenuInfo managerMenuInfo = new ManagerMenuInfo();
        managerMenuInfo.setStatus(1);
        managerMenuInfo.setMenuType(MenuTypeEnum.CATALOG.getValue());
        return managerMenuInfoMapper.select(managerMenuInfo);
    }

    @Override
    public PageInfo<ManagerMenuInfo> selectManagerMenuList(Integer currentPage, Integer pageSize,
                                                           String menuName, String parentMenuId, Integer menuType, Integer status) {
        if (currentPage != null && pageSize != null) {
            PageHelper.startPage(currentPage, pageSize);
        }

        List<ManagerMenuInfo> managerMenuInfoList = managerMenuInfoMapper.selectMenuInfoList(menuName, parentMenuId, menuType, status);
        return new PageInfo<>(managerMenuInfoList);
    }

    @Override
    public ManagerMenuInfoVO getManagerMenuID(Integer id) {
        if (id == null) {
            throw new RiskBackStageException(MenuConstant.MENU_MENUID_NOTEMPTY);
        }
        ManagerMenuInfo managerMenuInfo = managerMenuInfoMapper.selectByPrimaryKey(id);
        ManagerMenuInfoVO managerMenuInfoVO = new ManagerMenuInfoVO();
        if (managerMenuInfo != null) {
            managerMenuInfoVO.setId(id);
            managerMenuInfoVO.setMenuName(managerMenuInfo.getMenuName());
            managerMenuInfoVO.setIcon(managerMenuInfo.getIcon());
            managerMenuInfoVO.setMenuType(managerMenuInfo.getMenuType());
            managerMenuInfoVO.setOrderNum(managerMenuInfo.getOrderNum());
            managerMenuInfoVO.setParentId(managerMenuInfo.getParentId());
            managerMenuInfoVO.setPowerUrl(managerMenuInfo.getPowerUrl());
            managerMenuInfoVO.setStatus(managerMenuInfo.getStatus());
        }
        //权限列表
        List<PowerInsertInfoVO> powerInsertInfoVOList = managerMenuPowerService.selectMenuPowerByMenuId(id);
        managerMenuInfoVO.setPowerList(powerInsertInfoVOList);
        return managerMenuInfoVO;
    }


    @Override
    public ManagerMenuInfoVO getRiskManagerMenuBeforeInfo(String paramValue) {
        ManagerMenuInfo managerMenuInfo = JSONObject.parseObject(paramValue, ManagerMenuInfo.class);
        if (managerMenuInfo != null) {
            return this.getManagerMenuID(managerMenuInfo.getId());
        }
        return null;
    }

    /*
     *
     * 获取所有菜单列表
     *
     * @param menuIdList
     * @return
     */
    private List<MenuInfoVO> getAllMenuList(List<Integer> menuIdList, String allMenu) {
        //查询根菜单列表
        List<MenuInfoVO> menuList = queryListParentId(0, menuIdList, null);

        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList, allMenu);
        return menuList;
    }

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId   父菜单ID
     * @param menuIdList 用户菜单ID
     * @return
     */
    public List<MenuInfoVO> queryListParentId(Integer parentId, List<Integer> menuIdList, String allMenu) {
        List<MenuInfoVO> menuList = new ArrayList<>();

        if (allMenu != null && "menuPower".equalsIgnoreCase(allMenu)) {
            //查询菜单权限
            menuList = managerMenuPowerService.selectMenuPowerList(parentId);
        } else {

            menuList = managerMenuInfoMapper.queryListByParentId(parentId);
        }

        if (menuIdList == null) {
            return menuList;
        }

        List<MenuInfoVO> userMenuList = new ArrayList<>();

        for (MenuInfoVO menu : menuList) {
            if (menuIdList.contains(menu.getMenuId())) {
                MenuInfoVO menuInfoVO = new MenuInfoVO();

                try {
                    BeanUtils.copyProperties(menuInfoVO, menu);
                    userMenuList.add(menuInfoVO);
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return userMenuList;
    }

    /* *
     * 递归子菜单
     */
    private List<MenuInfoVO> getMenuTreeList(List<MenuInfoVO> menuList, List<Integer> menuIdList, String allMenu) {
        List<MenuInfoVO> subMenuList = new ArrayList<MenuInfoVO>();

        for (MenuInfoVO entity : menuList) {
            if (entity.getMenuType() == MenuTypeEnum.CATALOG.getValue()) {
                //目录
                List<MenuInfoVO> menuTreeList = getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList, allMenu), menuIdList, allMenu);
                entity.setList(menuTreeList);
            }
            subMenuList.add(entity);
        }
        return subMenuList;
    }

}
