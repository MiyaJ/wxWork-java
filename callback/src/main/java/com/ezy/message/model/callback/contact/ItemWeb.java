package com.ezy.message.model.callback.contact;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName ItemWeb.java
 * @Description 网页类型属性
 * @createTime 2020年08月03日 14:53:00
 */
@Data
@XStreamAlias("Web")
public class ItemWeb implements Serializable {
    private static final long serialVersionUID = -5809535162675202446L;

    /**
     * 网页的展示标题
     */
    @XStreamAlias("Title")
    private String title;

    /**
     * 网页的url
     */
    @XStreamAlias("Url")
    private String url;
}
