package com.example.anpuservice.service.Impl;


import com.example.anpuservice.bo.RoleVO;
import com.example.anpuservice.constants.RoleConstant;
import com.example.anpuservice.exception.RiskBackStageException;
import com.example.anpuservice.mapper.ManagerRolePowerMapper;
import com.example.anpuservice.model.ManagerRolePower;
import com.example.anpuservice.service.ManagerMenuInfoService;
import com.example.anpuservice.service.RolePowerService;
import com.example.anpuservice.utils.ShiroUtils;
import com.example.anpuservice.vo.ManagerUserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @author lichenghuai
 * @version 1.0
 * @description
 * @date 2019/2/27 17:01
 */

@Slf4j
@Service
public class RolePowerServiceImpl implements RolePowerService {
    @Autowired
    private ManagerMenuInfoService managerMenuInfoService;
    @Autowired
    private ManagerRolePowerMapper managerRolePowerMapper;
    @Transactional
    @Override
    public void addPowerToRole(RoleVO roleVO) {
        if (roleVO.getRoleId().intValue() == RoleConstant.SUPER_ADMIN_ROLE_ID.intValue()) {
            throw new RiskBackStageException(RoleConstant.ROLE_SUPER_ADMINFOBIDUP);
        }
        ManagerUserInfoVO managerUserInfoVO = ShiroUtils.getUserEntity();
        if (managerUserInfoVO != null
                && managerUserInfoVO.getRoleId().intValue() == roleVO.getRoleId().intValue()) {
            throw new RiskBackStageException(RoleConstant.ROLE_NOTSUPPORT_OWNROLEPOWER);
        }
        saveOrUpdateRoleMenus(roleVO.getMenuIdList(), roleVO.getRoleId());
    }

    @Override
    public List<Integer> queryPowerIdList(Integer roleId) {
        return managerRolePowerMapper.findPowerIdList(roleId);
    }
    @Transactional
    @Override
    public void updateRolePowerStatus(Integer powerId, Integer status) {
        Example delRolePowerExample = new Example(ManagerRolePower.class);
        Example.Criteria delRolePowerCriteria = delRolePowerExample.createCriteria();
        delRolePowerCriteria.andEqualTo("powerId", powerId);
        ManagerRolePower managerRolePower = new ManagerRolePower();
        managerRolePower.setStatus(status);
        managerRolePowerMapper.updateByExampleSelective(managerRolePower, delRolePowerExample);
    }

    @Transactional
    @Override
    public void updateManagerRolePowerStatus(List<ManagerRolePower> rolePowerList, Integer status) {
        rolePowerList.stream().filter(Objects::nonNull).forEach(managerRolePower -> {
            Example delRolePowerExample = new Example(ManagerRolePower.class);
            Example.Criteria delRolePowerCriteria = delRolePowerExample.createCriteria();
            delRolePowerCriteria.andEqualTo("id", managerRolePower.getId());
            ManagerRolePower updateManagerRolePower = new ManagerRolePower();
            updateManagerRolePower.setStatus(status);
            managerRolePowerMapper.updateByExampleSelective(updateManagerRolePower, delRolePowerExample);
        });
    }
    @Override
    public List<ManagerRolePower> findManagerRolePower(List<Integer> powerId) {
        if (powerId == null){
            return null;
        }

        Example rolePowerExample = new Example(ManagerRolePower.class);
        Example.Criteria rolePowerCriteria = rolePowerExample.createCriteria();
        rolePowerCriteria.andIn("powerId", powerId);
        rolePowerCriteria.andEqualTo("status",1);
        return managerRolePowerMapper.selectByExample(rolePowerExample);
    }
    @Override
    public List<Integer> findCurrentLoginMenuIdList(Integer userId) {
        //查找到子菜单Id
        List<Integer> menuIdList = managerRolePowerMapper.findCurrentLoginMenuIDList(userId);
        if (!menuIdList.isEmpty()) {
            //查找子菜单对应的父菜单ID
            List<Integer> parentMenuIdList = managerMenuInfoService.queryParentMenuIds(menuIdList);
            menuIdList.addAll(parentMenuIdList);
        }
        return menuIdList;
    }


    private void saveOrUpdateRoleMenus(List<Integer> powerIdList, Integer roleId) {
        //首先将原有的全部置为已删除状态
        Example delExample = new Example(ManagerRolePower.class);
        Example.Criteria delCriteria = delExample.createCriteria();
        delCriteria.andEqualTo("roleId", roleId);
        ManagerRolePower managerRolePower = new ManagerRolePower();
        managerRolePower.setStatus(0);
        managerRolePowerMapper.updateByExampleSelective(managerRolePower, delExample);

        //转为set去重
        Set<Integer> powerIdSet = new HashSet<>(powerIdList);
        //检查新的关联是否已存在，若已存在则更新，否则新增
        for (Integer powerId : powerIdSet) {
            delExample.clear();
            Example.Criteria criteria = delExample.createCriteria();
            criteria.andEqualTo("roleId", roleId);
            criteria.andEqualTo("powerId", powerId);
            List<ManagerRolePower> exits = managerRolePowerMapper.selectByExample(delExample);
            ManagerRolePower updateRolePower = new ManagerRolePower();
            if (null != exits && exits.size() > 0) {
                updateRolePower.setStatus(1);
                managerRolePowerMapper.updateByExampleSelective(updateRolePower, delExample);
            } else {
                //没有则新增
                updateRolePower.setStatus(1);
                updateRolePower.setRoleId(roleId);
                updateRolePower.setPowerId(powerId);
                updateRolePower.setCreateUserId(ShiroUtils.getUserId());
                updateRolePower.setCreateTime(new Date());
                managerRolePowerMapper.insertSelective(updateRolePower);
            }
        }
    }

}
