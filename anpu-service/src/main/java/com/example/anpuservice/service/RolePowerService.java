package com.example.anpuservice.service;



import com.example.anpuservice.bo.RoleVO;
import com.example.anpuservice.model.ManagerRolePower;

import java.util.List;

/**
 * @Author: lichenghuai
 * @Date: 2019/2/27 17:00
 * @Version 1.0
 */
public interface RolePowerService {
    /**
     * 根据角色id 查询 角色权限和角色信息 获取角色权限id
     * @param roleId
     * @return
     */
    List<Integer> queryPowerIdList(Integer roleId);

    /**
     * 根据权限信息id 查询有效的角色权限
     * @param powerId
     * @return
     */
    List<ManagerRolePower> findManagerRolePower(List<Integer> powerId);

    /**
     *  不是超管 根据userid 查询对应的查找到子菜单Id和子菜单对应的父菜单ID
     * @param userId
     * @return
     */
    List<Integer> findCurrentLoginMenuIdList(Integer userId);

    void updateManagerRolePowerStatus(List<ManagerRolePower> rolePowerList, Integer status);

    /**
     * 将角色权限置为启用 只启用当前key存在的对应的角色权限的关系，其他剔除掉的key的权限和角色关系不启用
     * @param powerId
     * @param status
     */
    void updateRolePowerStatus(Integer powerId, Integer status);

    /**
     * 赋予角色权限
     * @param roleVO
     */
    void addPowerToRole(RoleVO roleVO);

}
