package com.example.anpuservice.utils;


import com.example.anpuservice.constants.UserConstant;
import com.example.anpuservice.exception.RiskBackStageException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.StringUtils;

/**
 * FileName: com.iyingke.riskbackstage.utils.EncryptUtil.java
 * Author: Administrator
 * Description:
 */
public class EncryptUtil {

    private static String sn = "risk_back_b4rrdbde8"; //密钥

    /**
     * 密码加密 登录密码
     *
     * @param password 待加密密码
     * @return
     */
    public static String encrypt(String password) {
        if (password == null || "".equals(password)) {
            return password;
        }
        // Base64 编码处理
        password = encode(password);
        int snLen = sn.length();
        int[] snNum = new int[password.length()];
        // 异或运算
        for (int i = 0; i < password.length(); i++) {
            snNum[i] = password.charAt(i) ^ sn.charAt(i % snLen);
        }
        String temp;
        StringBuffer result = new StringBuffer();
        for (int k = 0; k < password.length(); k++) {
            temp = String.valueOf(snNum[k]);
            while (5 - temp.length() > 0) {
                temp = "0" + temp;
            }
            result.append(temp);
        }
        return encode(result.toString());
    }


    /**
     * 密码解密 登录密码
     *
     * @param password 待解密密码
     * @return
     */
    public static String decrypt(String password) {
        try {
            password = decode(password);
            char[] snNum = new char[password.length() / 5];
            String result = "";
            for (int i = 0, j = 0; i < password.length() / 5; i++, j++) {
                if (j == sn.length()) j = 0;
                int n = Integer.parseInt(password.substring(i * 5, i * 5 + 5));
                // 异或运算
                snNum[i] = (char) ((char) n ^ sn.charAt(j));
            }
            for (int k = 0; k < password.length() / 5; k++) {
                result += snNum[k];
            }
            return decode(result);
        } catch (Exception e) {
            throw new RiskBackStageException(UserConstant.PASSWORD_DECRYPTIONFAIL_CHECKCIPHER);
        }
    }

    /**
     * BASE64 解密
     *
     * @param str
     * @return
     */
    public static String decode(String str) {
        if (StringUtils.isEmpty(str)) return "";
        return new String(Base64.decodeBase64(str));
    }

    /**
     * BASE64 加密
     *
     * @param str
     * @return
     */
    public static String encode(String str) {
        if (StringUtils.isEmpty(str)) return "";
        return Base64.encodeBase64String(str.getBytes());
    }

    public static void main(String[] args) {
        System.out.println(EncryptUtil.encrypt("123456"));
    }
}
