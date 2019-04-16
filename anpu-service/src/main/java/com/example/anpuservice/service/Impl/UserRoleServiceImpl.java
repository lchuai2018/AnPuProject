package com.example.anpuservice.service.Impl;

import com.example.anpuservice.constants.UserConstant;
import com.example.anpuservice.exception.RiskBackStageException;
import com.example.anpuservice.mapper.ManagerUserRoleMapper;
import com.example.anpuservice.model.ManagerUserRole;
import com.example.anpuservice.service.PowerInfoService;
import com.example.anpuservice.service.UserRoleService;
import com.example.anpuservice.utils.ShiroUtils;
import com.example.anpuservice.utils.StringTransUtil;
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
 * @date 2019/2/26 15:24
 */
@Slf4j
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private ManagerUserRoleMapper managerUserRoleMapper;
    @Autowired
    private PowerInfoService managerPowerInfoService;
    @Override
    public List<ManagerUserRole> findByManagerUserId(Integer managerId) {
        List<ManagerUserRole> managerUserRoleList = managerUserRoleMapper.findByManagerUserId(managerId);
        return managerUserRoleList;
    }

    @Transactional
    @Override
    public void saveOrUpdateUserRole(Integer id, Integer roleId) {
        Example selectExample = new Example(ManagerUserRole.class);
        Example.Criteria selectCriteria = selectExample.createCriteria();
        selectCriteria.andEqualTo("userId", id);
        selectCriteria.andEqualTo("status", 1);
        List<ManagerUserRole> managerUserRoleList = managerUserRoleMapper.selectByExample(selectExample);
        if (managerUserRoleList != null && managerUserRoleList.size() > 0) {
            List<Integer> roleIdList = new ArrayList<>();
            managerUserRoleList.stream().forEach(managerUserRole -> {
                roleIdList.add(managerUserRole.getRoleId());
            });
            if (roleIdList.contains(roleId)) {
                return;
            } else {
                //首先将原有的全部置为已删除状态
                Example delExample = new Example(ManagerUserRole.class);
                Example.Criteria delCriteria = delExample.createCriteria();
                delCriteria.andEqualTo("userId", id);
                ManagerUserRole roleMenu = new ManagerUserRole();
                roleMenu.setStatus(0);
                managerUserRoleMapper.updateByExampleSelective(roleMenu, delExample);
            }
        }

        ManagerUserRole managerUserRole = new ManagerUserRole();
        managerUserRole.setRoleId(roleId);
        managerUserRole.setStatus(1);
        managerUserRole.setUserId(id);
        managerUserRole.setCreatedManagerId(ShiroUtils.getUserId());
        managerUserRole.setCreateTime(new Date());
        managerUserRoleMapper.insertSelective(managerUserRole);
    }

    @Override
    public void insertManagerPowers(String roleIds, Integer managerId) {
        List<Integer> powerIdList = StringTransUtil.getIntegerList(roleIds);
        boolean flag = false;
        if (managerId > 0) {
            flag = insertManagerPowers(powerIdList, managerId);
        }
        if (!flag) {
            throw new RiskBackStageException(UserConstant.USER_ADD_INFOFAIL);
        }
    }
    @Override
    public Set<String> getUserPermissions(Integer userId) {
        //用户权限列表
        List<String> permsList = new ArrayList<>();
        if (userId.intValue() == UserConstant.SUPER_ADMIN_USER_ID.intValue()) {
            //用户权限列表
            permsList = managerPowerInfoService.queryPowerKey();
        } else {
            //用户权限列表
            permsList = managerUserRoleMapper.queryAllUserPowerKeys(userId);
        }

        Set<String> permsSet = new HashSet<String>();
        permsSet.addAll(permsList);
        return permsSet;
    }


    public boolean insertManagerPowers(List<Integer> roleList, Integer managerId) {
        int row = 0;
        int powerLength = roleList.size();
        for (Integer roleId : roleList) {
            ManagerUserRole managerPowerInfo = new ManagerUserRole();
            managerPowerInfo.setCreatedManagerId(ShiroUtils.getUserId());
            managerPowerInfo.setRoleId(roleId);
            managerPowerInfo.setUserId(managerId);
            if (insertManagerPower(managerPowerInfo) > 0) {
                row++;
            }
        }
        if (row < powerLength) {
            return false;
        }
        return true;
    }

    public int insertManagerPower(ManagerUserRole managerUserRole) {
        managerUserRole.setCreateTime(new Date());
        managerUserRole.setUpdateTime(new Date());
        return managerUserRoleMapper.insertSelective(managerUserRole);
    }
}
