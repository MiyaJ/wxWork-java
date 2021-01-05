package com.ezy.approval.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Caixiaowei
 * @ClassName OkHttpClientUtil.java
 * @Description OkHttp 工具类
 * @createTime 2020年06月18日 11:33:00
 */
@Slf4j
public class OkHttpClientUtil {

    public OkHttpClientUtil() {
    }

    private static int retryCount = 3;
    private static int connTimeOut = 5;
    private static int readTimeOut = 20;
    private static int writeTimeOut = 10;
    public static OkHttpClient client = null;

    static {
        // 构建 OkHttpClient 对象
        client = new OkHttpClient.Builder()
                .connectTimeout(connTimeOut, TimeUnit.SECONDS)
                .readTimeout(readTimeOut, TimeUnit.SECONDS)
                .writeTimeout(writeTimeOut, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .connectionPool(new ConnectionPool())
                .build();
    }


    /**
     * @param
     * @title 同步get 请求
     * @description
     * @author Caixiaowei
     * @updateTime 2020/6/18 12:47
     */
    public static String doGet(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
        if (!CollectionUtils.isEmpty(params)) {
            String paramStr = mapToString(params);
            url = url + "?" + paramStr;
        }
        // 构建 Request 对象
        Request.Builder requestBuilder = new Request.Builder();
        if (!CollectionUtils.isEmpty(headers)) {
            Iterator iterator = headers.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                requestBuilder.addHeader(key, headers.get(key));
            }
        }
        Request request = requestBuilder.get()
                .url(url)
                .build();

        try {
            // new 一个 Call 对象,并调用同步请求的方法 execute()
            Response response = client.newCall(request).execute();
            // 获取请求的响应
            return response.body() == null ? "" : response.body().string();
        } catch (Exception e) {
            log.error("url --->get --->exception: {}", e);
        }
        return StringUtils.EMPTY;
    }

    /**
     * @param
     * @title 同步post 请求
     * @description
     * @author Caixiaowei
     * @updateTime 2020/6/18 12:41
     */
    public static String doPost(String url, Map<String, String> headers, Map<String, Object> params) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10L, TimeUnit.SECONDS)
                .build();
        Request.Builder requestBuilder = new Request.Builder();
        if (!CollectionUtils.isEmpty(headers)) {
            Iterator iterator = headers.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                requestBuilder.addHeader(key, headers.get(key));
            }
        }
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String json = new Gson().toJson(params);
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = requestBuilder
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str = response.body().string();
            return StringUtils.isNotBlank(str) ? str : StringUtils.EMPTY;
        } catch (IOException e) {
            log.error("url --->get --->exception: {}", e);
        }
        return StringUtils.EMPTY;
    }

    /**
     * @param
     * @title 同步post 请求
     * @description
     * @author Caixiaowei
     * @updateTime 2020/6/18 12:41
     */
    public static String doPost(String url, Map<String, String> headers, JSONObject params) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10L, TimeUnit.SECONDS)
                .build();
        Request.Builder requestBuilder = new Request.Builder();
        if (!CollectionUtils.isEmpty(headers)) {
            Iterator iterator = headers.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                requestBuilder.addHeader(key, headers.get(key));
            }
        }
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String json = params.toJSONString();
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = requestBuilder
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str = response.body().string();
            return StringUtils.isNotBlank(str) ? str : StringUtils.EMPTY;
        } catch (IOException e) {
            log.error("url --->get --->exception: {}", e);
        }
        return StringUtils.EMPTY;
    }

    /**
     * @param
     * @title map 转换为 string
     * @description
     * @author Caixiaowei
     * @updateTime 2020/6/18 12:22
     */
    public static String mapToString(Map<String, String> params) {
        String sprlitStr = "&";
        String result = "";
        StringBuilder stringBuilder = new StringBuilder();
        if (!CollectionUtils.isEmpty(params)) {
            List<String> entryList = new ArrayList<>(params.keySet());
            Collections.sort(entryList);
            for (String item : entryList) {
                String value = params.get(item);
                stringBuilder.append(item).append("=").append(value).append(sprlitStr);
            }
            result = stringBuilder.toString();
            result = result.substring(0, stringBuilder.lastIndexOf(sprlitStr));
        }
        System.out.println("param str : " + result);
        return result;
    }
}
