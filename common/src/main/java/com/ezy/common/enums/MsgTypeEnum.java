package com.ezy.common.enums;


/**
 * @author Caixiaowei
 * @ClassName MsgTypeEnum.java
 * @Description 消息类型枚举
 * @createTime 2020年06月19日 13:34:00
 */
public enum MsgTypeEnum {

    /**
     * 文本消息
     */
    TEXT("文本消息", "text"),

    /**
     * 图片消息
     */
    IMAGE("图片消息", "image"),

    /**
     * 语音消息
     */
    VOICE("语音消息", "voice"),

    /**
     * 视频消息
     */
    VIDEO("视频消息", "video"),

    /**
     * 文件消息
     */
    FILE("文件消息", "file"),

    /**
     * 文本卡片消息
     */
    TEXTCARD("文本卡片消息", "textcard"),

    /**
     * 图文消息
     */
    NEWS("图文消息", "news"),

    /**
     * 图文消息
     */
    MPNEWS("图文消息", "mpnews"),

    /**
     * markdown消息
     */
    MARKDOWN("markdown消息", "markdown"),

    /**
     * 小程序消息
     */
    MINIPROGRAM_NOTICE("小程序消息", "miniprogram_notice"),

    /**
     * 任务卡片消息
     */
    TASKCARD("任务卡片消息", "taskcard");


    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;


    MsgTypeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
