package com.ezy.approval.service;

import com.ezy.approval.model.sys.DeptInfo;
import com.ezy.approval.model.sys.EmpInfo;

public interface ICommonService {

    /**
     * 根据企业微信用户id查询员工信息
     *
     * @param userId 业微信用户id
     * @return EmpInfo
     * @author Caixiaowei
     * @updateTime 2020/9/2 14:34
     */
    EmpInfo getEmpByUserId(String userId);

    /**
     * 根据企微部门id获取部门详情
     *
     * @param id 企微部门id
     * @return DeptInfo
     * @author Caixiaowei
     * @updateTime 2020/9/28 10:06
     */
    DeptInfo getDeptByQwId(String id);
}
