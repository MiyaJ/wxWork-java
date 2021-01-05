package com.ezy.message.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: jinweiwei
 * @date: 2019/6/19
 * Time: 14:56
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class QywxCallBackUtils {

    public static String getStringInputstream(HttpServletRequest request) {
        StringBuffer strb = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String str = null;
            while (null != (str = reader.readLine())) {
                strb.append(str);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strb.toString();
    }
}