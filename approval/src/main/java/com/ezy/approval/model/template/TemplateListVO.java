package com.ezy.approval.model.template;

import com.ezy.approval.model.apply.ApplyDataContent;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName TemplateListVO
 * @Description
 * @createTime 2020/9/16$ 14:32$
 */
@Data
public class TemplateListVO implements Serializable {
    private static final long serialVersionUID = -2475041097798978426L;

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
     * 模板描述
     */
    private String description;

    /**
     * 是否启用
     */
    private Boolean isEnable;
}
