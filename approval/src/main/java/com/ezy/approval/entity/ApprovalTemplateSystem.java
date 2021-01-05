package com.ezy.approval.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 审批模板与调用系统关系表
 * </p>
 *
 * @author CaiXiaowei
 * @since 2020-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_approval_template_system")
public class ApprovalTemplateSystem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 业务标识, 调用方唯一标识
     */
    private String systemCode;

    /**
     * 模板id
     */
    private String templateId;

    /**
     * 调用方回调url
     */
    private String callbackUrl;

    /**
     * 企微联系人userid
     */
    private String qwContactPerson;


}
