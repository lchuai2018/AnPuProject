package com.example.anpuservice.service;



import com.example.anpuservice.model.ManagerUserRole;

import java.util.List;
import java.util.Set;

/**
 * @Author: lichenghuai
 * @Date: 2019/2/26 15:19
 * @Version 1.0
 */
public interface UserRoleService {
    /**
     * 根据uid管理员角色信息表
     * @param managerId
     * @return
     */
    List<ManagerUserRole> findByManagerUserId(Integer managerId);

    /**
     * 根据用户修改用户角色
     * @param id
     * @param roleId
     */
    void saveOrUpdateUserRole(Integer id, Integer roleId);

    /**
     * 为管理员添加多个角色
     */
    void insertManagerPowers(String roleIds, Integer managerUserId);

    /**
     * 根据userId 获取权限列表
     * @param userId
     * @return
     */
    Set<String> getUserPermissions(Integer userId);

}



