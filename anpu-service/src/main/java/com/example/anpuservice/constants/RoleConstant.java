package com.example.anpuservice.constants;

/**
 * @author lichenghuai
 * @version 1.0
 * @description
 * @date 2019/2/28 9:46
 */
public class RoleConstant {
    public static final String ROLE_ROLEID_MUST = "角色ID，必传！";
    public static final String ROLE_INFO_NOTEXITS = "未找到角色信息！";
    public static final String ROLE_ADD_RARAMEMPTY = "新增角色时，角色请求体参数为空！";
    public static final String ROLE_ROLENAME_NOTEMPTY = "角色名称不可为空！";
    public static final String ROLE_ROLENAME_EXIST = "该角色名称已存在！";

    public static final String ROLE_SUPER_ADMINFOBIDUP = "超级管理员禁止修改";
    public static final String ROLE_NOTSUPPORT_OWNROLEPOWER= "暂不支持修改自己的角色权限,请联系超级管理员进行修改";
    /**
     * 超级管理员角色ID
     */
    public static final Integer SUPER_ADMIN_ROLE_ID = 1;


}
