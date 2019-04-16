package com.example.anpuservice.service;


import com.example.anpuservice.model.ManagerUserInfo;
import com.example.anpuservice.vo.ManagerUserInfoVO;

/**
 * @Author: lichenghuai
 * @Date: 2019/2/28 11:58
 * @Version 1.0
 */
public interface ManagerService {

    /**
     * 登录成功返回token
     * @param accountNum
     * @param password
     * @param ip
     * @return
     */
    String login(String accountNum, String password, String ip);

    /**
     * 登出
     */
    void logout();

}
