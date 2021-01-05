package com.ezy.approval.model.message.template;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Caixiaowei
 * @ClassName MessageTemplateDTO
 * @Description
 * @createTime 2020/9/15$ 13:57$
 */
@Data
public class MessageTemplateDTO implements Serializable {
    private static final long serialVersionUID = 2213402624747578800L;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 消息类型: text-文本消息; image-图片消息; voice-语音消息; video-视频消息; file-文件消息; textcard-文本卡片消息; news-图文消息;
     *          mpnews-图文消息; markdown-markdown消息; miniprogram_notice-小程序消息; taskcard-任务卡消息
     */
    private String type;

    /**
     * 模板内容
     */
    private String content;

    /**
     * 状态: 启用/未启用
     */
    private Integer status;

    private String requestParam;

    private String response;

}
