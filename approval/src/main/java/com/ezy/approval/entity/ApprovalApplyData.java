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
 * 审批申请数据
 * </p>
 *
 * @author CaiXiaowei
 * @since 2020-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_approval_apply_data")
public class ApprovalApplyData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 审批编号
     */
    private String spNo;

    /**
     * 控件类型
     */
    private String control;

    /**
     * 控件id
     */
    private String controlId;

    /**
     * 控件名称
     */
    private String title;

    /**
     * 控件值
     */
    private String value;


}
