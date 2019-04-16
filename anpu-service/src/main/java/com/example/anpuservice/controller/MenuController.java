package com.example.anpuservice.controller;



import com.example.anpuservice.constants.UserConstant;
import com.example.anpuservice.service.MagMenuInfoService;
import com.example.anpuservice.service.ManagerMenuInfoService;
import com.example.anpuservice.utils.ShiroUtils;
import com.example.anpuservice.utils.annotation.ManagerLog;
import com.example.anpuservice.vo.ManagerMenuInfoVO;
import com.example.anpuservice.vo.MenuPowerVO;
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
@Api(description = "后台菜单管理")
@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private ManagerMenuInfoService managerMenuInfoService;
    @Autowired
    private MagMenuInfoService magMenuInfoService;

    @GetMapping("/login_menu/list")
    @ApiOperation(value = "查看当前登录的权限菜单", notes = "查看当前登录的权限菜单", response = MenuPowerVO.class)
    public ResponseBodyVO selectCurrentLoginMenuList() {
        return ResponseBodyVO.response().setData(managerMenuInfoService.selectCurrentLoginMenuList(ShiroUtils.getUserId())).build();
    }

    @GetMapping(value = "/tree")
    @ApiOperation(value = "所有菜单树结构", notes = "所有菜单树结构")
    public ResponseBodyVO menuTree() {
        return ResponseBodyVO.response().setData(managerMenuInfoService.findMenuTree()).build();
    }

    @GetMapping("/parentMenu_tree")
    @ApiOperation(value = "父菜单列表", notes = "父菜单列表")
    public ResponseBodyVO parentMenuTreeList() {
        return ResponseBodyVO.response().setData(managerMenuInfoService.findParentMenuTreeList()).build();
    }

   // @RequiresPermissions("menu:select")
    @GetMapping(value = "/list")
    @ApiOperation(value = "菜单列表", notes = "菜单列表")
    public ResponseBodyVO selectManagerMenuList(@ApiParam(value = "页码", required = true) @RequestParam(value = "currentPage") Integer currentPage,
                                                @ApiParam(value = "每页的条数", required = true) @RequestParam(value = "pageSize") Integer pageSize,
                                                @ApiParam(value = "菜单名称") @RequestParam(value = "menuName", required = false) String menuName,
                                                @ApiParam(value = "父菜单ID") @RequestParam(value = "parentMenuId", required = false) String parentMenuId,
                                                @ApiParam(value = "菜单类型 0目录，1菜单") @RequestParam(value = "menuType", required = false) Integer menuType,
                                                @ApiParam(value = "状态 0：未使用 1：已使用") @RequestParam(value = "status", required = false) Integer status) {
        return ResponseBodyVO.response().setData(managerMenuInfoService.selectManagerMenuList(currentPage, pageSize, menuName, parentMenuId, menuType, status)).build();
    }

    @RequiresPermissions("menu:select")
    @GetMapping(value = "/id")
    @ApiOperation(value = "根据ID查找菜单信息详情", notes = "根据ID查找菜单信息详情")
    public ResponseBodyVO getManagerMenuID(@ApiParam(value = "菜单ID", required = true) @RequestParam(value = "id") Integer id) {
        return ResponseBodyVO.response().setData(managerMenuInfoService.getManagerMenuID(id)).build();
    }

    @RequiresPermissions("menu:insert")
    @ManagerLog(paramName = "0", operation = "添加菜单", operateType = UserConstant.OPERATE_TYPE_INSERT)
    @PostMapping(value = "/insert")
    @ApiOperation(value = "添加菜单", notes = "添加菜单")
    public ResponseBodyVO insertMenuInfo(@RequestBody ManagerMenuInfoVO managerMenuInfoVO) {
        magMenuInfoService.insertMenuInfo(managerMenuInfoVO);
        return ResponseBodyVO.response().build();
    }

    @RequiresPermissions("menu:update")
    @ManagerLog(paramName = "0", operation = "编辑菜单", operateType = UserConstant.OPERATE_TYPE_UPDATE)
    @PutMapping(value = "/updateMenu")
    @ApiOperation(value = "编辑菜单", notes = "编辑菜单")
    public ResponseBodyVO updateMenuInfo(@RequestBody ManagerMenuInfoVO managerMenuInfoVO) {
        magMenuInfoService.updateMenuInfo(managerMenuInfoVO);
        return ResponseBodyVO.response().build();
    }


    @RequiresPermissions("menu:update")
    @ManagerLog(paramName = "0", operation = "修改菜单状态", operateType = UserConstant.OPERATE_TYPE_UPDATE)
    @PutMapping(value = "/updateMenuStatus")
    @ApiOperation(value = "修改菜单状态", notes = "修改菜单状态")
    public ResponseBodyVO updateMenuInfoStatus(@RequestBody ManagerMenuInfoVO managerMenuInfoVO) {
        magMenuInfoService.updateMenuStatus(managerMenuInfoVO.getId(), managerMenuInfoVO.getStatus());
        return ResponseBodyVO.response().build();
    }
}
