package com.ezy.message.model.callback.approval;

import com.ezy.message.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName Xml.java
 * @Description 审批申请状态变化回调通知
 * @createTime 2020年07月08日 10:44:00
 */
@Data
@XStreamAlias("xml")
public class ApprovalStatuChangeEvent implements Serializable {
    private static final long serialVersionUID = -3688915988159711276L;

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
