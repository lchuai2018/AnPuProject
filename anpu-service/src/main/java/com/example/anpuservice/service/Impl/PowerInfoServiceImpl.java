package com.example.anpuservice.service.Impl;


import com.example.anpuservice.constants.UserConstant;
import com.example.anpuservice.exception.RiskBackStageException;
import com.example.anpuservice.mapper.ManagerPowerInfoMapper;
import com.example.anpuservice.model.ManagerPowerInfo;
import com.example.anpuservice.model.ManagerRolePower;
import com.example.anpuservice.service.MenuPowerService;
import com.example.anpuservice.service.PowerInfoService;
import com.example.anpuservice.service.RolePowerService;
import com.example.anpuservice.utils.PinYinUtils;
import com.example.anpuservice.utils.ShiroUtils;
import com.example.anpuservice.vo.PowerInsertInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @author lichenghuai
 * @version 1.0
 * @description
 * @date 2019/2/27 17:06
 */
@Slf4j
@Service
public class PowerInfoServiceImpl implements PowerInfoService {
    @Autowired
    private ManagerPowerInfoMapper managerPowerInfoMapper;
    @Autowired
    private RolePowerService managerRolePowerService;
    @Autowired
    private MenuPowerService managerMenuPowerService;
    @Override
    public List<String> queryPowerKey() {
        //用户权限列表
        return managerPowerInfoMapper.queryPowerKey();
    }


    @Transactional
    @Override
    public List<Integer> insertPermInfo(Integer id, List<PowerInsertInfoVO> powerList, String menuName) throws RiskBackStageException {
        List<Integer> permIdList = new ArrayList<>();
        if (powerList != null && powerList.size() > 0) {
            Set<PowerInsertInfoVO> voSet = new HashSet<>(powerList);
            voSet.stream().filter(Objects::nonNull).forEach(powerInsertInfoVO -> {
                if (!StringUtils.isEmpty(powerInsertInfoVO.getPowerKey()) && !StringUtils.isEmpty(powerInsertInfoVO.getPowerName())) {
                    //校验权限key是否已存在
                    List<ManagerPowerInfo> managerPowerInfoList = managerMenuPowerService.checkPowerKeyExist(powerInsertInfoVO.getPowerKey(), null);
                    if (managerPowerInfoList != null && managerPowerInfoList.size() > 0) {
                        throw new RiskBackStageException(String.format("该权限key:%s已存在，且是其他菜单的权限key！", powerInsertInfoVO.getPowerKey()));
                    }
                    ManagerPowerInfo managerPowerInfo = new ManagerPowerInfo();
                    managerPowerInfo.setPowerKey(powerInsertInfoVO.getPowerKey());
                    managerPowerInfo.setPowerName(powerInsertInfoVO.getPowerName());
                    managerPowerInfo.setStatus(1);
                    managerPowerInfo.setCreateUserId(ShiroUtils.getUserId());
                    managerPowerInfo.setCreateTime(new Date());
                    managerPowerInfo.setUpdateTime(new Date());
                    managerPowerInfoMapper.insertSelective(managerPowerInfo);

                    permIdList.add(managerPowerInfo.getId());
                } else {
                    throw new RiskBackStageException(UserConstant.PERSIMISSIONS_KEY_ORPNAMENOTEMPTY);
                }

            });
        } else {
            ManagerPowerInfo managerPowerInfo = new ManagerPowerInfo();
            managerPowerInfo.setPowerName(menuName + "全部权限");
            managerPowerInfo.setPowerKey(PinYinUtils.toHanyuPinyin(menuName) + ":all");
            managerPowerInfo.setStatus(1);
            managerPowerInfo.setCreateUserId(ShiroUtils.getUserId());
            managerPowerInfo.setCreateTime(new Date());
            managerPowerInfo.setUpdateTime(new Date());
            managerPowerInfoMapper.insertSelective(managerPowerInfo);

            permIdList.add(managerPowerInfo.getId());
        }
        return permIdList;
    }

    /**
     * 查找不是all权限的id
     *
     * @param powerIdList
     * @return
     */
    @Override
    public List<Integer> selectManagerPowerInfo(List<Integer> powerIdList) {
        return managerPowerInfoMapper.selectManagerNotAllPowerInfo(powerIdList);
    }
    @Transactional
    @Override
    public List<Integer> updatePowerInfo(Integer id, String menuName, List<PowerInsertInfoVO> powerList, Integer status) {
        List<Integer> powerIdList = managerMenuPowerService.findPowerIdListByMenuId(id);

        List<ManagerRolePower> rolePowerList = new ArrayList<>();
        if (powerIdList != null && powerIdList.size() > 0) {
            //如果存在，先把之前的有效的权限全部置为删除，也把有效的权限菜单置为删除，也把有效的权限角色置为删除
            powerIdList.stream().filter(Objects::nonNull).forEach(powerId -> {
                // 首先将原有菜单权限关系全部置为已删除状态
                managerMenuPowerService.updateMenuPowerStatus(id, powerId, 0);
                // 使权限置为删除
                this.updatePowerInfoStatus(powerId, 0, null);
            });

            // 查询有效的角色权限 将角色权限置为删除
            rolePowerList = managerRolePowerService.findManagerRolePower(powerIdList);
            if (rolePowerList != null && rolePowerList.size() > 0) {
                //根据权限信息id 查询有效的角色权限 信息 将角色权限置为删除
                managerRolePowerService.updateManagerRolePowerStatus(rolePowerList, 0);
            }
        }

        List<Integer> permIdList = new ArrayList<>();
        if (powerList != null && powerList.size() > 0) {

            powerList.stream().filter(Objects::nonNull).forEach(powerInsertInfoVO -> {
                String powerKey = powerInsertInfoVO.getPowerKey();

                if (StringUtils.isEmpty(powerKey) || StringUtils.isEmpty(powerInsertInfoVO.getPowerName())) {
                    throw new RiskBackStageException(UserConstant.PERSIMISSIONS_KEY_ORPNAMENOTEMPTY);
                }

                //校验权限key是否已存在
                List<ManagerPowerInfo> managerPowerInfoList = managerMenuPowerService.checkPowerKeyExist(powerKey, id);
                if (managerPowerInfoList != null && managerPowerInfoList.size() > 0) {
                    throw new RiskBackStageException(String.format("该权限key:【%s】已存在，且是其他菜单的权限key！", powerKey));
                }

                Example powerExample = new Example(ManagerPowerInfo.class);
                Example.Criteria powerCriteria = powerExample.createCriteria();
                powerCriteria.andEqualTo("powerKey", powerKey);
                ManagerPowerInfo managerPowerInfoResult = managerPowerInfoMapper.selectOneByExample(powerExample);
                if (managerPowerInfoResult != null) {
                    Integer powerId = managerPowerInfoResult.getId();
                    // 首先将原有菜单权限关系全部置为启用状态
                    managerMenuPowerService.updateMenuPowerStatus(id, powerId, status);
                    // 使权限置为启用
                    this.updatePowerInfoStatus(powerId, status, powerInsertInfoVO.getPowerName());
                    // 将角色权限置为启用 只启用当前key存在的对应的角色权限的关系，其他剔除掉的key的权限和角色关系不启用
                    managerRolePowerService.updateRolePowerStatus(powerId, status);
                } else {
                    ManagerPowerInfo managerPowerInfo = new ManagerPowerInfo();
                    managerPowerInfo.setPowerKey(powerInsertInfoVO.getPowerKey());
                    managerPowerInfo.setPowerName(powerInsertInfoVO.getPowerName());
                    managerPowerInfo.setStatus(status);
                    managerPowerInfo.setCreateUserId(ShiroUtils.getUserId());
                    managerPowerInfo.setUpdateTime(new Date());
                    managerPowerInfo.setCreateTime(new Date());
                    managerPowerInfoMapper.insertSelective(managerPowerInfo);

                    permIdList.add(managerPowerInfo.getId());
                }
            });
        } else {
            Example powerExample = new Example(ManagerPowerInfo.class);
            Example.Criteria powerCriteria = powerExample.createCriteria();
            powerCriteria.andEqualTo("powerName", menuName + "全部权限");
            ManagerPowerInfo managerPowerInfoResult = managerPowerInfoMapper.selectOneByExample(powerExample);
            if (managerPowerInfoResult != null) {
                Integer powerId = managerPowerInfoResult.getId();
                // 首先将原有菜单权限关系全部置为启用状态
                managerMenuPowerService.updateMenuPowerStatus(id, powerId, status);
                // 使权限置为启用
                this.updatePowerInfoStatus(powerId, status, null);
                // 将角色权限置为启用
                managerRolePowerService.updateRolePowerStatus(powerId, status);
            } else {
                ManagerPowerInfo managerPowerInfo = new ManagerPowerInfo();
                managerPowerInfo.setPowerKey(PinYinUtils.toHanyuPinyin(menuName) + ":all");
                managerPowerInfo.setPowerName(menuName + "全部权限");
                managerPowerInfo.setStatus(status);
                managerPowerInfo.setCreateUserId(ShiroUtils.getUserId());
                managerPowerInfo.setCreateTime(new Date());
                managerPowerInfo.setUpdateTime(new Date());
                managerPowerInfoMapper.insertSelective(managerPowerInfo);

                permIdList.add(managerPowerInfo.getId());
            }
        }
        return permIdList;
    }



    @Transactional
    @Override
    public void updatePowerInfoStatus(Integer powerId, Integer status, String powerName) {
        Example delPowerExample = new Example(ManagerPowerInfo.class);
        Example.Criteria delPowerCriteria = delPowerExample.createCriteria();
        delPowerCriteria.andEqualTo("id", powerId);
        ManagerPowerInfo managerPowerInfo = new ManagerPowerInfo();
        managerPowerInfo.setStatus(status);
        if (StringUtils.isNotEmpty(powerName)) {
            managerPowerInfo.setPowerName(powerName);
        }
        managerPowerInfoMapper.updateByExampleSelective(managerPowerInfo, delPowerExample);
    }
}
