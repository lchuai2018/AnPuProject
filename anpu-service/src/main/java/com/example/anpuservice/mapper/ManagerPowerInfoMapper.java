package com.example.anpuservice.mapper;


import com.example.anpuservice.common.BaseMapper;
import com.example.anpuservice.model.ManagerPowerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ManagerPowerInfoMapper extends BaseMapper<ManagerPowerInfo> {
    /**
     * 查询所有的权限信息
     * @return
     */
    List<String> queryPowerKey();

    /**根据权限id 查询
     *
     * @param powerIdList
     * @return
     */
    List<Integer> selectManagerNotAllPowerInfo(@Param("powerIdList") List<Integer> powerIdList);
}