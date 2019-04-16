package com.example.anpuservice.controller;


import com.example.anpuservice.bo.ManagerUserRequestVO;
import com.example.anpuservice.constants.UserConstant;
import com.example.anpuservice.service.UserInfoService;
import com.example.anpuservice.utils.ShiroUtils;
import com.example.anpuservice.utils.annotation.ManagerLog;
import com.example.anpuservice.vo.ManagerUserInfoRespVO;
import com.example.anpuservice.vo.ManagerUserInfoVO;
import com.example.anpuservice.vo.PasswordVO;
import com.example.anpuservice.vo.ResponseBodyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author lichenghuai
 * @version 1.0
 * @description
 * @date 2019/2/26 10:51
 */
@RestController
@RequestMapping("/users")
@Api(value = "后台账号管理", description = "后台账号管理")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;

    @RequiresPermissions("users:select")
    @GetMapping("/selectAllManagers")
    @ApiOperation(value = "查看所有管理用户信息", notes = "查看所有管理用户信息", response = ManagerUserInfoRespVO.class)
    public ResponseBodyVO selectAllManagers(Integer currentPage, Integer pageSize, String name, String roleIds, Integer status) {
        return ResponseBodyVO.response().setData(userInfoService.findAllManagers(currentPage, pageSize, name, roleIds, status)).build();
    }

    @GetMapping("/{userId}")
    @ApiOperation(value = "查看用户详情", notes = "查看用户详情", response = ManagerUserInfoVO.class)
    public ResponseBodyVO selectOneManager(@PathVariable Integer userId) {
        return ResponseBodyVO.response().setData(userInfoService.findManagerById(userId)).build();
    }

    @GetMapping("/exportAllManagers")
    @ApiOperation(value = "导出管理用户信息", notes = "导出管理用户信息")
    public ResponseBodyVO exportAllManagers(Integer currentPage, Integer pageSize, String name, String roleIds, Integer status, HttpServletResponse response) {
        userInfoService.exportManagerUserExcel(currentPage, pageSize, name, roleIds, status, response);
        return ResponseBodyVO.response().build();
    }

    @RequiresPermissions("users:insert")
    @ManagerLog(paramName = "0", operation = "用户添加及角色分配", operateType = UserConstant.OPERATE_TYPE_INSERT)
    @PostMapping("/addManager")
    @ApiOperation(value = "用户添加及角色分配", notes = "用户添加及角色分配")
    public ResponseBodyVO addManager(@RequestBody ManagerUserRequestVO managerUserRequestVO) {
        userInfoService.insertManagerUserInfo(managerUserRequestVO);
        return ResponseBodyVO.response().build();
    }

    @RequiresPermissions("users:update")
    @ManagerLog(paramName = "0", operation = "修改用户信息", operateType = UserConstant.OPERATE_TYPE_UPDATE)
    @PutMapping("/updateManager")
    @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    public ResponseBodyVO updateManager(@RequestBody ManagerUserInfoVO managerUserInfoVO) {
        userInfoService.updateManager(managerUserInfoVO);
        return ResponseBodyVO.response().build();
    }

    @RequiresPermissions("users:update")
    @ManagerLog(paramName = "0", operation = "批量修改用户信息状态", operateType = UserConstant.OPERATE_TYPE_UPDATE)
    @PostMapping("/updateManagerList")
    @ApiOperation(value = "批量修改用户信息状态", notes = "批量修改用户信息状态")
    public ResponseBodyVO updateManagerStatusList(@RequestBody ManagerUserRequestVO managerUserRequestVO) {
        List<Integer> managerIds = managerUserRequestVO.getManagerIds();
        Integer type = managerUserRequestVO.getType();
        Integer status = managerUserRequestVO.getStatus();
        userInfoService.updateManagerUsers(managerIds, status, type);
        return ResponseBodyVO.response().build();
    }


    @PostMapping("/password")
    @ApiOperation(value = "修改系统当前登录的用户登录密码", notes = "修改系统当前登录的用户登录密码")
    public ResponseBodyVO updateManagerUserLoginPassword(@RequestBody PasswordVO passwordVO) {
        userInfoService.updateSysCurrentUserLoginPassword(passwordVO, ShiroUtils.getUserId());
        ShiroUtils.logout();
        return ResponseBodyVO.response().build();
    }

    @GetMapping("/check_account")
    @ApiOperation(value = "校验新增用户账号唯一性", notes = "校验新增用户账号唯一性")
    public ResponseBodyVO checkUserAccountName(@ApiParam(value = "账号名", required = true) @RequestParam(value = "accountNum") String accountNum) {
        userInfoService.checkUserAccountName(accountNum);
        return ResponseBodyVO.response().build();
    }
}
