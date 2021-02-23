package com.miya.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 部门信息
 * </p>
 *
 * @author Caixiaowei
 * @since 2021-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_dept")
public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 部门名称(同一个层级的部门名称不能重复)
     */
    private String name;

    /**
     * 英文名称
     */
    private String nameEn;

    /**
     * 父部门id
     */
    private Long parentId;

    /**
     * 在父部门中的次序值
     */
    private Integer order;

    /**
     * 删除标识: 0-未删除; 1-已删除
     */
    private Boolean isDeleted;

    /**
     * 企业微信部门id
     */
    private Long qywxId;

    /**
     * 企业微信父部门id
     */
    private Long qywxParentId;


}
