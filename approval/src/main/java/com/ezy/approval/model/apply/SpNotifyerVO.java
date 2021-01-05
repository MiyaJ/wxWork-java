package com.ezy.approval.model.apply;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName SpNotifyerVO.java
 * @Description 审批抄送人vo
 * @createTime 2020年07月31日 15:06:00
 */
@Data
public class SpNotifyerVO implements Serializable {

    private static final long serialVersionUID = -4615641934284743836L;
    /**
     * 企业微信用户id
     */
    private String userId;

    /**
     * 员工id
     */
    private Long empId;

    /**
     * 员工姓名
     */
    private String empName;
}
