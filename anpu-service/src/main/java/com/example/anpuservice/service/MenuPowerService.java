package com.example.anpuservice.service;




import com.example.anpuservice.model.ManagerPowerInfo;
import com.example.anpuservice.vo.MenuInfoVO;
import com.example.anpuservice.vo.PowerInsertInfoVO;

import java.util.List;

/**
 * @Author: lichenghuai
 * @Date: 2019/2/28 11:58
 * @Version 1.0
 */
public interface MenuPowerService {

    /**
     * 根据父菜单ID 查询菜单信息和权限信息
     * @param parentId
     * @return
     */
      List<MenuInfoVO> selectMenuPowerList(Integer parentId);

    /**
     * 根据菜单id查询权限信息
     * @param id
     * @return
     */
     List<PowerInsertInfoVO> selectMenuPowerByMenuId(Integer id);

    /**
     * 添加菜单权限关系
     * @param id
     * @param permIdList
     * @param status
     */
    void insertMenuPerm(Integer id, List<Integer> permIdList, Integer status);

    /**
     *  根据菜单id 和权限值  查询菜单权限和权限信息  校验是否存在
     * @param powerKey
     * @param id
     * @return
     */
    List<ManagerPowerInfo> checkPowerKeyExist(String powerKey, Integer id);

    /**
     * 根据权限信息 id 将原有菜单权限关系全部置为已删除状态
     * @param id
     * @param powerId
     * @param status
     */
    void updateMenuPowerStatus(Integer id, Integer powerId, Integer status);

    /**
     * 根据菜单id 查询菜单权限表 获取权限信息id
     * @param id
     * @return
     */
    List<Integer> findPowerIdListByMenuId(Integer id);

    /**
     *  根据菜单id  查询菜单权限表 获取权限id
     * @param childId
     * @return
     */
    List<Integer> findAllPowerIdListByMenuId(Integer childId);
}
