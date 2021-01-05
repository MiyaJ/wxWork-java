package com.ezy.message.model.callback.contact;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName Item.java
 * @Description 扩展属性项
 * @createTime 2020年08月03日 14:19:00
 */
@Data
@XStreamAlias("Item")
public class Item implements Serializable {
    private static final long serialVersionUID = 5759523679971757289L;

    /**
     *
     */
    @XStreamAlias("Name")
    private String name;

    /**
     * 扩展属性类型: 0-本文 1-网页
     */
    @XStreamAlias("Type")
    private String type;

    /**
     * 文本属性类型
     */
    @XStreamAlias("Text")
    private ItemText text;

    /**
     * 网页类型属性
     */
    @XStreamAlias("Web")
    private ItemWeb web;

}
