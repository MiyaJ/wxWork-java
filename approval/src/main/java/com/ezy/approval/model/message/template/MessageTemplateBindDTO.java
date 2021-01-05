package com.ezy.approval.model.message.template;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName MessageTemplateBindDTO
 * @Description
 * @createTime 2020/9/16$ 10:56$
 */
@Data
public class MessageTemplateBindDTO implements Serializable {
    private static final long serialVersionUID = 95580481208281747L;


    private String systemCode;

    private Long messageTemplateId;
}
