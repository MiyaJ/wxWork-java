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
 * 审批抄送人信息
 * </p>
 *
 * @author CaiXiaowei
 * @since 2020-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_approval_sp_notifyer")
public class ApprovalSpNotifyer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 审批单号
     */
    private String spNo;

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

    private Integer step;
}
