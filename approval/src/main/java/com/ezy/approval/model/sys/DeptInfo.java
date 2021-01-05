package com.ezy.approval.model.sys;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName DeptInfo
 * @Description
 * @createTime 2020/9/28$ 10:05$
 */
@Data
public class DeptInfo implements Serializable {

    /**
     * 企业微信用户id
     */
    private String qwId;
    /**
     * 企业微信用户名
     */
    private String qwName;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;
}
