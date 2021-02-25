package com.miya.service;

import com.miya.model.contact.WxDept;
import com.miya.model.contact.WxUser;

import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName IContactService
 * @Description 通讯录业务层接口
 * @createTime 2021/2/24 10:49
 */
public interface IContactService {

    /**
     * 获取通讯录应用token
     *
     * @param
     * @return
     * @author Caixiaowei
     * @updateTime 2021/2/24 11:20
     */
    String getAccessToken();

    /**
     * 获取部门列表
     *
     * @param id 部门id, 获取指定部门及其下的子部门（以及及子部门的子部门等等，递归）。 如果不填，默认获取全量组织架构
     * @return List<WxDept> 部门列表
     * @author Caixiaowei
     * @updateTime 2021/2/24 11:24
     */
    List<WxDept> departmentList(Long id);
    
    /**
     * 读取成员
     *
     * @param userId 成员UserID。对应管理端的帐号，企业内必须唯一
     * @return WxUser 企业微信成员信息
     * @author Caixiaowei
     * @updateTime 2021/2/24 14:54
     */
    WxUser getUser(String userId);

    /**
     * 获取部门成员
     *
     * @param departmentId 获取的部门id
     * @param fetchChild 是否递归获取子部门下面的成员：1-递归获取，0-只获取本部门
     * @return 
     * @author Caixiaowei
     * @updateTime 2021/2/24 15:19
     */
    List<WxUser> userSimpleList(String departmentId, Integer fetchChild);

    /**
     * 获取部门成员
     *
     * @param departmentId 获取的部门id
     * @param fetchChild 是否递归获取子部门下面的成员：1-递归获取，0-只获取本部门
     * @return
     * @author Caixiaowei
     * @updateTime 2021/2/24 15:19
     */
    List<WxUser> userList(Long departmentId, Integer fetchChild);

    /**
     * 初始化部门与员工
     *
     * @param deptId 部门id, 不传或null 为初始化全部
     * @return
     * @author Caixiaowei
     * @updateTime 2021/2/24 15:46
     */
    void initDeptAndEmp(Long deptId);

    /**
     * 修复部门与员工
     *
     * @param deptId 部门id, 不传或null 为初始化全部
     * @return
     * @author Caixiaowei
     * @updateTime 2021/2/25 15:19
     */
    void repairDeptAndEmp(Long deptId);
}
