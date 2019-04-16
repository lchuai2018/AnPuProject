package com.example.anpuservice.service;


import com.example.anpuservice.bo.ManagerUserRequestVO;
import com.example.anpuservice.model.ManagerUserInfo;
import com.example.anpuservice.vo.ManagerUserInfoRespVO;
import com.example.anpuservice.vo.ManagerUserInfoVO;
import com.example.anpuservice.vo.PasswordVO;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserInfoService {
    /**
     * 根据账号和密码进行查询
     *
     * @param accountNumber
     * @param password
     * @return
     */
    ManagerUserInfo findManagerByAccountPassword(String accountNumber, String password);


    /**
     * 根据用户id查询
     *
     * @param managerId
     * @return
     */
    ManagerUserInfoVO findManagerById(Integer managerId);

    /**
     * 修改用户信息
     *
     * @param managerUserInfoVO
     */
    void updateManager(ManagerUserInfoVO managerUserInfoVO);

    /**
     * 批量修改用户信息状态
     *
     * @param managerIdList
     * @param status
     * @param type
     */
    void updateManagerUsers(List<Integer> managerIdList, Integer status, Integer type);

    /**
     * 修改系统当前登录的用户登录密码
     *
     * @param passwordVO
     * @param userId
     */
    void updateSysCurrentUserLoginPassword(PasswordVO passwordVO, Integer userId);

    /**
     * 校验新增用户账号唯一性
     *
     * @param accountNum
     */
    void checkUserAccountName(String accountNum);

    /**
     * 查看所有管理用户信息
     */
    PageInfo<ManagerUserInfoRespVO> findAllManagers(Integer currentPage, Integer pageSize, String name, String roleIds, Integer status);

    /**
     * 用户添加及角色分配
     *
     * @param managerUserRequestVO
     */
    void insertManagerUserInfo(ManagerUserRequestVO managerUserRequestVO);

    //void updateSelectiveById(ManagerUserInfo managerUserInfo);

    List<ManagerUserInfoVO> getManagerBeforeUserInfo(String paramValue, String methodName);

    void updateSelectiveById(ManagerUserInfo managerUserInfo);

    /**
     * 导出管理用户信息
     *
     * @param pageSize
     * @param name
     * @param roleIds
     * @param status
     * @param response
     */
    void exportManagerUserExcel(Integer currentPage, Integer pageSize, String name, String roleIds, Integer status, HttpServletResponse response);


}
