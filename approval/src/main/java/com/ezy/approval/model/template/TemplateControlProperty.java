package com.ezy.approval.model.template;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName TemplateControlProperty.java
 * @Description 模板控件属性
 * @createTime 2020年07月29日 10:48:00
 */
@Data
@Builder
public class TemplateControlProperty implements Serializable {
    private static final long serialVersionUID = 472380110965750118L;

    @Tolerate
    public TemplateControlProperty() { }

    /**
     * 使用打印
     */
    private Integer unPrint;

    /**
     * 控件名称
     */
    private String control;

    /**
     * 是否必填
     */
    private Integer require;

    /**
     * 控件id
     */
    private String id;

    /**
     * 说明
     */
    private String placeholder;

    /**
     * 标题名称
     */
    private String title;
}
