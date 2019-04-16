package com.example.anpuservice.utils.shiro;


import com.example.anpuservice.mapper.ManagerUserInfoMapper;
import com.example.anpuservice.mapper.ManagerUserRoleMapper;
import com.example.anpuservice.model.ManagerUserInfo;
import com.example.anpuservice.model.ManagerUserRole;
import com.example.anpuservice.service.UserRoleService;
import com.example.anpuservice.vo.ManagerUserInfoVO;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.apache.shiro.session.Session;

import org.springframework.beans.BeanUtils;


import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 认证
 */
@Component
public class UserRealm extends AuthorizingRealm {


    @Autowired
    private ManagerUserInfoMapper managerUserInfoMapper;
    @Autowired
    private ManagerUserRoleMapper managerUserRoleMapper;
    @Autowired
    private UserRoleService managerUserRoleService;

    @Autowired
    private SessionDAO sessionDAO;

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ManagerUserInfo user = (ManagerUserInfo) principals.getPrimaryPrincipal();
        Integer userId = user.getId();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //用户权限列表
        Set<String> permsSet = managerUserRoleService.getUserPermissions(userId);
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accountName = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        // 踢出已登录的用户
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (principalCollection != null) {
                ManagerUserInfoVO managerUserInfoVO = (ManagerUserInfoVO) principalCollection.getPrimaryPrincipal();
                if (accountName.equals(managerUserInfoVO.getAccountNumber())) {
                    session.setTimeout(0);// 设置session立即失效，即将其踢出系统
                    break;
                }
            }
        }

        //查询用户信息
        List<ManagerUserInfo> managerUserInfoList = managerUserInfoMapper.findManagerByAccountNumber(accountName);

        //账号不存在
        if (managerUserInfoList == null || managerUserInfoList.size() <= 0) {
            throw new UnknownAccountException("账号或密码不正确");
        }

        ManagerUserInfo managerUserInfo = managerUserInfoList.get(0);

        //密码错误
        if (!password.equals(managerUserInfo.getPassword())) {
            throw new IncorrectCredentialsException("账号或密码不正确");
        }

        //账号锁定
        if (1 != managerUserInfo.getStatus()) {
            throw new LockedAccountException("账号已锁定,请联系超级管理员");
        }

        ManagerUserInfoVO managerUserInfoVO = new ManagerUserInfoVO();

        BeanUtils.copyProperties(managerUserInfo, managerUserInfoVO);
        //查询用户的角色id放入用户实体中
        List<ManagerUserRole> managerUserRoleList = managerUserRoleMapper.findByManagerUserId(managerUserInfo.getId());
        if (managerUserRoleList != null && managerUserRoleList.size() > 0) {
            managerUserRoleList.stream().filter(Objects::nonNull).forEach(managerUserRole -> {
                managerUserInfoVO.setRoleId(managerUserRole.getRoleId());
            });
        }

        managerUserInfoVO.setPassword(null);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(managerUserInfoVO, password, getName());
        return info;
    }


}
