package com.ezy.approval.model.template;

import com.alibaba.fastjson.JSONArray;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName TemplateRequestParam
 * @Description
 * @createTime 2020/9/25$ 11:13$
 */
@Data
@Builder
public class TemplateRequestParam implements Serializable {
    private static final long serialVersionUID = 2139213407263641480L;

    @Tolerate
    public TemplateRequestParam() {
    }

    /**
     * 控件id
     */
    private String id;

    /**
     * 控件
     */
    private String control;

    /**
     * 类型
     */
    private String type;

    /**
     * 是否必填: 0否; 1是
     */
    private Integer require;

    /**
     * 是否打印: 0否; 1是
     */
    private Integer unPrint;

    /**
     * 控件标题
     */
    private String title;

    /**
     * 控件标题
     */
    private String placeholder;

    /**
     * selector 选项
     */
    private JSONArray options;

}
