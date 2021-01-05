package com.ezy.common.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: zehao.zhang
 * @CreateDate: 2020/06/30 14:48
 * @Desc 处理模糊查询字段
 * @Version: 1.0
 */
@UtilityClass
public class SqlUtils {

    public static String likeOrNull(String input) {
        if (StringUtils.isBlank(input)) {
            return null;
        } else {
            String escape = input
                    .replaceAll("\\\\", "\\\\")
                    .replaceAll("%", "\\%")
                    .replaceAll("_", "\\_");
            return "%" + String.join("%", escape.split(" ")) + "%";
        }
    }
}
