package com.ezy.common.model;

/**
 * @Author: Kevin Liu
 * @CreateDate: 2020/7/14 20:07
 * @Desc
 * @Version: 1.0
 */
public class TokenVerifyResult {

    private boolean isSuccess;

    private String failMsg;

    private String newToken;

    private String json;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg;
    }

    public String getNewToken() {
        return newToken;
    }

    public void setNewToken(String newToken) {
        this.newToken = newToken;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public TokenVerifyResult(String json) {
        this.isSuccess = true;
        this.json = json;
    }

    public TokenVerifyResult(boolean isSuccess, String failMsg) {
        this.isSuccess = isSuccess;
        this.failMsg = failMsg;
    }

    public TokenVerifyResult(boolean isSuccess,String failMsg, String newToken) {
        this.isSuccess = isSuccess;
        this.failMsg = failMsg;
        this.newToken = newToken;
    }

}
