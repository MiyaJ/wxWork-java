package com.ezy.common.constants;

/**
 * @author Caixiaowei
 * @ClassName RedisConstans.java
 * @Description redis 常量
 * @createTime 2020年06月18日 11:26:00
 */
public class RedisConstans {

    public RedisConstans() {
    }

    public static final Long QYWX_ACCESS_TOKEN_EXPIRATION = 7200L;
    /**
     * 审批应用接口token
     */
    public static final String QYWX_ACCESS_TOKEN_KEY_APPROVAL = "qywx:access_token:approval";
    /**
     * 审批应用接口token
     */
    public static final String QYWX_ACCESS_TOKEN_KEY_MESSAGE = "qywx:access_token:message";

    /**
     * 审批超时单据编号set
     */
    public static final String APPROVAL_TIME_OUT_NO = "approval:timeOut:no";

    /**
     * 审批回调通知重试
     */
    public static final String APPROVAL_CALLBACK_RETRY= "approval:callback:retry";

    /**
     * 审批MQ 消费失败重试
     */
    public static final String APPROVAL_COMSUME_RETRY= "approval:consume:retry";

    /**
     * 审批失败次数
     */
    public static final String APPROVAL_CONSUMER_FAIL_COUNT_PREFIX = "approval:consumer:fail:count:";
}
