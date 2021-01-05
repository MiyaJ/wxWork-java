package com.ezy.message.model.callback.third;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName ApprovalNodes.java
 * @Description 审批流程信息
 * @createTime 2020年07月01日 12:31:00
 */
@Data
@XStreamAlias("ApprovalNode")
public class ApprovalNode implements Serializable {
    private static final long serialVersionUID = -6055519164602734208L;

    /**
     * 节点审批操作状态：1-审批中；2-已同意；3-已驳回；4-已转审
     */
    @XStreamAlias("NodeStatus")
    private Integer nodeStatus;

    /**
     * 审批节点属性：1-或签；2-会签
     */
    @XStreamAlias("NodeAttr")
    private Integer nodeAttr;

    /**
     * 审批节点类型：1-固定成员；2-标签；3-上级
     */
    @XStreamAlias("NodeType")
    private Integer nodeType;

    /**
     * 审批节点信息，当节点为标签或上级时，一个节点可能有多个分支
     */
    @XStreamAlias("Items")
    private List<ApprovalNodeItem> items;
}
