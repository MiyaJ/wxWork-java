package com.miya.model.sys;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName EmpInfo
 * @Description 员工信息
 * @createTime 2020/9/2$ 14:30$
 */
@Data
public class EmpInfo implements Serializable {

    /**
     * 企业微信用户id
     */
    private String qwUserId;
    /**
     * 企业微信用户名
     */
    private String qwName;

    /**
     * 员工id
     */
    private Long empId;

    /**
     * 员工姓名
     */
    private String empName;
}
