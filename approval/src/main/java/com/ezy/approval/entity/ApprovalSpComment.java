package com.ezy.approval.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Caixiaowei
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_approval_sp_comment")
public class ApprovalSpComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @JsonIgnore
    private Long id;

    /**
     * 备注企微id
     */
    @JsonIgnore
    private String commentId;

    /**
     * 备注人企微id
     */
    private String userid;

    /**
     * 审批编号
     */
    @JsonIgnore
    private String spNo;

    /**
     * 备注内容
     */
    private String content;

    /**
     * 备注时间戳(秒,10位)
     */
    private Long commentTime;

    /**
     * 是否删除:0-否; 1-是,已删除
     */
    @JsonIgnore
    private Boolean isDeleted;


}
