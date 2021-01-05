package com.ezy.message.model.callback.approval;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName Details.java
 * @Description 审批节点详情
 * @createTime 2020年07月08日 10:55:00
 */
@Data
@XStreamAlias("Details")
public class Details implements Serializable {
    private static final long serialVersionUID = -8472053746876187691L;

    /**
     * 分支审批人
     */
    @XStreamAlias("Approver")
    private Applyer approver;

    /**
     * 审批意见字段
     */
    @XStreamAlias("Speech")
    private String speech;

    /**
     * 分支审批人审批状态：1-审批中；2-已同意；3-已驳回；4-已转审
     */
    @XStreamAlias("SpStatus")
    private Integer spStatus;

    /**
     * 节点分支审批人审批操作时间，0为尚未操作
     */
    @XStreamAlias("SpTime")
    private Long spTime;
}
