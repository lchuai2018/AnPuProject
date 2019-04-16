package com.example.anpuservice.service;



import com.example.anpuservice.exception.RiskBackStageException;
import com.example.anpuservice.vo.PowerInsertInfoVO;

import java.util.List;

/**
 * @Author: lichenghuai
 * @Date: 2019/2/27 17:06
 * @Version 1.0
 */
public interface PowerInfoService {

    /**
     * 查询权限列表
     * @return
     */
    List<String> queryPowerKey();

    /**
     *添加权限信息
     * @param id
     * @param powerList
     * @param menuName
     * @return
     * @throws RiskBackStageException
     */
    List<Integer> insertPermInfo(Integer id, List<PowerInsertInfoVO> powerList, String menuName) throws RiskBackStageException;

    /**
     * 根据权限id 修改权限信息
     * @param powerId
     * @param status
     * @param powerName
     */
    void updatePowerInfoStatus(Integer powerId, Integer status, String powerName);

    /**
     *
     * 根据菜单id 菜单名称，菜单权限相关信息，状态  进行编辑菜单修改
     * @param id
     * @param menuName
     * @param powerList
     * @param status
     * @return
     */
    List<Integer> updatePowerInfo(Integer id, String menuName, List<PowerInsertInfoVO> powerList, Integer status);

    /**
     *  根据权限id查询权限信息表 id
     * @param powerIdList
     * @return
     */
    List<Integer> selectManagerPowerInfo(List<Integer> powerIdList);
}
