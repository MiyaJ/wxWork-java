package com.miya.model.contact;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName WxDept
 * @Description
 * @createTime 2021/2/24 11:26
 */
@Data
public class WxDept implements Serializable {
    private static final long serialVersionUID = -5497365205315540306L;

    /**
     * 部门id
     */
    private Long id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 英文名称
     */
    @JSONField(name = "name_en")
    private String nameEn;

    /**
     * 父部门id
     */
    @JSONField(name = "parentid")
    private Long parentId;

    /**
     * 在父部门中的次序值, order值大的排序靠前
     */
    private Long order;
}
