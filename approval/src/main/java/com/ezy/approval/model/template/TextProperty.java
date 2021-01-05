package com.ezy.approval.model.template;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName TextProperty.java
 * @Description text属性
 * @createTime 2020年07月29日 13:30:00
 */
@Data
public class TextProperty implements Serializable {
    private static final long serialVersionUID = -7863139590555932605L;

    private String text;

    private String lang;
}
