package com.ezy.approval.service.impl;

import com.ezy.approval.model.sys.DeptInfo;
import com.ezy.approval.model.sys.EmpInfo;
import com.ezy.approval.service.ICommonService;
import org.springframework.stereotype.Service;

/**
 * @author Caixiaowei
 * @ClassName CommonServiceImpl
 * @Description
 * @createTime 2020/9/4$ 10:43$
 */
@Service
public class CommonServiceImpl implements ICommonService {

    /**
     * 根据企业微信用户id查询员工信息
     *
     * @param userId 业微信用户id
     * @return EmpInfo
     * @author Caixiaowei
     * @updateTime 2020/9/2 14:34
     */
    @Override
    public EmpInfo getEmpByUserId(String userId) {
        EmpInfo empInfo = new EmpInfo();

        // TODO: 2020/9/2 待usercenter 开发完成，实现具体逻辑
        empInfo.setQwUserId(userId);
        empInfo.setQwName("cxw0615");
        empInfo.setEmpId(1L);
        empInfo.setEmpName("张三");

        return empInfo;
    }

    /**
     * 根据企微部门id获取部门详情
     *
     * @param id 企微部门id
     * @return DeptInfo
     * @author Caixiaowei
     * @updateTime 2020/9/28 10:06
     */
    @Override
    public DeptInfo getDeptByQwId(String id) {
        // TODO: 2020/9/28 根据企微部门id获取部门详情
        DeptInfo deptInfo = new DeptInfo();
        deptInfo.setQwId(id);
        deptInfo.setQwName("总裁办");
        deptInfo.setDeptId(2L);
        deptInfo.setDeptName("总裁办");

        return deptInfo;
    }
}
