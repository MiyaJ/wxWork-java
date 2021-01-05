package com.ezy.approval.model.apply;

import com.alibaba.fastjson.JSONObject;
import com.ezy.approval.model.template.TextProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;
import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName ApplyDataContent.java
 * @Description 申请数据内容
 * @createTime 2020年07月30日 10:36:00
 */
@Data
@Builder
public class ApplyDataContent implements Serializable {

    @Tolerate
    public ApplyDataContent() {
    }

    /**
     * 控件
     */
    private String control;

    /**
     * 控件id
     */
    private String id;

    /**
     * 是否必填: 0否; 1是
     */
    private Integer require;

    /**
     * 控件标题
     */
    private List<TextProperty> title;

    /**
     * 控件值
     */
    private JSONObject value;
}
