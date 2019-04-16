package com.example.anpuservice.utils;


import com.example.anpuservice.constants.UserConstant;
import com.example.anpuservice.exception.RiskBackStageException;
import com.example.anpuservice.utils.EncryptUtil;
import com.example.anpuservice.utils.IdentityIdUtil;
import org.apache.shiro.crypto.hash.Sha256Hash;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * FileName: com.iyingke.riskbackstage.utils.ValidateUtil.java
 * Author: Administrator
 * Description:
 */
public class ValidateUtil {

    /**
     * 密码复杂度判断
     *
     * @return
     */
    public static String checkPasswordComplexity(String password) {
        // 校验密码
        if (password == null || "".equals(password)) {
            throw new RiskBackStageException("密码不可为空！");
        }
        // 解密
        password = EncryptUtil.decrypt(password);

        // 汉字,空格
        if (password.indexOf(" ") != -1) {
            throw new RiskBackStageException("密码包含非法字符");
        }
        if (containsChinese(password)) {
            throw new RiskBackStageException("密码包含非法字符");
        }
        //判断复杂度 登录密码必须为6~10位，且为数字、字母、特殊符号中任意两种的组合
        String reg = UserConstant.PASSWORD_REG;
        if (!password.matches(reg)) {
            throw new RiskBackStageException("密码需为6~10位数字、字母、特殊符号中任意两种组合");
        }
        return password;
    }

    /**
     * 输入的字符是否包含汉字
     *
     * @param s String
     * @return boolean
     */
    public static boolean containsChinese(String s) {
        if (null == s || "".equals(s.trim())) return false;
        for (int i = 0; i < s.length(); i++) {
            if (isChinese(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 输入的字符是否是汉字
     *
     * @param a char
     * @return boolean
     */
    private static boolean isChinese(char a) {
        int v = (int) a;
        return (v >= 19968 && v <= 171941);
    }

    /**
     * 登录密码(解密-校验-加密)  for manager
     *
     * @return
     */
    public static String checkPasswordForMag(String password) {
        // 校验密码
        if (password == null || "".equals(password)) {
            throw new RiskBackStageException("密码不可为空");
        }
        // 解密
        password = EncryptUtil.decrypt(password);
        // 判定合法性
        password = ValidateUtil.checkPasswordForRule(password);
        // 加密
        password = new Sha256Hash(password).toHex();
        return password;
    }

    /**
     * 密码合法性判定
     *
     * @return
     */
    public static String checkPasswordForRule(String password) {
        // 汉字,空格
        if (password.indexOf(" ") != -1) {
            throw new RiskBackStageException("密码包含非法字符");
        }
        if (containsChinese(password)) {
            throw new RiskBackStageException("密码包含非法字符");
        }
        return password;
    }

    /**
     * 手机号码校验
     *
     * @param phoneNumber
     */
    public static String checkPhone(String phoneNumber) {
        //前台解密
//        phoneNumber = EncryptUtil.decrypt(phoneNumber);
        if ("".equals(phoneNumber) || phoneNumber == null) {
            throw new RiskBackStageException("手机号不可为空！");
        }
        if (phoneNumber.length() != 11) {
            throw new RiskBackStageException("手机号长度为11位！");
        }
        if (!isPhoneLegal(phoneNumber)) {
            throw new RiskBackStageException("手机号码校验失败！");
        }
        return phoneNumber;
    }

    public static Boolean validatePhone(String phoneNumber) {
        if ("".equals(phoneNumber) || phoneNumber == null) {
            return false;
        }
        if (phoneNumber.length() != 11) {
            return false;
        }
        if (!isPhoneLegal(phoneNumber)) {
            return false;
        }
        return true;
    }

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        // 查询数据库的正则表达式 0,3,5-8
//        String regExp = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        String regExp = "^(1[2-9])\\d{9}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static String checkEmail(String email) {
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            if (!matcher.matches()) {
                throw new RiskBackStageException("邮箱格式不合法！");
            }
        } catch (Exception e) {
            throw new RiskBackStageException("邮箱格式校验失败！");
        }
        return email;
    }

    /**
     * 验证身份证号
     * @param IdentityId
     */
    public static String checkIdentityId(String IdentityId) {
        //前台解密
        if ("".equals(IdentityId) || IdentityId == null){
            throw new RiskBackStageException("身份证号为空！");
        }
        IdentityIdUtil iv = new IdentityIdUtil();
        if ( !iv.isValidatedAllIdcard(IdentityId) ){
            throw new RiskBackStageException("身份证号不合法");
        }
        return IdentityId.toUpperCase();
    }

    public static void main(String[] args) {
        //base64加密
        String newPassword = EncryptUtil.encrypt("123456");
        //sha256加密
        newPassword = ValidateUtil.checkPasswordForMag(newPassword);
        System.out.println(newPassword);
    }
}
