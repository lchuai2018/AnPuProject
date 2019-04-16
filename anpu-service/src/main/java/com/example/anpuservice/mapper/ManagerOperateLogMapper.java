package com.example.anpuservice.mapper;


import com.example.anpuservice.bo.MagOperateLogReqVO;
import com.example.anpuservice.common.BaseMapper;
import com.example.anpuservice.model.ManagerOperateLog;
import com.example.anpuservice.vo.TreeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ManagerOperateLogMapper extends BaseMapper<ManagerOperateLog> {
    List<TreeVO> getOperationNameList();
    List<ManagerOperateLog> selectOperateLogList(@Param("magOperateLogReqVO") MagOperateLogReqVO magOperateLogReqVO);

}