package com.ezy.approval.model.template;

import com.ezy.approval.model.apply.ApplyDataContent;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;
import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName TemplateDetailVO.java
 * @Description 模板详情vo
 * @createTime 2020年07月29日 13:49:00
 */
@Data
@Builder
public class TemplateDetailVO implements Serializable {
    private static final long serialVersionUID = 6067935891749280737L;

    @Tolerate
    public TemplateDetailVO() {
    }

    /**
     * 模板id
     */
    private String templateId;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 模板样式示例图url
     */
    private String patternImage;

    /**
     * json结构, 模板入参, 调用方需要传入的参数及其说明
     */
//    private List<ApplyDataContent> requestParam;
    private List<TemplateRequestParam> requestParam;

    /**
     * json结构
     */
    private String responseParam;

    /**
     * 模板描述
     */
    private String description;

    /**
     * 是否启用
     */
    private Boolean isEnable;
}
