package com.example.anpuservice.mapper;


import com.example.anpuservice.common.BaseMapper;
import com.example.anpuservice.model.ManagerUserInfo;
import com.example.anpuservice.vo.ManagerUserInfoRespVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ManagerUserInfoMapper extends BaseMapper<ManagerUserInfo> {

    /**
     *  根据账号查询用户信息
     * @param accountNumber
     * @return
     */
    List<ManagerUserInfo> findManagerByAccountNumber(@Param("accountNumber") String accountNumber);


    /**
     *   根据名称，角色id ，状态查询用户信息及角色信息
     * @param name
     * @param roleIds
     * @param status
     * @return
     */
    List<ManagerUserInfoRespVO> findAllManagersList(@Param("name") String name, @Param("roleIds") String roleIds, @Param("status") Integer status);


    /**
     *   根据uid 修改密码
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    Integer updateUserLoginPassword(@Param("userId") Integer userId, @Param("oldPassword") String oldPassword, @Param("newPassword") String newPassword);
}