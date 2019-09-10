package com.chanshiyu.util;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

/**
 * @author shiyu
 * @date 2019/9/7 20:15
 * @Description: MD5 工具类
 */
public class MD5Utils {

    /**
     * 对字符串进行md5加密
     */
    public static String getMD5Str(String strValue) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String newstr = Base64.encodeBase64String(md5.digest(strValue.getBytes()));
        return newstr;
    }

}
