package com.ezy.approval.model.apply;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName ApplyDataItem
 * @Description
 * @createTime 2020/9/25$ 15:12$
 */
@Data
public class ApplyDataItem implements Serializable {

    private static final long serialVersionUID = 3295243909593957330L;
    private String control;

    private String id;

    private JSONObject value;
}
