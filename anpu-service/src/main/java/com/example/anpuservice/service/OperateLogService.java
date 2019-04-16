package com.example.anpuservice.service;


import com.example.anpuservice.bo.MagOperateLogReqVO;
import com.example.anpuservice.model.ManagerOperateLog;
import com.example.anpuservice.vo.TreeVO;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author: lichenghuai
 * @Date: 2019/2/28 17:09
 * @Version 1.0
 */
public interface OperateLogService {
    /**
     * 添加日志信息
     * @param managerOperateLog
     */
    void insertSelective(ManagerOperateLog managerOperateLog);

    /**
     *
     * 记录修改前的值
     * @param managerOperateLog
     * @param paramValue
     * @param methodName
     */
    void buildManagerOperateLog(ManagerOperateLog managerOperateLog, String paramValue, String methodName);

    void updateManagerOperateLog(String logNumber, Map<String, Object> responseMap);


    /**
     *根据条件，查询日志列表
     * @param magOperateLogReqVO
     * @return
     */
    PageInfo<ManagerOperateLog> findOperateLogList(MagOperateLogReqVO magOperateLogReqVO);

    /**
     * 查询操作名称列表
     * @return
     */
    List<TreeVO> getOperateNameTreeList();


    /**
     *查询操作类型列表
     * @return
     */
    List<TreeVO> getOperateTypeTreeList();


    /**
     *根据条件，查询日志列表导出
     * @param magOperateLogReqVO
     * @param response
     */
    void exportOperateLogList(MagOperateLogReqVO magOperateLogReqVO, HttpServletResponse response);
}
