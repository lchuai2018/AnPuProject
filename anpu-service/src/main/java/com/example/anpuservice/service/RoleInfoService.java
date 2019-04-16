package com.example.anpuservice.service;



import com.example.anpuservice.model.ManagerRoleInfo;
import com.example.anpuservice.vo.ManagerRoleInfoVO;

import java.util.List;

/**
 * @Author: lichenghuai
 * @Date: 2019/2/28 9:39
 * @Version 1.0
 */
public interface RoleInfoService {

    /**
     *
     * 查看所有角色信息
     * @return
     */
    List<ManagerRoleInfo> findAllRole();


    /**
     * 根据编号查询角色信息
     * @param Id
     * @return
     */
    ManagerRoleInfo findById(Integer Id);
    /**
     * 根据角色ID查看角色信息
     * @param roleId
     * @return
     */
    ManagerRoleInfoVO findRoleInfoByRoleID(Integer roleId);
    /**
     * 角色信息添加
     * @param managerRoleInfo
     */
    void insertManagerRole(ManagerRoleInfo managerRoleInfo);

    /**
     * 查看所有角色树结构
     * @return
     */
    List<ManagerRoleInfo> selectAllRoleList();


    /**
     * 后台角色操作日志
     * @param paramValue
     * @return
     */
    ManagerRoleInfo getRoleBeforeInfo(String paramValue);

}
