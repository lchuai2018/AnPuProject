package com.example.anpuservice.service.Impl;


import com.example.anpuservice.constants.MenuConstant;
import com.example.anpuservice.exception.RiskBackStageException;
import com.example.anpuservice.mapper.ManagerMenuPowerMapper;
import com.example.anpuservice.mapper.ManagerRolePowerMapper;
import com.example.anpuservice.model.ManagerMenuPower;
import com.example.anpuservice.model.ManagerPowerInfo;
import com.example.anpuservice.service.MenuPowerService;
import com.example.anpuservice.utils.ShiroUtils;
import com.example.anpuservice.vo.MenuInfoVO;
import com.example.anpuservice.vo.PowerInsertInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * FileName: com.iyingke.riskbackstage.service.impl.ManagerMenuPowerServiceImpl.java
 * Author: Administrator
 * Description:
 */
@Service
public class MenuPowerServiceImpl implements MenuPowerService {
    @Autowired
    private ManagerMenuPowerMapper managerMenuPowerMapper;
    @Autowired
    private ManagerRolePowerMapper managerRolePowerMapper;

    @Override
    public List<Integer> findAllPowerIdListByMenuId(Integer childId) {
        return managerMenuPowerMapper.findAllPowerIdListByMenuId(childId);
    }
    @Override
    public List<Integer> findPowerIdListByMenuId(Integer id) {
        return managerMenuPowerMapper.selectPowerIdListByMenuId(id);
    }
    @Transactional
    @Override
    public void insertMenuPerm(Integer id, List<Integer> permIdList, Integer status) {
        if (permIdList != null && permIdList.size() > 0) {
            try {
                permIdList.stream().filter(Objects::nonNull).forEach(permId -> {
                    ManagerMenuPower managerMenuPower = new ManagerMenuPower();
                    managerMenuPower.setMenuId(id);
                    managerMenuPower.setPowerId(permId);
                    managerMenuPower.setStatus(status);
                    managerMenuPower.setCreateUserId(ShiroUtils.getUserId());
                    managerMenuPower.setCreateTime(new Date());
                    managerMenuPower.setUpdateTime(new Date());
                    managerMenuPowerMapper.insertSelective(managerMenuPower);
                });
            } catch (Exception e) {
                throw new RiskBackStageException(MenuConstant.MENU_ADD_PERMISSIONSRALATIONFAIL);
            }
        }
    }


    @Override
    public List<MenuInfoVO> selectMenuPowerList(Integer parentId) {
        List<MenuInfoVO> voList = managerMenuPowerMapper.selectMenuPowerByParentId(parentId);
        return voList;
    }
/*
    @Override
    public List<Integer> selectMenuIdList(List<Integer> powerIdList) {
        return managerMenuPowerMapper.selectMenuIdList(powerIdList);
    }

    @Transactional
    @Override
    public void insertMenuPerm(Integer id, List<Integer> permIdList, Integer status) {
        if (permIdList != null && permIdList.size() > 0) {
            try {
                permIdList.stream().filter(Objects::nonNull).forEach(permId -> {
                    ManagerMenuPower managerMenuPower = new ManagerMenuPower();
                    managerMenuPower.setMenuId(id);
                    managerMenuPower.setPowerId(permId);
                    managerMenuPower.setStatus(status);
                    managerMenuPower.setCreateUserId(ShiroUtils.getUserId());
                    managerMenuPower.setCreateTime(new Date());
                    managerMenuPower.setUpdateTime(new Date());
                    managerMenuPowerMapper.insertSelective(managerMenuPower);
                });
            } catch (Exception e) {
                throw new RiskBackStageException("添加菜单权限关系失败！");
            }
        }
    }*/

    @Override
    public List<PowerInsertInfoVO> selectMenuPowerByMenuId(Integer id) {
        return managerMenuPowerMapper.selectMenuPowerByMenuId(id);
    }
/*

    @Transactional
    @Override
    public void updateByExampleSelective(ManagerMenuPower managerMenuPower, Example example) {
        managerMenuPowerMapper.updateByExampleSelective(managerMenuPower, example);
    }

    @Override
    public List<Integer> findPowerIdListByMenuId(Integer id) {
        return managerMenuPowerMapper.selectPowerIdListByMenuId(id);
    }

    @Transactional
    @Override
    public void updateMenuPowerStatus(Integer id, Integer powerId, Integer status) {
        Example updateMenuPowerExample = new Example(ManagerMenuPower.class);
        Example.Criteria updateMenuPowerCriteria = updateMenuPowerExample.createCriteria();
        updateMenuPowerCriteria.andEqualTo("menuId", id);
        if (powerId != null) {
            updateMenuPowerCriteria.andEqualTo("powerId", powerId);
        }
        ManagerMenuPower managerMenuPower = new ManagerMenuPower();
        managerMenuPower.setStatus(status);
        managerMenuPowerMapper.updateByExampleSelective(managerMenuPower, updateMenuPowerExample);
    }

    @Override
    public List<ManagerPowerInfo> checkPowerKeyExist(String powerKey, Integer id) {
        List<ManagerPowerInfo> managerPowerInfoList = managerMenuPowerMapper.selectPowerIsExistByPowerKey(powerKey, id);
        return managerPowerInfoList;
    }

    @Override
    public List<Integer> findAllPowerIdListByMenuId(Integer childId) {
        return managerMenuPowerMapper.findAllPowerIdListByMenuId(childId);
    }
*/



    @Override
    public List<ManagerPowerInfo> checkPowerKeyExist(String powerKey, Integer id) {
        List<ManagerPowerInfo> managerPowerInfoList = managerMenuPowerMapper.selectPowerIsExistByPowerKey(powerKey, id);
        return managerPowerInfoList;
    }
    @Transactional
    @Override
    public void updateMenuPowerStatus(Integer id, Integer powerId, Integer status) {
        Example updateMenuPowerExample = new Example(ManagerMenuPower.class);
        Example.Criteria updateMenuPowerCriteria = updateMenuPowerExample.createCriteria();
        updateMenuPowerCriteria.andEqualTo("menuId", id);
        if (powerId != null) {
            updateMenuPowerCriteria.andEqualTo("powerId", powerId);
        }
        ManagerMenuPower managerMenuPower = new ManagerMenuPower();
        managerMenuPower.setStatus(status);
        managerMenuPowerMapper.updateByExampleSelective(managerMenuPower, updateMenuPowerExample);
    }

}
