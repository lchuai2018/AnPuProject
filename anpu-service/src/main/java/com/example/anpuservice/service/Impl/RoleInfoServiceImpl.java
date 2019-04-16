package com.example.anpuservice.service.Impl;


import com.alibaba.fastjson.JSONObject;
import com.example.anpuservice.bo.RoleVO;
import com.example.anpuservice.constants.RoleConstant;
import com.example.anpuservice.exception.RiskBackStageException;
import com.example.anpuservice.mapper.ManagerRoleInfoMapper;
import com.example.anpuservice.model.ManagerRoleInfo;
import com.example.anpuservice.service.RoleInfoService;
import com.example.anpuservice.service.RolePowerService;
import com.example.anpuservice.utils.ShiroUtils;
import com.example.anpuservice.vo.ManagerRoleInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author lichenghuai
 * @version 1.0
 * @description
 * @date 2019/2/28 9:39
 */

@Slf4j
@Service
public class RoleInfoServiceImpl extends BaseServiceImpl<ManagerRoleInfoMapper, ManagerRoleInfo>  implements RoleInfoService {

    @Autowired
    private RolePowerService managerRolePowerService;

    @Override
    public List<ManagerRoleInfo> findAllRole() {
        Example example = new Example(ManagerRoleInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", 1);
        criteria.andNotEqualTo("id", RoleConstant.SUPER_ADMIN_ROLE_ID);
        return mapper.selectByExample(example);
    }


    @Override
    public ManagerRoleInfo findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }
    @Override
    public ManagerRoleInfoVO findRoleInfoByRoleID(Integer roleId) {
        if (roleId == null) {
            throw new RiskBackStageException(RoleConstant.ROLE_ROLEID_MUST);
        }
        ManagerRoleInfo managerRoleInfo = mapper.selectByPrimaryKey(roleId);
        if (managerRoleInfo == null) {
            throw new RiskBackStageException(RoleConstant.ROLE_INFO_NOTEXITS);
        }
        ManagerRoleInfoVO managerRoleInfoVO = new ManagerRoleInfoVO();
        BeanUtils.copyProperties(managerRoleInfo, managerRoleInfoVO);
        //查询角色对应的菜单权限
        List<Integer> menuIdList = managerRolePowerService.queryPowerIdList(roleId);
        managerRoleInfoVO.setMenuIdList(menuIdList);
        return managerRoleInfoVO;
    }

    @Override
    public List<ManagerRoleInfo> selectAllRoleList() {
        Example example = new Example(ManagerRoleInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", 1);
        return mapper.selectByExample(example);
    }


    @Transactional
    @Override
    public void insertManagerRole(ManagerRoleInfo managerRoleInfo) {
        if (managerRoleInfo == null){
            throw new RiskBackStageException(RoleConstant.ROLE_ADD_RARAMEMPTY);
        }
        String roleName = managerRoleInfo.getRoleName();
        if (StringUtils.isEmpty(roleName)){
            throw new RiskBackStageException(RoleConstant.ROLE_ROLENAME_NOTEMPTY);
        }
        Example example = new Example(ManagerRoleInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleName", roleName);
        List<ManagerRoleInfo> roleInfoList = mapper.selectByExample(example);
        if (roleInfoList != null && roleInfoList.size() > 0){
            throw new RiskBackStageException(RoleConstant.ROLE_ROLENAME_EXIST);
        }

        managerRoleInfo.setCreatedManagerId(ShiroUtils.getUserId());
        mapper.insertSelective(managerRoleInfo);
    }

    @Override
    public ManagerRoleInfo getRoleBeforeInfo(String paramValue) {
        RoleVO roleVO = JSONObject.parseObject(paramValue, RoleVO.class);
        if (roleVO != null) {
            return findRoleInfoByRoleID(roleVO.getRoleId());
        }
        return null;
    }

}
