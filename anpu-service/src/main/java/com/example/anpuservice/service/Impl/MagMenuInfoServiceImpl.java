package com.example.anpuservice.service.Impl;


import com.example.anpuservice.constants.MenuConstant;
import com.example.anpuservice.enums.MenuTypeEnum;
import com.example.anpuservice.exception.RiskBackStageException;
import com.example.anpuservice.mapper.ManagerMenuInfoMapper;
import com.example.anpuservice.model.ManagerMenuInfo;
import com.example.anpuservice.service.MagMenuInfoService;
import com.example.anpuservice.service.MenuPowerService;
import com.example.anpuservice.service.PowerInfoService;
import com.example.anpuservice.service.RolePowerService;
import com.example.anpuservice.utils.ShiroUtils;
import com.example.anpuservice.vo.ManagerMenuInfoVO;
import com.example.anpuservice.vo.MenuInfoVO;
import com.example.anpuservice.vo.PowerInsertInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author lichenghuai
 * @version 1.0
 * @description
 * @date 2019/2/27 17:11
 */
@Slf4j
@Service
public class MagMenuInfoServiceImpl implements MagMenuInfoService {
    @Autowired
    private ManagerMenuInfoMapper managerMenuInfoMapper;
    @Autowired
    private MenuPowerService managerMenuPowerService;
    @Autowired
    private PowerInfoService managerPowerInfoService;

    @Autowired
    private RolePowerService managerRolePowerService;
    @Override
    @Transactional
    public void insertMenuInfo(ManagerMenuInfoVO managerMenuInfoVO) {

        if (managerMenuInfoVO == null) {
            throw new RiskBackStageException(MenuConstant.MENU_ADD_PARAMNOTEMPTY);
        }

        if (StringUtils.isBlank(managerMenuInfoVO.getMenuName())) {
            throw new RiskBackStageException(MenuConstant.MENU_ADD_MENUNAMENOTEMPTY);
        }

        checkMenuName(managerMenuInfoVO.getMenuName(), null);

        Integer menuType = managerMenuInfoVO.getMenuType();
        if (menuType == null) {
            throw new RiskBackStageException(MenuConstant.MENU_TYPE_NOTEMPTY);
        }

        ManagerMenuInfo managerMenuInfo = new ManagerMenuInfo();
        //目录
        //TODO  判断当前数据菜单类型不是目录
        if (menuType != MenuTypeEnum.CATALOG.getValue()) {
            if (StringUtils.isBlank(managerMenuInfoVO.getPowerUrl())) {
                throw new RiskBackStageException(MenuConstant.MENU_ADD_MENUURLNOTEMPTY);
            }
            //检查功能请求路径是不是唯一的
            checkMenuUrlUnique(managerMenuInfoVO.getPowerUrl(), null);
        }

        managerMenuInfo.setIcon(managerMenuInfoVO.getIcon());
        managerMenuInfo.setParentId(managerMenuInfoVO.getParentId());
        managerMenuInfo.setMenuName(managerMenuInfoVO.getMenuName());
        managerMenuInfo.setPowerUrl(managerMenuInfoVO.getPowerUrl());
        managerMenuInfo.setMenuType(managerMenuInfoVO.getMenuType());
        managerMenuInfo.setStatus(managerMenuInfoVO.getStatus());
        managerMenuInfo.setOrderNum(managerMenuInfoVO.getOrderNum());
        //TODO   当前用户登录的userId
        managerMenuInfo.setCreateUserId(ShiroUtils.getUserId());
        managerMenuInfo.setCreateTime(new Date());
        managerMenuInfo.setUpdateTime(new Date());
        managerMenuInfoMapper.insertSelective(managerMenuInfo);

        if (managerMenuInfo.getId() == null) {
            throw new RiskBackStageException(MenuConstant.MENU_ADD_FAIL);
        }

        //   菜单
        if (menuType != MenuTypeEnum.CATALOG.getValue()) {
            List<PowerInsertInfoVO> powerList = managerMenuInfoVO.getPowerList();
            //添加权限
            List<Integer> permIdList = managerPowerInfoService.insertPermInfo(managerMenuInfo.getId(), powerList, managerMenuInfoVO.getMenuName());
            //添加菜单权限关系
            managerMenuPowerService.insertMenuPerm(managerMenuInfo.getId(), permIdList, managerMenuInfoVO.getStatus());
        }

    }

    @Transactional
    @Override
    public void updateMenuInfo(ManagerMenuInfoVO managerMenuInfoVO) {
        if (managerMenuInfoVO == null) {
            throw new RiskBackStageException(MenuConstant.MENU_UPDATE_PARAMNOTEMPTY);
        }

        if (StringUtils.isBlank(managerMenuInfoVO.getMenuName())) {
            throw new RiskBackStageException(MenuConstant.MENU_UPDATE_MENUNAMENOTEMPTY);
        }

        if (managerMenuInfoVO.getId() == null) {
            throw new RiskBackStageException(MenuConstant.MENU_UPDATE_MENUIDMUST);
        }

        checkMenuName(managerMenuInfoVO.getMenuName(), managerMenuInfoVO.getId());

        Integer menuType = managerMenuInfoVO.getMenuType();
        if (menuType == null) {
            throw new RiskBackStageException(MenuConstant.MENU_UPDATEMENUTYPENOTEMPTY);
        }

        ManagerMenuInfo managerMenuInfo = new ManagerMenuInfo();
        managerMenuInfo.setId(managerMenuInfoVO.getId());
        //目录
        if (menuType != MenuTypeEnum.CATALOG.getValue()) {
            if (StringUtils.isBlank(managerMenuInfoVO.getPowerUrl())) {
                throw new RiskBackStageException(MenuConstant.MENU_UPDATE_MENUURLNOTEMPTY);
            }
            checkMenuUrlUnique(managerMenuInfoVO.getPowerUrl(), managerMenuInfoVO.getId());
        }

        managerMenuInfo.setIcon(managerMenuInfoVO.getIcon());
        managerMenuInfo.setParentId(managerMenuInfoVO.getParentId());
        managerMenuInfo.setMenuName(managerMenuInfoVO.getMenuName());
        managerMenuInfo.setPowerUrl(managerMenuInfoVO.getPowerUrl());
        managerMenuInfo.setMenuType(managerMenuInfoVO.getMenuType());
        managerMenuInfo.setStatus(managerMenuInfoVO.getStatus());
        managerMenuInfo.setOrderNum(managerMenuInfoVO.getOrderNum());
        managerMenuInfo.setCreateUserId(ShiroUtils.getUserId());
        managerMenuInfo.setUpdateTime(new Date());
        managerMenuInfoMapper.updateByPrimaryKeySelective(managerMenuInfo);

        try {
            //菜单权限、权限、角色权限修改
            List<Integer> permIdList = managerPowerInfoService.updatePowerInfo(managerMenuInfo.getId(), managerMenuInfoVO.getMenuName(),
                    managerMenuInfoVO.getPowerList(), managerMenuInfoVO.getStatus());

            //添加菜单权限关系
            managerMenuPowerService.insertMenuPerm(managerMenuInfo.getId(), permIdList, managerMenuInfoVO.getStatus());
        } catch (Exception e) {
            throw new RiskBackStageException(e.getMessage());
        }

    }


    private void checkMenuName(String menuName, Integer id) {
        Example example = new Example(ManagerMenuInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("menuName", menuName);
        if (id != null) {
            criteria.andNotEqualTo("id", id);
        }
        List<ManagerMenuInfo> managerMenuInfoList = managerMenuInfoMapper.selectByExample(example);
        if (managerMenuInfoList != null && managerMenuInfoList.size() > 0) {
            throw new RiskBackStageException(MenuConstant.MENU_CHECK_MENUNAMEEXITS);
        }
    }
    private void checkMenuUrlUnique(String powerUrl, Integer id) {
        Example example = new Example(ManagerMenuInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("powerUrl", powerUrl);
        if (id != null) {
            criteria.andNotEqualTo("id", id);
        }

        List<ManagerMenuInfo> managerMenuInfoList = managerMenuInfoMapper.selectByExample(example);
        if (managerMenuInfoList != null && managerMenuInfoList.size() > 0) {
            throw new RiskBackStageException(String.format("该菜单Url：%s已存在", powerUrl));
        }
    }




    @Transactional
    @Override
    public void updateMenuStatus(Integer id, Integer status) {
        if (id == null || status == null) {
            throw new RiskBackStageException(MenuConstant.MENU_UPDATE_PARAMNOTEMPTY);
        }
        ManagerMenuInfo managerMenuInfo = managerMenuInfoMapper.selectByPrimaryKey(id);
        if (managerMenuInfo == null) {
            throw new RiskBackStageException(MenuConstant.MENU_MEUNID_NOSEARCHMENU);
        }

        if (status.equals(managerMenuInfo.getStatus())) {
            throw new RiskBackStageException(MenuConstant.MENU_STATUS_NOREPEATMODIFY);
        }

        try {
            //修改菜单状态
            ManagerMenuInfo updateMenuInfo = new ManagerMenuInfo();
            updateMenuInfo.setId(id);
            updateMenuInfo.setStatus(status);
            managerMenuInfoMapper.updateByPrimaryKeySelective(updateMenuInfo);

            //判断菜单是否是菜单 是菜单 禁用菜单权限 禁用权限 禁用权限角色
            List<MenuInfoVO> voList = managerMenuInfoMapper.queryListByParentId(id);
            if (voList != null && voList.size() > 0) {
                voList.stream().filter(Objects::nonNull).forEach(menuInfoVO -> {
                    //如果有子菜单 置为删除或者启用
                    Integer childId = menuInfoVO.getMenuId();
                    ManagerMenuInfo updateChildMenuInfo = new ManagerMenuInfo();
                    updateChildMenuInfo.setId(childId);
                    updateChildMenuInfo.setStatus(status);
                    managerMenuInfoMapper.updateByPrimaryKeySelective(updateChildMenuInfo);
                    //禁用子菜单菜单权限关系 禁用权限 禁用权限角色
                    this.updateMenuRolePowerStatus(childId, status);
                });
            } else {
                this.updateMenuRolePowerStatus(id, status);
            }
        } catch (RiskBackStageException e) {
            System.out.println(e.getErrorMsg());
            throw new RiskBackStageException(MenuConstant.MENU_UPDATE_STATUSERROE);
        }
    }
    private void updateMenuRolePowerStatus(Integer id, Integer status) {
        //禁用子菜单菜单权限关系 禁用权限 禁用权限角色
        managerMenuPowerService.updateMenuPowerStatus(id, null, status);

        List<Integer> powerIdList = managerMenuPowerService.findAllPowerIdListByMenuId(id);
        if (powerIdList != null && powerIdList.size() > 0) {

            if (powerIdList.size() > 1) {
                powerIdList = managerPowerInfoService.selectManagerPowerInfo(powerIdList);
            }

            powerIdList.stream().filter(Objects::nonNull).forEach(powerId -> {
//                Integer powerId = managerMenuPower.getPowerId();
                managerPowerInfoService.updatePowerInfoStatus(powerId, status, null);

                managerRolePowerService.updateRolePowerStatus(powerId, status);
            });
        }
    }
}
