package com.ezy.approval.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Caixiaowei
 * @ClassName IWXWorkService.java
 * @Description 企业微信接口
 * @createTime 2020年06月24日 16:29:00
 */
public interface IWxWorkService {

    /**
     * 获取应用接口token
     *
     * @param corpsecret 应用的凭证密钥
     * @return String 接口凭证token
     * @description
     * @author Caixiaowei
     * @updateTime 2020/6/24 16:37
     */
    String getAccessToken(String corpsecret);

    /**
     * 上传文件到企微素材库
     * @description
     * @author Caixiaowei
     * @param file: 文件信息
     * @param accessToken: 应用token
     * @updateTime 2020/7/28 10:52
     * @return
     */
    JSONObject upload(MultipartFile file, String accessToken);
}
