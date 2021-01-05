package com.ezy.approval.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 审批模板 
 * </p>
 *
 * @author CaiXiaowei
 * @since 2020-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_approval_template")
public class ApprovalTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 模板id
     */
    private String templateId;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 删除标识 0未删除;1已删除
     */
    private Integer isDeleted;

    /**
     * 模板内容
     */
    private String content;

    /**
     * 模板样式示例图url
     */
    private String patternImage;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * json结构, 模板入参, 调用方需要传入的参数及其说明
     */
    private String requestParam;

    /**
     * json结构
     */
    private String responseParam;

    /**
     * 模板描述
     */
    private String description;

    /**
     * 是否启用: 1 true 启用; 0 false 未启用
     */
    private Boolean isEnable;


}
