package com.ezy.approval.model.apply;

import com.ezy.approval.model.BaseQueryDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName ApprovalQueryDTO
 * @Description
 * @createTime 2020/9/22$ 15:36$
 */
@Data
public class ApprovalQueryDTO extends BaseQueryDTO implements Serializable {
    private static final long serialVersionUID = 5169216478515878146L;

    /**
     * 审批编号
     */
    private String spNo;
    /**
     * 审批名称
     */
    private String spName;


    /**
     * 模板id
     */
    private String templateId;

    /**
     * 员工id
     */
    private Long empId;

    /**
     * 员工名称
     */
    private String empName;

    /**
     * 审批状态
     */
    private Integer status;

    /**
     * 回调状态: 0 失败; 1成功
     */
    private Integer callbackStatus;

    /**
     * 申请时间
     */
    private Long applyTime;
}
