package com.example.anpuservice.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.example.anpuservice.bo.ManagerUserRequestVO;
import com.example.anpuservice.constants.RoleConstant;
import com.example.anpuservice.constants.UserConstant;
import com.example.anpuservice.enums.StatusEnum;
import com.example.anpuservice.exception.RiskBackStageException;
import com.example.anpuservice.mapper.ManagerUserInfoMapper;
import com.example.anpuservice.model.ManagerUserInfo;
import com.example.anpuservice.model.ManagerUserRole;
import com.example.anpuservice.service.UserInfoService;
import com.example.anpuservice.service.UserRoleService;
import com.example.anpuservice.utils.EncryptUtil;
import com.example.anpuservice.utils.PoiExcelUtils;
import com.example.anpuservice.utils.ShiroUtils;
import com.example.anpuservice.utils.ValidateUtil;
import com.example.anpuservice.vo.ManagerUserInfoRespVO;
import com.example.anpuservice.vo.ManagerUserInfoVO;
import com.example.anpuservice.vo.PasswordVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserInfoServiceImpl extends BaseServiceImpl<ManagerUserInfoMapper, ManagerUserInfo> implements UserInfoService {

    @Autowired
    private UserRoleService managerUserRoleService;


    @Override
    public ManagerUserInfo findManagerByAccountPassword(String accountNumber, String password) {
        ManagerUserInfo managerInfo = new ManagerUserInfo();
        managerInfo.setAccountNumber(accountNumber);
        managerInfo.setPassword(password);
        return mapper.selectOne(managerInfo);
    }

    @Override
    public PageInfo<ManagerUserInfoRespVO> findAllManagers(Integer currentPage, Integer pageSize, String name, String roleIds, Integer status) {
        if (currentPage == null) {
            currentPage = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PageHelper.startPage(currentPage, pageSize);
        List<ManagerUserInfoRespVO> managerUserInfoList = mapper.findAllManagersList(name, roleIds, status);
        return new PageInfo<>(managerUserInfoList);
    }

    @Override
    public void exportManagerUserExcel(Integer currentPage, Integer pageSize, String name, String roleIds, Integer status, HttpServletResponse response) {
        PageInfo<ManagerUserInfoRespVO> voPageInfo = this.findAllManagers(currentPage, pageSize, name, roleIds, status);
        if (voPageInfo != null) {
            List<ManagerUserInfoRespVO> voList = voPageInfo.getList();
            List<Object[]> dataList = new ArrayList<>();
            String[] rowsName = new String[]{"编号", "姓名", "登录账号", "邮箱", "角色", "联系方式", "最近一次登录时间", "账号状态"};
            voList.stream().filter(Objects::nonNull).forEach(managerUserInfoRespVO -> {
                Object[] objs = new Object[rowsName.length];
                objs[0] = managerUserInfoRespVO.getId();
                objs[1] = managerUserInfoRespVO.getName();
                objs[2] = managerUserInfoRespVO.getAccountNumber();
                objs[3] = managerUserInfoRespVO.getEmail();
                objs[4] = managerUserInfoRespVO.getRoleName();
                objs[5] = managerUserInfoRespVO.getPhone();
                objs[6] = managerUserInfoRespVO.getLastLoginTime();
                objs[7] = StatusEnum.getStatusNameByStatus(managerUserInfoRespVO.getStatus());
                dataList.add(objs);
            });

            PoiExcelUtils ex = new PoiExcelUtils("用户管理", rowsName, dataList);
            try {
                ex.export(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @Transactional
    @Override
    public void insertManagerUserInfo(ManagerUserRequestVO managerUserRequestVO) {

        //校验添加用户请求体
        this.checkManagerUserRequest(managerUserRequestVO);

        ManagerUserInfo managerUserInfo = new ManagerUserInfo();
        managerUserInfo.setName(managerUserRequestVO.getName());
        managerUserInfo.setAccountNumber(managerUserRequestVO.getAccountNumber());
        managerUserInfo.setPhone(managerUserRequestVO.getPhone());
        managerUserInfo.setRemark(managerUserRequestVO.getRemark());
        managerUserInfo.setEmail(managerUserRequestVO.getEmail());
        managerUserInfo.setCreateUserId(ShiroUtils.getUserId());
        managerUserInfo.setCreateTime(new Date());
        managerUserInfo.setUpdateTime(new Date());

        managerUserInfo.setPassword("MDAwNjMwMDA2MTAwMDU4MDAwMTcwMDAxNzAwMDM4MDAwNTIwMDA4MQ==");
        String roleId = managerUserRequestVO.getRoleId();
        if (RoleConstant.SUPER_ADMIN_ROLE_ID.toString().equals(roleId)) {
            throw new RiskBackStageException(UserConstant.USER_ADD_NOTUSERROLESETADMIN);
        }

        int row = mapper.insertSelective(managerUserInfo);
        if (row > 0) {
            Integer userInfoId = managerUserInfo.getId();
            //添加用户角色
            managerUserRoleService.insertManagerPowers(roleId, userInfoId);
        }
    }

    @Override
    public ManagerUserInfoVO findManagerById(Integer managerId) {
        if (managerId == null) {
            throw new RiskBackStageException(UserConstant.USER_USERID_NOTEMPTY);
        }
        ManagerUserInfo managerUserInfo = mapper.selectByPrimaryKey(managerId);
        if (managerUserInfo == null) {
            throw new RiskBackStageException(500, UserConstant.USER_USERID_NOTEXISTS);
        }
        ManagerUserInfoVO managerUserInfoVO = new ManagerUserInfoVO();
        BeanUtils.copyProperties(managerUserInfo, managerUserInfoVO);
        managerUserInfoVO.setPassword(null);
        //查询用户的角色id放入用户实体中
        List<ManagerUserRole> managerUserRoleList = managerUserRoleService.findByManagerUserId(managerId);
        if (managerUserRoleList != null && managerUserRoleList.size() > 0) {
            ManagerUserRole managerUserRole = managerUserRoleList.get(0);
            managerUserInfoVO.setRoleId(managerUserRole.getRoleId());
        }
        return managerUserInfoVO;
    }

    @Transactional
    @Override
    public void updateManager(ManagerUserInfoVO managerUserInfoVO) {
        checkManagerUserInfoVO(managerUserInfoVO);

        Integer id = managerUserInfoVO.getId();
        if (id == null) {
            throw new RiskBackStageException(UserConstant.USER_UPDATE_USERIDNOTEMPTY);
        }
        Integer roleId = managerUserInfoVO.getRoleId();

        if (id.intValue() == UserConstant.SUPER_ADMIN_USER_ID.intValue()) {
            throw new RiskBackStageException(UserConstant.USER_UP_USERINFO_ADMINNOTSUPPORT);
        }

        if (id.intValue() == ShiroUtils.getUserId().intValue()
                && id.intValue() != UserConstant.SUPER_ADMIN_USER_ID.intValue()) {
            throw new RiskBackStageException(UserConstant.USER_UP_USERINFO_NOTSUPPORTOWNNSTATE);
        }

        if (roleId != null && roleId.intValue() == RoleConstant.SUPER_ADMIN_ROLE_ID.intValue()) {
            throw new RiskBackStageException(UserConstant.USER_UPDATE_NOROLEADMIN);
        }
        try {
            ManagerUserInfo managerUserInfo = new ManagerUserInfo();
            BeanUtils.copyProperties(managerUserInfoVO, managerUserInfo);
            managerUserInfo.setUpdateTime(new Date());
            mapper.updateByPrimaryKeySelective(managerUserInfo);
            //修改用户角色
            managerUserRoleService.saveOrUpdateUserRole(id, roleId);
        } catch (Exception e) {
            throw new RiskBackStageException(UserConstant.USER_UPDATE_INFOFAIL);
        }

    }

    private void checkManagerUserInfoVO(ManagerUserInfoVO managerUserInfoVO) {
        if (managerUserInfoVO == null) {
            throw new RiskBackStageException(UserConstant.USER_UPDATE_PARAMNOTEMPTY);
        }

        if (StringUtils.isEmpty(managerUserInfoVO.getName())) {
            throw new RiskBackStageException(UserConstant.USER_ADD_USERNAMENOTEMPTY);
        }

        String accountNumber = managerUserInfoVO.getAccountNumber();
        if (StringUtils.isEmpty(accountNumber)) {
            throw new RiskBackStageException(UserConstant.USER_ADD_ACCOUNTNOTEMPTY);
        }
        //校验登录账号是否已经存在
        // checkUserAccountName(accountNumber);

        String phone = managerUserInfoVO.getPhone();
        if (StringUtils.isEmpty(phone)) {
            throw new RiskBackStageException(UserConstant.USER_ADD_PHONENOTEMPTY);
        }
        ValidateUtil.checkPhone(phone);

        if (StringUtils.isEmpty(managerUserInfoVO.getRoleId())) {
            throw new RiskBackStageException(UserConstant.USER_ADD_USERROLENOTEMPTY);
        }
        //邮箱
        String email = managerUserInfoVO.getEmail();
        if (StringUtils.isEmpty(email)) {
            throw new RiskBackStageException(UserConstant.USER_ADD_USEREMAILNOTEMPTY);
        }
        ValidateUtil.checkEmail(email);
    }

    @Transactional
    @Override
    public void updateManagerUsers(List<Integer> managerIdList, Integer status, Integer type) {
        if (managerIdList == null || managerIdList.size() <= 0) {
            throw new RiskBackStageException(UserConstant.USER_NO_OPTIONUSERID);
        }

        if (managerIdList.contains(UserConstant.SUPER_ADMIN_USER_ID)) {
            throw new RiskBackStageException(UserConstant.USER_UP_USERINFO_ADMINNOTSUPPORT);
        }

        if (managerIdList.contains(ShiroUtils.getUserId())) {
            throw new RiskBackStageException(UserConstant.USER_UP_USERINFO_NOTSUPPORTOWNNSTATE);
        }

        for (Integer managerId : managerIdList) {
            ManagerUserInfo managerUserInfo = findManagerById(managerId);
            //type 0：修改状态 1：修改密码
            if (type != null && type.equals(1)) {
                //base64加密
                String newPassword = EncryptUtil.encrypt("123456");
                //sha256加密
                newPassword = ValidateUtil.checkPasswordForMag(newPassword);
                managerUserInfo.setPassword(newPassword);
            } else {
                managerUserInfo.setStatus(status);
            }
            mapper.updateByPrimaryKeySelective(managerUserInfo);
        }
    }

    @Transactional
    @Override
    public void updateSysCurrentUserLoginPassword(PasswordVO passwordVO, Integer userId) {
        //新密码
        String newPassword = passwordVO.getNewPassword();
        //旧密码
        String oldPassword = passwordVO.getPassword();
        //确认密码
        String confirmPassword = passwordVO.getConfirmPassword();

        if (StringUtils.isEmpty(userId)) {
            throw new RiskBackStageException(UserConstant.USER_USERID_NOTEMPTY);
        }

        if (StringUtils.isEmpty(newPassword)
                || StringUtils.isEmpty(oldPassword)
                || StringUtils.isEmpty(confirmPassword)) {
            throw new RiskBackStageException(UserConstant.PASSWORD_NOTEMPTY);
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new RiskBackStageException(UserConstant.PASSWORD_DOUBLE_DIFFERENT);
        }

        //新旧密码不能一致
        if (oldPassword.equals(newPassword)) {
            throw new RiskBackStageException(UserConstant.PASSWORD_NEWOLD_NOTSAME);
        }

        //登录密码均必须为8~16位，且为数字、字母、特殊符号中任意两种的组合
        ValidateUtil.checkPasswordComplexity(newPassword);

        //sha256加密
        oldPassword = ValidateUtil.checkPasswordForMag(oldPassword);
        //sha256加密
        newPassword = ValidateUtil.checkPasswordForMag(newPassword);

        //更新密码
        Integer result = mapper.updateUserLoginPassword(userId, oldPassword, newPassword);
        if (result > 1) {
            throw new RiskBackStageException(UserConstant.PASSWORD_UPDATE_ERROE);
        }
    }

    @Override
    public void checkUserAccountName(String accountNum) {
        ManagerUserInfo managerUserInfo = new ManagerUserInfo();
        managerUserInfo.setAccountNumber(accountNum);
        List<ManagerUserInfo> userInfoList = this.mapper.select(managerUserInfo);
        if (userInfoList != null && userInfoList.size() > 0) {
            throw new RiskBackStageException(UserConstant.USER_ACCOUNT_EXITS);
        }
    }


    private void checkManagerUserRequest(ManagerUserRequestVO managerUserRequestVO) {
        if (managerUserRequestVO == null) {
            throw new RiskBackStageException(UserConstant.USER_ADDINFO_NOTEMPTY);
        }
        if (StringUtils.isEmpty(managerUserRequestVO.getName())) {
            throw new RiskBackStageException(UserConstant.USER_ADD_USERNAMENOTEMPTY);
        }
        String accountNumber = managerUserRequestVO.getAccountNumber();
        if (StringUtils.isEmpty(accountNumber)) {
            throw new RiskBackStageException(UserConstant.USER_ADD_ACCOUNTNOTEMPTY);
        }

        if (ValidateUtil.containsChinese(accountNumber)) {
            throw new RiskBackStageException(UserConstant.USER_ADD_ACCOUNT_NOTCHINAESE);
        }

        //校验登录账号是否已经存在
        checkUserAccountName(accountNumber);

        String phone = managerUserRequestVO.getPhone();
        if (StringUtils.isEmpty(phone)) {
            throw new RiskBackStageException(UserConstant.USER_ADD_PHONENOTEMPTY);
        }

        ValidateUtil.checkPhone(phone);

        if (StringUtils.isEmpty(managerUserRequestVO.getRoleId())) {
            throw new RiskBackStageException(UserConstant.USER_ADD_USERROLENOTEMPTY);
        }
        //邮箱
        String email = managerUserRequestVO.getEmail();
        if (StringUtils.isEmpty(email)) {
            throw new RiskBackStageException(UserConstant.USER_ADD_USEREMAILNOTEMPTY);
        }

        ValidateUtil.checkEmail(email);
    }

    @Override
    public List<ManagerUserInfoVO> getManagerBeforeUserInfo(String paramValue, String methodName) {
        List<ManagerUserInfoVO> userInfoVoList = new ArrayList<>();

        if (methodName.equalsIgnoreCase("updateManager")) {
            ManagerUserInfoVO managerUserInfoVO = JSONObject.parseObject(paramValue, ManagerUserInfoVO.class);
            if (managerUserInfoVO != null) {
                Integer id = managerUserInfoVO.getId();
                ManagerUserInfoVO userInfoVO = findManagerById(id);
                userInfoVoList.add(userInfoVO);
            }
        } else if (methodName.equalsIgnoreCase("updateManagerStatusList")) {
            ManagerUserRequestVO managerUserRequestVO = JSONObject.parseObject(paramValue, ManagerUserRequestVO.class);
            if (managerUserRequestVO != null) {
                List<Integer> userIdList = managerUserRequestVO.getManagerIds();
                if (userIdList != null && userIdList.size() > 0) {
                    userIdList.stream().filter(Objects::nonNull).forEach(userId -> {
                        ManagerUserInfoVO userInfoVO = findManagerById(userId);
                        userInfoVoList.add(userInfoVO);
                    });
                }
            }
        } else {
            System.out.println("error=====>" + methodName);
        }
        return userInfoVoList;
    }

}
