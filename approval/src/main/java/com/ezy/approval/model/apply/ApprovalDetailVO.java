package com.ezy.approval.model.apply;

import com.ezy.approval.entity.ApprovalSpComment;
import com.ezy.approval.entity.ApprovalSpRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Caixiaowei
 * @ClassName ApprovalDetail.java
 * @Description 审批单据详情
 * @createTime 2020年07月31日 13:37:00
 */
@Data
public class ApprovalDetailVO implements Serializable {
    private static final long serialVersionUID = -6513046092016256513L;

    /**
     * 审批编号
     */
    private String spNo;

    /**
     * 业务标识
     */
    private String systemCode;

    /**
     * 审批模板id
     */
    private String templateId;

    /**
     * 审批申请类型(审批模板名称)
     */
    private String spName;

    /**
     * 审批状态 1-审批中；2-已通过；3-已驳回；4-已撤销；6-通过后撤销；7-已删除；10-已支付
     */
    private Integer status;

    /**
     * 申请提交时间 审批申请提交时间,Unix时间戳
     */
    private Long applyTime;

    /**
     * 申请人员工id
     */
    private Long empId;

    /**
     * 申请人员工姓名
     */
    private String empName;

    /**
     * 申请人微信用户id 申请人userid
     */
    private String wxUserId;

    /**
     * 申请人wx部门id
     */
    private String wxPartyId;

    /**
     * 审批数据
     */
    @JsonIgnore
    private String applyData;

    /**
     * 申请失败原因
     */
    private String errorReason;

    /**
     * 调用方回调结果
     */
    private String ack;

    /**
     * 申请数据
     */
    private List<Map<String, String>> info;

    /**
     * 审批抄送人
     */
    private List<SpNotifyerVO> notifyers;

    /**
     * 审批流程节点
     */
    private List<ApprovalSpRecord> spRecords;

    /**
     * 审批备注
     */
    private List<ApprovalSpComment> spComments;

}
