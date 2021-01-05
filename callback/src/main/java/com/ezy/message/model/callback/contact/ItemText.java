package com.ezy.message.model.callback.contact;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName ItemText.java
 * @Description 文本属性类型
 * @createTime 2020年08月03日 14:21:00
 */
@XStreamAlias("Text")
@Data
public class ItemText implements Serializable {

    private static final long serialVersionUID = -1006984579999186779L;

    /**
     * 文本属性内容
     */
    @XStreamAlias("Value")
    private String value;
}
