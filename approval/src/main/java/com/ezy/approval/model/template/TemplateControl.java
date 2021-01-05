package com.ezy.approval.model.template;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName TemplateControl.java
 * @Description 模板控件
 * @createTime 2020年07月29日 10:47:00
 */
@Data
public class TemplateControl implements Serializable {
    private static final long serialVersionUID = 5295928382754256578L;

    /**
     * 模板控件属性
     */
    private TemplateControlProperty property;

    /**
     * 模板控件配置
     */
    private JSONObject config;
}
