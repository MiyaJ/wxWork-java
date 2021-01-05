package com.ezy.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author: Kevin Liu
 * @CreateDate: 2020/7/14 14:26
 * @Desc BCrypt加密工具类
 * @Version: 1.0
 */
public class BCryptUtils {

    private final static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 加密方法
     * @param source 源字符串
     * @return
     */
    public static String encodePwd(String source) {
        return encoder.encode(source);
    }

    /**
     * 是否匹配
     * @param source 源字符串
     * @param target 目标字符串
     * @return
     */
    public static boolean isMatch(String source, String target) {
        return encoder.matches(source, target);
    }

}
