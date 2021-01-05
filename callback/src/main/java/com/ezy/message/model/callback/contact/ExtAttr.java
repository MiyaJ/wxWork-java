package com.ezy.message.model.callback.contact;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName ExtAttr.java
 * @Description 扩展属性
 * @createTime 2020年08月03日 14:15:00
 */
@Data
@XStreamAlias("ExtAttr")
public class ExtAttr implements Serializable {
    private static final long serialVersionUID = -8598378123799803370L;

    @XStreamImplicit(itemFieldName="Item")
    private List<Item> items;
}
