package com.ezy.message.model.callback.approval;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName SpRecord.java
 * @Description 审批流程信息
 * @createTime 2020年07月07日 15:38:00
 */
@Data
@XStreamAlias("SpRecord")
public class SpRecord implements Serializable {
    private static final long serialVersionUID = 6324330626013127536L;

    /**
     * 审批节点状态：1-审批中；2-已同意；3-已驳回；4-已转审
     */
    @XStreamAlias("SpStatus")
    private Integer spStatus;

    /**
     * 节点审批方式：1-或签；2-会签
     */
    @XStreamAlias("ApproverAttr")
    private Integer approverAttr;

    @XStreamImplicit(itemFieldName="Details")
    private List<Details> details;
}
