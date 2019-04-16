package com.example.anpuservice.controller;


import com.example.anpuservice.bo.MagOperateLogReqVO;
import com.example.anpuservice.model.ManagerOperateLog;
import com.example.anpuservice.service.OperateLogService;
import com.example.anpuservice.vo.ResponseBodyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author lichenghuai
 * @version 1.0
 * @description
 * @date 2019/2/28 17:08
 */

@RestController
@RequestMapping("/log")
@Api(value = "管理后台操作日志管理", description = "管理后台操作日志管理")
public class OperateLogController {

    @Autowired
    private OperateLogService operateLogService;

    @RequiresPermissions("system:log:select")
    @GetMapping("/list")
    @ApiOperation(value = "根据条件，查询日志列表", notes = "根据条件，查询日志列表", response = ManagerOperateLog.class)
    public ResponseBodyVO findOperateLogList(MagOperateLogReqVO magOperateLogReqVO) {
        return ResponseBodyVO.response().setData(operateLogService.findOperateLogList(magOperateLogReqVO)).build();
    }

    @GetMapping("/exportLog")
    @ApiOperation(value = "根据条件，查询日志列表导出", notes = "根据条件，查询日志列表导出")
    public ResponseBodyVO exportOperateLogList(MagOperateLogReqVO magOperateLogReqVO, HttpServletResponse response) {
        operateLogService.exportOperateLogList(magOperateLogReqVO, response);
        return ResponseBodyVO.response().build();
    }

    @GetMapping("/operate_name/list")
    @ApiOperation(value = "查询操作名称列表", notes = "查询操作名称列表")
    public ResponseBodyVO findOperateNameTreeList() {
        return ResponseBodyVO.response().setData(operateLogService.getOperateNameTreeList()).build();
    }

    @GetMapping("/operate_type/list")
    @ApiOperation(value = "查询操作类型列表", notes = "查询操作类型列表")
    public ResponseBodyVO getOperateTypeTreeList() {
        return ResponseBodyVO.response().setData(operateLogService.getOperateTypeTreeList()).build();
    }


}
