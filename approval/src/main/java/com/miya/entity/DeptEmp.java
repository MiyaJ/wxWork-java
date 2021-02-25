package com.miya.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 部门员工关系
 * </p>
 *
 * @author Caixiaowei
 * @since 2021-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_dept_emp")
@NoArgsConstructor
@AllArgsConstructor
public class DeptEmp implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long deptId;

    private Long empId;

    /**
     * 是否是主部门: 0-否; 1-是
     */
    private Boolean isMainDept;

    /**
     * 是否是部门leader: 0-否; 1-是
     */
    private Boolean isLeader;

    /**
     * 部门内的排序值, 默认0
     */
    private Long sort;

    /**
     * 删除标识:0未删除; 1-已删除
     */
    private Boolean isDeleted;


}
