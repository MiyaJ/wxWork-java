package com.ezy.message.model.callback.third;

import com.ezy.message.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName AppravalCallback.java
 * @Description 审批事件回调消息
 * @createTime 2020年07月01日 11:31:00
 */
@Data
@Slf4j
@XStreamAlias("xml")
public class AppravalCallbackMessage implements Serializable {
    private static final long serialVersionUID = 4245680641277774844L;

    /**
     * 接收方企业Corpid
     */
    @XStreamAlias("ToUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String toUserName;

    /**
     * FromUserName
     */
    @XStreamAlias("FromUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String fromUserName;

    /**
     * 消息发送时间, 时间戳, 精确到秒
     */
    @XStreamAlias("CreateTime")
    private Long createTime;

    /**
     * 消息类型
     */
    @XStreamAlias("MsgType")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String msgType;

    /**
     * 事件名称：open_approval_change
     */
    @XStreamAlias("Event")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String event;

    /**
     * 企业应用的id，整型
     */
    @XStreamAlias("AgentID")
    private Integer agentId;

    @XStreamAlias("ApprovalInfo")
    private ApprovalInfo approvalInfo;
}
