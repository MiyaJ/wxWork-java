package com.ezy.message.model.callback.third;
import com.ezy.message.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName ApprovalNotifyNode.java
 * @Description 抄送信息
 * @createTime 2020年07月01日 12:35:00
 */
@Data
@XStreamAlias("NotifyNode")
public class ApprovalNotifyNode implements Serializable {
    private static final long serialVersionUID = 1058750600556017044L;

    /**
     * 抄送人姓名
     */
    @XStreamAlias("ItemName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String itemName;

    /**
     * 抄送人userid
     */
    @XStreamAlias("ItemUserId")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String itemUserId;

    /**
     * 抄送人头像
     */
    @XStreamAlias("ItemImage")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String itemImage;

}
