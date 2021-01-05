package com.ezy.approval.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.ezy.approval.service.IWxWorkService;
import com.ezy.approval.utils.OkHttpClientUtil;
import com.ezy.common.constants.CommonConstan;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;

/**
 * @author Caixiaowei
 * @ClassName WxWorkServiceImpl.java
 * @Description 企业微信接口实现
 * @createTime 2020年06月24日 16:30:00
 */
@Service
@Slf4j
public class WxWorkServiceImpl implements IWxWorkService {

    @Value("${qywx.corpid}")
    private String CORPID;

    private String ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";

    /**
     * 获取应用接口token
     *
     * @param corpsecret 应用的凭证密钥
     * @return String 接口凭证token
     * @description
     * @author Caixiaowei
     * @updateTime 2020/6/24 16:37
     */
    @Override
    public String getAccessToken(String corpsecret) {
        String accessToken = StringUtils.EMPTY;
        Map<String, String> params = Maps.newHashMap();
        params.put("corpid", this.CORPID);
        params.put("corpsecret", corpsecret);
        try {
            String result = OkHttpClientUtil.doGet(ACCESS_TOKEN_URL, null, params);
            JSONObject json = JSONObject.parseObject(result);
            if (json.getInteger("errcode") != null && json.getInteger("errcode") == 0) {
                accessToken = json.getString("access_token");
            }
        } catch (Exception e) {
            log.error("获取应用access_token 异常--->{}", e);
        }
        return accessToken;
    }

    /**
     * 上传文件到企微素材库
     *
     * @param file : 文件信息
     * @return 上传结果
     * @description 暂时只支持图片与文件
     * @author Caixiaowei
     * @updateTime 2020/7/28 10:52
     */
    @Override
    public JSONObject upload(MultipartFile file, String accessToken) {

        String uploadUrl = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

        String[] split = file.getOriginalFilename().split("\\.");
        String suffixName = split[split.length-1];

        // 校验文件
        String fileType = this.checkFile(file.getSize(), suffixName);
        if (StrUtil.isEmpty(fileType)) {
            log.error("文件类型不支持， 请重新选择");
            return null;
        }
        String replacedUrl = uploadUrl
                .replace("ACCESS_TOKEN", accessToken)
                .replace("TYPE", fileType);

        // 上传素材
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("", file.getOriginalFilename(),
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                    input2byte(file.getResource().getInputStream())))
                    .build();
            Request request = new Request.Builder()
                    .url(replacedUrl)
                    .method("POST", body)
                    .build();
            Response response = client.newCall(request).execute();
            String str = response.body().string();
            return StringUtils.isNotBlank(str) ? JSONObject.parseObject(str) : null;
        } catch (Exception e) {

        }

        return null;
    }

    /**
     * 文件是否符合微信规范
     * @param size
     * @param suffixName
     * @return String: 文件类型
     */
    public String checkFile(long size, String suffixName) {
        String result = StrUtil.EMPTY;

        if (size > CommonConstan.MAX_SIZE) {
            log.info("文件太大，文件的大小最大为2M，请重新上传!");
        }

        if (Arrays.asList(CommonConstan.SUPPORTED_IMG_FORMAT.split(",")).contains(suffixName)) {
            result = "image";
        } else if (Arrays.asList(CommonConstan.SUPPORTED_FILE_FORMAT.split(",")).contains(suffixName)) {
            result = "file";
        }
        return result;
    }

    private static final byte[] input2byte(InputStream inStream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }
}
