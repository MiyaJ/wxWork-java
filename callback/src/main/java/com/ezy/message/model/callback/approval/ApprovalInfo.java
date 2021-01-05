package com.ezy.message.model.callback.approval;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName ApprovalInfo2.java
 * @Description 审批详情
 * @createTime 2020年07月07日 16:17:00
 */
@Data
@XStreamAlias("ApprovalInfo")
public class ApprovalInfo implements Serializable {
    private static final long serialVersionUID = -8756499340176598836L;

    /**
     * 审批单号
     */
    @XStreamAlias("SpNo")
    private String spNo;


    /**
     * 审批申请类型名称（审批模板名称）
     */
    @XStreamAlias("SpName")
    private String spName;

    /**
     * 申请单状态：1-审批中；2-已通过；3-已驳回；4-已撤销；6-通过后撤销；7-已删除；10-已支付
     */
    @XStreamAlias("SpStatus")
    private Integer spStatus;

    /**
     * 审批模板id。可在“获取审批申请详情”、“审批状态变化回调通知”中获得，也可在审批模板的模板编辑页面链接中获得。
     */
    @XStreamAlias("TemplateId")
    private String templateId;

    /**
     * 审批申请提交时间,Unix时间戳 10位
     */
    @XStreamAlias("ApplyTime")
    private Integer applyTime;

    /**
     *
     */
    @XStreamAlias("Applyer")
    private Applyer Applyer;

    /**
     * 审批流程信息，可能有多个审批节点。
     */
    @XStreamImplicit(itemFieldName="SpRecord")
    private List<SpRecord> spRecord;

    /**
     * 抄送信息，可能有多个抄送节点
     */
    @XStreamImplicit(itemFieldName="Notifyer")
    private List<Notifyer> notifyer;

    /**
     * 审批申请备注信息，可能有多个备注节点
     */
    @XStreamImplicit(itemFieldName="Comments")
    private List<Comments> comments;

    /**
     * 审批申请状态变化类型：1-提单；2-同意；3-驳回；4-转审；5-催办；6-撤销；8-通过后撤销；10-添加备注
     */
    @XStreamAlias("StatuChangeEvent")
    private Integer statuChangeEvent;

}
