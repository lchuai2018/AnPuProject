package com.example.anpuservice.controller;


import com.example.anpuservice.bo.ManagerUserLoginVO;
import com.example.anpuservice.constants.UserConstant;
import com.example.anpuservice.service.ManagerService;
import com.example.anpuservice.utils.IpUtil;
import com.example.anpuservice.utils.ShiroUtils;
import com.example.anpuservice.utils.annotation.ManagerLog;
import com.example.anpuservice.vo.ResponseBodyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lichenghuai
 * @version 1.0
 * @description
 * @date 2019/2/28 11:56
 */


@RestController
@RequestMapping
@Api(value = "后台管理：登录、退出等", description = "后台管理：登录、退出等")
public class LoginController {
    @Autowired
    private ManagerService managerService;

    @ManagerLog(paramName = "accountNum", operation = "用户登录", operateType = UserConstant.OPERATE_TYPE_OTHER)
    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public ResponseBodyVO managerLogin(@RequestBody ManagerUserLoginVO managerUserLoginVO, HttpServletRequest request) {
        String ip = IpUtil.getRemoteIP(request);
        String token = managerService.login(managerUserLoginVO.getAccountNum(), managerUserLoginVO.getPassword(), ip);
        return ResponseBodyVO.response().setData(token).build();
    }

    @ManagerLog(operation = "退出", operateType = UserConstant.OPERATE_TYPE_OTHER)
    @GetMapping("/logout")
    @ApiOperation(value = "退出", notes = "退出")
    public ResponseBodyVO managerLoginOut() {
        managerService.logout();
        return ResponseBodyVO.response().build();
    }

    @GetMapping("/login/current_user")
    @ApiOperation(value = "当前登录用户信息", notes = "当前登录用户信息")
    public ResponseBodyVO getCurrentLoginUserInfo() {
        return ResponseBodyVO.response().setData(ShiroUtils.getUserEntity()).build();
    }

}
