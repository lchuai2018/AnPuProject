package com.example.anpuservice.constants;

/**
 * @author lichenghuai
 * @version 1.0
 * @description
 * @date 2019/2/26 15:42
 */
public class UserConstant {
    public static final String PASSWORD_NOTEMPTY = "密码不可为空！";
    public static final String PASSWORD_ISVALID = "密码包含非法字符";
    public static final String PASSWORD_FORMATERROR = "密码需为6~10位数字、字母、特殊符号中任意两种组合";
    public static final String PASSWORD_DECRYPTIONFAIL_CHECKCIPHER = "密码解密失败,请检查密文";
    public static final String PASSWORD_DOUBLE_DIFFERENT = "两次输入密码不一致";
    public static final String PASSWORD_NEWOLD_NOTSAME = "新旧密码不能一致";
    public static final String PASSWORD_UPDATE_ERROE = "修改密码出错";


    public static final String PHONE_NOTEMPTY = "手机号不可为空！";
    public static final String PHONE_LENGTH_ISVALID = "手机号长度为11位！";
    public static final String PHONE_CHECK_FAIL = "手机号码校验失败！";

    public static final String IDCARD_ISVALID = "身份证号不合法";
    public static final String IDCARD_ISEMPTY = "身份证号为空！";

    public static final String EMAIL_FORMAT_CHECKFAIL = "邮箱格式校验失败！";
    public static final String EMAIL_FORMAT_ISVALID = "邮箱格式不合法！！";

    public static final String USER_USERID_NOTEMPTY = "用户ID不可为空";
    public static final String USER_USERID_NOTEXISTS = "用户ID不存在";
    public static final String USER_UPDATE_USERIDNOTEMPTY = "修改用户信息，用户ID不可为空！";
    public static final String USER_UPDATE_NOROLEADMIN = "修改用户信息，不可将用户角色修改为超级管理员！";
    public static final String USER_UPDATE_INFOFAIL = "修改用户信息失败！";
    public static final String USER_UPDATE_PARAMNOTEMPTY = "修改用户信息，参数不可为空！";

    public static final String USER_ADD_USERNAMENOTEMPTY = "添加用户信息,用户姓名不可为空";
    public static final String USER_ADD_NOTUSERROLESETADMIN = "新增用户时，不可将用户角色设置为超级管理员！";
    public static final String USER_ADD_ACCOUNTNOTEMPTY = "添加用户信息,用户登录账号不可为空";
    public static final String USER_ADD_PHONENOTEMPTY = "添加用户信息,用户手机号不可为空";
    public static final String USER_ADD_USERROLENOTEMPTY = "添加用户信息,用户角色不可为空";
    public static final String USER_ADD_USEREMAILNOTEMPTY = "添加用户信息,用户邮箱不可为空";
    public static final String USER_ADDINFO_NOTEMPTY = "添加用户信息不可为空";
    public static final String USER_ADD_ACCOUNT_NOTCHINAESE = "添加用户信息,用户登录账号暂不支持有汉字组成";
    public static final String USER_ADD_NOROLEADMIN = "添加用户信息,用户登录账号暂不支持有汉字组成";
    public static final String USER_ADD_INFOFAIL = "添加用户失败！";

    public static final String USER_ACCOUNT_EXITS = "此登录账号已存在";
    public static final String USER_NO_OPTIONUSERID = "未选择用户ID";
    public static final String USER_UP_USERINFO_ADMINNOTSUPPORT = "修改用户信息，超级管理员信息暂不支持修改！";
    public static final String USER_UP_USERINFO_NOTSUPPORTOWNNSTATE = "修改用户信息，暂不支持修改本人状态、重置密码，请联系超级管理员进行修改！";
    public static final String USER_ACCOUNT_ORPASSWORD_ERROR = "账号或者密码错误";
    public static final String USER_ACCOUNT_FORBID_USE = "账号被禁止使用,请联系管理员！";
    public static final String USER_ACCOUNT_LOCK_ADMIN = "账号已锁定,请联系超级管理员";


    public static final String DEFAULT_LOGINPASSWORD = "123456";


    public static final String PERSIMISSIONS_KEY_ORPNAMENOTEMPTY = "权限key或者权限名称不可为空！";


    /**
     * 日志操作类型
     */
    public static final String OPERATE_TYPE_INSERT = "insert";
    public static final String OPERATE_TYPE_UPDATE = "update";
    public static final String OPERATE_TYPE_DELETE = "delete";
    public static final String OPERATE_TYPE_SELECT = "select";
    public static final String OPERATE_TYPE_OTHER = "other";

    /**
     * 产品认证账号的Authorization，
     */
    public static final String PRODUCT_AUTH_IDENTITY_KEY = "product_auth_identity_key";
    /**
     * ip 白名单配置
     */
    public static final String IP_WHITELIST_KEY = "ip_whitelist_key";


    /**
     * 超级管理员用户ID
     */
    public static final Integer SUPER_ADMIN_USER_ID = 1;


    /**
     * 登录密码均必须为6~10位，且为数字、字母、特殊符号中任意两种的组合
     */
    public static final String PASSWORD_REG = "(?!.*[\\u4E00-\\u9FA5\\s])(?!^[a-zA-Z]+$)(?!^[\\d]+$)(?!^[^a-zA-Z\\d]+$)^.{6,10}$";


}
