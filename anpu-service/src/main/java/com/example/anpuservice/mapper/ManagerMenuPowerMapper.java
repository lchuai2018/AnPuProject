package com.example.anpuservice.mapper;


import com.example.anpuservice.common.BaseMapper;
import com.example.anpuservice.model.ManagerMenuPower;
import com.example.anpuservice.model.ManagerPowerInfo;
import com.example.anpuservice.vo.MenuInfoVO;
import com.example.anpuservice.vo.PowerInsertInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ManagerMenuPowerMapper extends BaseMapper<ManagerMenuPower> {

    List<MenuInfoVO> selectMenuPowerByParentId(@Param("parentId") Integer parentId);

    List<PowerInsertInfoVO> selectMenuPowerByMenuId(@Param("menuId") Integer id);

    List<Integer> selectPowerIdListByMenuId(@Param("menuId") Integer id);

    List<ManagerPowerInfo> selectPowerIsExistByPowerKey(@Param("powerKey") String powerKey, @Param("id") Integer id);


    List<Integer> findAllPowerIdListByMenuId(@Param("menuId") Integer menuId);
}