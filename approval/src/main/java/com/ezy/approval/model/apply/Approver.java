package com.ezy.approval.model.apply;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName Approver.java
 * @Description 审批流程信息
 * @createTime 2020年07月29日 15:01:00
 */
@Data
public class Approver implements Serializable {

    /**
     * 节点审批方式：1-或签；2-会签，仅在节点为多人审批时有效
     */
    private Integer attr;

    /**
     * 审批节点审批人userid列表，若为多人会签、多人或签，需填写每个人的userid
     */
    private List<String> userid;
}
