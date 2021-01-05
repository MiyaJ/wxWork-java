package com.ezy.approval.model.apply;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName ApprovalErrorListVO
 * @Description
 * @createTime 2020/9/22$ 15:31$
 */
@Data
public class ApprovalListVO implements Serializable {
    private static final long serialVersionUID = 7353938760049269205L;

    /**
     * 审批编号
     */
    private String spNo;

    /**
     * 审批名称
     */
    private String spName;

    /**
     * 员工id
     */
    private Long empId;

    /**
     * 员工名称
     */
    private String empName;

    /**
     * 系统
     */
    private String systemCode;

    /**
     * 审批状态
     */
    private Integer status;

    /**
     * 回调状态
     */
    private Integer callbackStatus;

}
