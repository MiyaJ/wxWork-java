package com.ezy.approval.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 审批节点详情
 * </p>
 *
 * @author CaiXiaowei
 * @since 2020-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_approval_sp_record_detail")
public class ApprovalSpRecordDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 审批流程id
     */
    private Long spRecordId;

    /**
     * 分支审批人userid
     */
    private String approverUserId;

    /**
     * 分支审批人员工id
     */
    private Long approverEmpId;

    /**
     * 分支审批人员工姓名
     */
    private String approverEmpName;

    /**
     * 审批意见
     */
    private String speech;

    /**
     * 分支审批人审批状态：1-审批中；2-已同意；3-已驳回；4-已转审
     */
    private Integer spStatus;

    /**
     * 节点分支审批人审批操作时间，0为尚未操作
     */
    private Long spTime;


}
