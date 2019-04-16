package com.example.anpuservice.service.Impl;


import com.example.anpuservice.constants.UserConstant;
import com.example.anpuservice.exception.RiskBackStageException;
import com.example.anpuservice.mapper.ManagerUserRoleMapper;
import com.example.anpuservice.model.ManagerUserInfo;
import com.example.anpuservice.model.ManagerUserRole;
import com.example.anpuservice.service.ManagerService;
import com.example.anpuservice.service.UserInfoService;
import com.example.anpuservice.utils.IpUtil;
import com.example.anpuservice.utils.ShiroUtils;
import com.example.anpuservice.utils.ValidateUtil;
import com.example.anpuservice.vo.ManagerUserInfoVO;
import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lichenghuai
 * @version 1.0
 * @description
 * @date 2019/2/28 11:58
 */

@Slf4j
@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ManagerUserRoleMapper managerUserRoleMapper;

    @Override
    public String login(String accountNum, String password, String ip) {
        //解密及md5加密
        String loginToken="12345";
        password = ValidateUtil.checkPasswordForMag(password);
        ManagerUserInfo managerUserInfo = userInfoService.findManagerByAccountPassword(accountNum, password);
        if (managerUserInfo != null) {
            if (managerUserInfo.getStatus().equals(0)) {
                throw new RiskBackStageException("账号被禁止使用,请联系管理员！");
            }
            Subject subject = ShiroUtils.getSubject();
            String managerUserInfoPassword = managerUserInfo.getPassword();
            //sha256加密
            UsernamePasswordToken token = new UsernamePasswordToken(managerUserInfo.getAccountNumber(), managerUserInfoPassword);
            subject.login(token);
            subject.getSession().setTimeout(5 * 60 * 60 * 1000);

            //记录用户登录时间与ip
            ManagerUserInfo userInfo = new ManagerUserInfo();
            userInfo.setId(managerUserInfo.getId());
            userInfo.setLastLoginTime(new Date());
            userInfo.setLastLoginIp(ip);
            userInfoService.updateSelectiveById(userInfo);
            return loginToken;
        } else {
            throw new RiskBackStageException(UserConstant.USER_ACCOUNT_ORPASSWORD_ERROR);
        }
    }



    @Override
    public void logout() {

        ShiroUtils.logout();
    }
}
