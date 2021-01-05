package com.ezy.approval.model.template;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName ApprovalTemplateAddDTO.java
 * @Description 审批模板新增dto
 * @createTime 2020年07月27日 14:55:00
 */
@Data
@EqualsAndHashCode
public class ApprovalTemplateAddDTO implements Serializable {
    private static final long serialVersionUID = -1120031828209813472L;

    /**
     * 模板id
     */
    private String templateId;

    /**
     * 调用方系统标识
     */
    private String systemCode;

    /**
     * 调用方回调连接
     */
    private String callbackUrl;

    /**
     * 模板样式示例图url
     */
    private String patternImage;

    /**
     * 回调通知企微userid
     */
    private String qwContactPerson;

    /**
     * 模板描述
     */
    private String description;

    /**
     * 启用状态
     */
    private Boolean isEnable;
}
