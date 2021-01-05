package com.ezy.approval.model.apply;

import com.alibaba.fastjson.JSONObject;
import com.ezy.approval.model.template.TextProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Caixiaowei
 * @ClassName ApprovalApplyDTO.java
 * @Description 审批申请dto
 * @createTime 2020年07月29日 14:53:00
 */
@Data
public class ApprovalApplyDTO implements Serializable {
    private static final long serialVersionUID = -5008923469320814588L;

    /**
     * 调用方系统标识
     */
    private String systemCode;

    /**
     * 模板id
     */
    private String templateId;

    /**
     * 申请人userid
     */
    private String creatorUserid;


    /**
     * 审批人模式：0-自定义指定; 1 模板默认
     */
    private Integer useTemplateApprover;


    /**
     * 审批流程信息
     */
    private List<Approver> approver;


    /**
     * 抄送人, 仅use_template_approver为0时生效。
     */
    private List<String> notifyer;


    /**
     * 抄送方式：1-提单时抄送（默认值）； 2-单据通过后抄送；3-提单和单据通过后抄送。仅use_template_approver为0时生效。
     */
    private Integer notifyType;


    /**
     * 审批申请数据
     */
    private JSONObject applyData;

    private Map<String, Object> applyDataMap;


    /**
     * 摘要信息, 最多3条
     */
    private List<TextProperty> summaryList;

}
