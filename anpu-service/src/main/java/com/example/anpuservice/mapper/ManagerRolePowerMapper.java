package com.example.anpuservice.mapper;


import com.example.anpuservice.common.BaseMapper;
import com.example.anpuservice.model.ManagerRolePower;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ManagerRolePowerMapper extends BaseMapper<ManagerRolePower> {
    /**
     * 根据角色id 查询 rolepower 、role info 获取可用的权限id
     * @param roleId
     * @return
     */
    List<Integer> findPowerIdList(@Param("roleId") Integer roleId);


    /**
     *根据用户id 查询 userrole、rolepower、menupower 可用的菜单id（去重）
     * @param userId
     * @return
     */
    List<Integer> findCurrentLoginMenuIDList(@Param("userId") Integer userId);
}