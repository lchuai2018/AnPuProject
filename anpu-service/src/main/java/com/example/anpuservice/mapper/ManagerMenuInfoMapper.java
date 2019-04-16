package com.example.anpuservice.mapper;


import com.example.anpuservice.common.BaseMapper;
import com.example.anpuservice.model.ManagerMenuInfo;
import com.example.anpuservice.vo.MenuInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ManagerMenuInfoMapper extends BaseMapper<ManagerMenuInfo> {
    /**
     *  根据父级菜单id 查询菜单信息
     * @param parentId
     * @return
     */
    List<MenuInfoVO> queryListByParentId(@Param("parentId") Integer parentId);

    /**
     * 根据已知子菜单id查询父菜单id
     * @param menuIdList
     * @return
     */
    List<Integer> selectParentIdList(@Param("idList") List<Integer> menuIdList);

    /**
     *  根据菜单名称，菜单id  菜单类型 状态查询
     * @param menuName
     * @param parentMenuId
     * @param menuType
     * @param status
     * @return
     */
    List<ManagerMenuInfo> selectMenuInfoList(@Param("menuName") String menuName, @Param("parentMenuId") String parentMenuId, @Param("menuType") Integer menuType, @Param("status") Integer status);
}