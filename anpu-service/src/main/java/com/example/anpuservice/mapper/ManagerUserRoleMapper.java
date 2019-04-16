package com.example.anpuservice.mapper;


import com.example.anpuservice.common.BaseMapper;
import com.example.anpuservice.model.ManagerUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ManagerUserRoleMapper extends BaseMapper<ManagerUserRole> {
    /**
     *  根据uid 查询用户角色
     * @param managerUserId
     * @return
     */
    List<ManagerUserRole> findByManagerUserId(@Param("managerUserId") Integer managerUserId);

    /**
     * 根据user查询  userrole、rolepower、pwerinfo 可用的权限值
     * @param userId
     * @return
     */
    List<String> queryAllUserPowerKeys(@Param("userId") Integer userId);
}