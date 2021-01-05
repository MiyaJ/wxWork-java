package com.ezy.message.model.callback.third;

import com.ezy.message.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName ApprovalNodeItem.java
 * @Description 审批节点信息
 * @createTime 2020年07月01日 12:32:00
 */
@Data
@XStreamAlias("Item")
public class ApprovalNodeItem implements Serializable {
    private static final long serialVersionUID = 1638868432056328711L;

    /**
     * 分支审批人姓名
     */
    @XStreamAlias("ItemName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String itemName;

    /**
     * 分支审批人userid
     */
    @XStreamAlias("ItemUserId")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String itemUserId;

    /**
     * 分支审批人头像
     */
    @XStreamAlias("ItemImage")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String itemImage;

    /**
     * 分支审批审批操作状态：1-审批中；2-已同意；3-已驳回；4-已转审
     */
    @XStreamAlias("ItemStatus")
    private Integer itemStatus;

    /**
     * 分支审批人审批意见
     */
    @XStreamAlias("ItemSpeech")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String itemSpeech;

    /**
     * 分支审批人操作时间, 时间戳, 精确到秒
     */
    @XStreamAlias("ApplyUserImage")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String ItemOpTime;


}
