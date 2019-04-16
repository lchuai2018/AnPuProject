package com.example.anpuservice.controller;


import com.example.anpuservice.bo.RoleVO;
import com.example.anpuservice.constants.UserConstant;
import com.example.anpuservice.model.ManagerRoleInfo;
import com.example.anpuservice.service.RoleInfoService;
import com.example.anpuservice.service.RolePowerService;
import com.example.anpuservice.utils.annotation.ManagerLog;
import com.example.anpuservice.vo.ResponseBodyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lichenghuai
 * @version 1.0
 * @description
 * @date 2019/2/27 19:34
 */
@RestController
@RequestMapping("/roles")
@Api(value = "角色控制类", description = "角色控制类")
public class RoleController {
    @Autowired
    private RoleInfoService roleInfoService;
    @Autowired
    private RolePowerService rolePowerService;

    @RequiresPermissions("role:select")
    @GetMapping("/selectAllRole")
    @ApiOperation(value = "查看所有角色", notes = "查看所有角色", response = ManagerRoleInfo.class)
    public ResponseBodyVO selectAllRole() {
        return ResponseBodyVO.response().setData(roleInfoService.findAllRole()).build();
    }

    @GetMapping("/tree_list")
    @ApiOperation(value = "查看所有角色树结构", notes = "查看所有角色树结构", response = ManagerRoleInfo.class)
    public ResponseBodyVO selectAllRoleList(@RequestHeader(required = false) String account) {
        return ResponseBodyVO.response().setData(roleInfoService.selectAllRoleList()).build();
    }

    @RequiresPermissions("role:update")
    @ManagerLog(paramName = "0", operation = "赋予角色权限", operateType = UserConstant.OPERATE_TYPE_UPDATE)
    @PostMapping("/addRoleToMenus")
    @ApiOperation(value = "赋予角色权限", notes = "赋予角色权限")
    public ResponseBodyVO addRoleToMenus(@RequestBody RoleVO roleVO) {
        rolePowerService.addPowerToRole(roleVO);
        return ResponseBodyVO.response().build();
    }

    @RequiresPermissions("role:insert")
    @ManagerLog(paramName = "0", operation = "添加角色", operateType = UserConstant.OPERATE_TYPE_INSERT)
    @PostMapping("/insert")
    @ApiOperation(value = "添加角色", notes = "添加角色")
    public ResponseBodyVO insertRoleInfo(@RequestBody ManagerRoleInfo managerRoleInfo) {
        roleInfoService.insertManagerRole(managerRoleInfo);
        return ResponseBodyVO.response().build();
    }

    @RequiresPermissions("role:select")
    @GetMapping("/{roleId}")
    @ApiOperation(value = "根据角色ID查看角色信息", notes = "根据角色ID查看角色信息")
    public ResponseBodyVO getRoleInfoByRoleID(@ApiParam(value = "角色ID", required = true) @PathVariable("roleId") Integer roleId) {
        return ResponseBodyVO.response().setData(roleInfoService.findRoleInfoByRoleID(roleId)).build();
    }

}
