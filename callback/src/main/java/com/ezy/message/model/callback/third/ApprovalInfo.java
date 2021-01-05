package com.ezy.message.model.callback.third;

import com.ezy.message.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName ApprovalInfo.java
 * @Description 审批信息
 * @createTime 2020年07月01日 12:29:00
 */
@Data
@XStreamAlias("ApprovalInfo")
public class ApprovalInfo implements Serializable {
    private static final long serialVersionUID = 2694470737773965218L;

    /**
     * 审批单编号，由开发者在发起申请时自定义
     */
    @XStreamAlias("ThirdNo")
    private Long thirdNo;

    /**
     * 审批模板名称
     */
    @XStreamAlias("OpenSpName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String openSpName;

    /**
     * 审批模板id
     */
    @XStreamAlias("OpenTemplateId")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String openTemplateId;

    /**
     * 申请单当前审批状态：1-审批中；2-已通过；3-已驳回；4-已取消
     */
    @XStreamAlias("OpenSpStatus")
    private Integer openSpStatus;

    /**
     * 提交申请时间, 时间戳 精确到秒
     */
    @XStreamAlias("ApplyTime")
    private Long applyTime;

    /**
     * 提交者姓名
     */
    @XStreamAlias("ApplyUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String applyUserName;

    /**
     * 提交者userid
     */
    @XStreamAlias("ApplyUserId")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String applyUserId;

    /**
     * 提交者所在部门
     */
    @XStreamAlias("ApplyUserParty")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String applyUserParty;

    /**
     * 提交者头像
     */
    @XStreamAlias("ApplyUserImage")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String applyUserImage;

    /**
     * 审批流程信息, 可以有多个审批节点
     */
    @XStreamAlias("ApprovalNodes")
    List<ApprovalNode> approvalNodes;

    /**
     * 抄送信息，可能有多个抄送人
     */
    @XStreamAlias("NotifyNodes")
    List<ApprovalNotifyNode> notifyNodes;

    /**
     * 当前审批节点：0-第一个审批节点；1-第二个审批节点…以此类推
     */
    @XStreamAlias("approverstep")
    private Integer approverstep;
}
