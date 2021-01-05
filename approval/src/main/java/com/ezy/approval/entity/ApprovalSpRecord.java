package com.ezy.approval.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;

/**
 * <p>
 * 审批流程信息
 * </p>
 *
 * @author CaiXiaowei
 * @since 2020-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_approval_sp_record")
public class ApprovalSpRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 审批单号
     */
    private String spNo;

    /**
     * 审批节点状态：1-审批中；2-已同意；3-已驳回；4-已转审
     */
    private Integer spStatus;

    /**
     * 节点审批方式：1-或签；2-会签
     */
    private Integer approverAttr;

    /**
     * 当前审批节点：0-第一个审批节点；1-第二个审批节点…以此类推
     */
    private Integer step;

    /**
     * 审批流程节点详情
     */
    private transient  List<ApprovalSpRecordDetail> recordDetails;

}
