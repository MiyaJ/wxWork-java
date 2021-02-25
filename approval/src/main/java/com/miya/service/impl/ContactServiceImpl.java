package com.miya.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.miya.constants.RedisConstans;
import com.miya.entity.Dept;
import com.miya.entity.DeptEmp;
import com.miya.entity.Emp;
import com.miya.model.contact.WxDept;
import com.miya.model.contact.WxUser;
import com.miya.service.*;
import com.miya.utils.OkHttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Caixiaowei
 * @ClassName ContactServiceImpl
 * @Description
 * @createTime 2021/2/24 10:49
 */
@Service
@Slf4j
public class ContactServiceImpl extends WxWorkServiceImpl implements IContactService {

    @Value("${qywx.contact-corpsecret}")
    private String CONTACT_SECRET;

    @Autowired
    private RedisService redisService;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IEmpService empService;
    @Autowired
    private IDeptEmpService deptEmpService;

    /**
     * 获取通讯录应用token
     *
     * @return
     * @author Caixiaowei
     * @updateTime 2021/2/24 11:20
     */
    @Override
    public String getAccessToken() {
        String accessToken = StrUtil.EMPTY;
        Object value = redisService.get(RedisConstans.QYWX_ACCESS_TOKEN_KEY_CONTACT);
        if (value == null) {
            try {
                accessToken = super.getAccessToken(this.CONTACT_SECRET);
                if (StringUtils.isNotBlank(accessToken)) {
                    redisService.set(RedisConstans.QYWX_ACCESS_TOKEN_KEY_CONTACT, accessToken, RedisConstans.QYWX_ACCESS_TOKEN_EXPIRATION);
                }
            } catch (Exception e) {
                log.error("获取审批应用access_token 异常--->{}", e);
            }

        } else {
            accessToken = String.valueOf(value);
        }
        return accessToken;
    }

    /**
     * 获取部门列表
     *
     * @param id 部门id, 获取指定部门及其下的子部门（以及及子部门的子部门等等，递归）。 如果不填，默认获取全量组织架构
     * @return List<WxDept> 部门列表
     * @author Caixiaowei
     * @updateTime 2021/2/24 11:24
     */
    @Override
    public List<WxDept> departmentList(Long id) {
        String accessToken = this.getAccessToken();
        String url = "https://qyapi.weixin.qq.com/cgi-bin/department/list";

        Map<String, String> params = Maps.newHashMap();
        params.put("ACCESS_TOKEN", accessToken);
        if (id != null) {
            params.put("id", String.valueOf(id));
        }
        List<WxDept> wxDepts = Lists.newArrayList();
        try {
            String result = OkHttpClientUtil.doGet(url, null, params);
            JSONObject data = JSONObject.parseObject(result);
            Integer errcode = data.getInteger("errcode");
            if (errcode != 0 ) {
                return wxDepts;
            }
            String department = data.getString("department");
            wxDepts = JSONArray.parseArray(department, WxDept.class);
            return wxDepts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wxDepts;
    }

    /**
     * 读取成员
     *
     * @param userId 成员UserID。对应管理端的帐号，企业内必须唯一
     * @return WxUser 企业微信成员信息
     * @author Caixiaowei
     * @updateTime 2021/2/24 14:54
     */
    @Override
    public WxUser getUser(String userId) {
        String accessToken = this.getAccessToken();
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/get";

        Map<String, String> params = Maps.newHashMap();
        params.put("ACCESS_TOKEN", accessToken);
        params.put("userid", String.valueOf(userId));

        try {
            String result = OkHttpClientUtil.doGet(url, null, params);
            JSONObject data = JSONObject.parseObject(result);
            Integer errcode = data.getInteger("errcode");
            if (errcode != 0 ) {
                return new WxUser();
            }
            WxUser wxUser = JSONObject.parseObject(result, WxUser.class);
            return wxUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new WxUser();
    }

    /**
     * 获取部门成员
     *
     * @param departmentId 获取的部门id
     * @param fetchChild   是否递归获取子部门下面的成员：1-递归获取，0-只获取本部门
     * @return
     * @author Caixiaowei
     * @updateTime 2021/2/24 15:19
     */
    @Override
    public List<WxUser> userSimpleList(String departmentId, Integer fetchChild) {
        String accessToken = this.getAccessToken();
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist";

        Map<String, String> params = Maps.newHashMap();
        params.put("ACCESS_TOKEN", accessToken);
        params.put("department_id", String.valueOf(departmentId));
        params.put("fetch_child", String.valueOf(fetchChild));

        List<WxUser> wxUsers = Lists.newArrayList();
        try {
            String result = OkHttpClientUtil.doGet(url, null, params);
            JSONObject data = JSONObject.parseObject(result);
            Integer errcode = data.getInteger("errcode");
            if (errcode != 0 ) {
                return wxUsers;
            }
            String userlist = data.getString("userlist");
            wxUsers = JSONArray.parseArray(userlist, WxUser.class);
            return wxUsers;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wxUsers;
    }

    /**
     * 获取部门成员
     *
     * @param departmentId 获取的部门id
     * @param fetchChild   是否递归获取子部门下面的成员：1-递归获取，0-只获取本部门
     * @return
     * @author Caixiaowei
     * @updateTime 2021/2/24 15:19
     */
    @Override
    public List<WxUser> userList(Long departmentId, Integer fetchChild) {
        String accessToken = this.getAccessToken();
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/list";

        Map<String, String> params = Maps.newHashMap();
        params.put("ACCESS_TOKEN", accessToken);
        params.put("department_id", String.valueOf(departmentId));
        params.put("fetch_child", String.valueOf(fetchChild));

        List<WxUser> wxUsers = Lists.newArrayList();
        try {
            String result = OkHttpClientUtil.doGet(url, null, params);
            JSONObject data = JSONObject.parseObject(result);
            Integer errcode = data.getInteger("errcode");
            if (errcode != 0 ) {
                return wxUsers;
            }
            String userlist = data.getString("userlist");
            wxUsers = JSONArray.parseArray(userlist, WxUser.class);
            return wxUsers;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wxUsers;
    }

    /**
     * 初始化部门与员工
     *
     * @param deptId 部门id, 不传或null 为初始化全部
     * @return
     * @author Caixiaowei
     * @updateTime 2021/2/24 15:46
     */
    @Override
    public void initDeptAndEmp(Long deptId) {
        List<WxDept> wxDepts = this.departmentList(deptId);

        Map<Long, WxDept> wxDeptMap = Maps.newHashMap();
        List<Dept> insertDeptList = Lists.newArrayList();
        if (CollUtil.isNotEmpty(wxDepts)) {
            Map<Long, List<WxDept>> collect = wxDepts.stream().collect(Collectors.groupingBy(e -> e.getId()));
            for (Map.Entry<Long, List<WxDept>> entry : collect.entrySet()) {
                wxDeptMap.put(entry.getKey(), entry.getValue().get(0));
            }

            List<WxDept> wxDeptSortList = wxDepts.stream().sorted((d1, d2) -> {
                return d1.getId().compareTo(d2.getId());
            }).collect(Collectors.toList());

            for (WxDept wxDept : wxDeptSortList) {
                Dept dept = new Dept(wxDept);
                insertDeptList.add(dept);
            }
        }

        // 初始化部门, 部门上下级关系缺失, 待部门初始化后更新
        deptService.saveBatch(insertDeptList);

        // 更新部门上下级关系
        Map<Long, List<Dept>> qwAndDeptMap = insertDeptList.stream().collect(Collectors.groupingBy(e -> e.getQywxId()));
        for (Dept dept : insertDeptList) {
            Long qywxParentId = dept.getQywxParentId();

            Long parentId = Dept.TOP;
            if (!qywxParentId.equals(Dept.TOP)) {
                parentId = qwAndDeptMap.get(qywxParentId).get(0).getId();
            }
            dept.setParentId(parentId);
        }
        deptService.updateBatchById(insertDeptList);

        // 初始化员工
        List<WxUser> wxUsersAll = Lists.newArrayList();
        for (Dept dept : insertDeptList) {
            Long qywxId = dept.getQywxId();
            List<WxUser> wxUsers = this.userList(qywxId, 0);

            wxUsersAll.addAll(wxUsers);
        }
        // 去重
        List<WxUser> wxUserList = wxUsersAll.stream()
                .filter(u -> StrUtil.isNotEmpty(u.getUserId()))
                .filter(distinctByKey(user -> user.getUserId()))
                .collect(Collectors.toList());
        List<Emp> insertEmpList = Lists.newArrayList();
        for (WxUser wxUser : wxUserList) {
            Emp emp = new Emp(wxUser);
            insertEmpList.add(emp);
        }
        empService.saveBatch(insertEmpList);

        // 维护dept 与 emp 的关系, 只维护主部门
        List<DeptEmp> insertDeptEmpList = Lists.newArrayList();
        for (Emp emp : insertEmpList) {
            DeptEmp deptEmp = new DeptEmp();
            Long tmpDeptId = qwAndDeptMap.get(emp.getMainDepartment()).get(0).getId();

            deptEmp.setDeptId(tmpDeptId);
            deptEmp.setEmpId(emp.getId());
            deptEmp.setIsDeleted(Boolean.FALSE);
            deptEmp.setIsMainDept(Boolean.TRUE);

            Boolean isLeader = checkIsLeader(emp);
            deptEmp.setIsLeader(isLeader);
            insertDeptEmpList.add(deptEmp);
        }
        deptEmpService.saveBatch(insertDeptEmpList);

    }

    /**
     * 修复部门与员工
     *
     * @param deptId 部门id, 不传或null 为初始化全部
     * @return
     * @author Caixiaowei
     * @updateTime 2021/2/25 15:19
     */
    @Override
    public void repairDeptAndEmp(Long deptId) {
        List<WxDept> wxDepts = this.departmentList(deptId);

        Map<Long, Dept> deptMap = Maps.newHashMap();
        if (CollUtil.isNotEmpty(wxDepts)) {
            List<Long> idList = wxDepts.stream().map(WxDept::getId).collect(Collectors.toList());

            // 根据企微部门ids查询db
            QueryWrapper<Dept> deptQuery = new QueryWrapper<>();
            deptQuery.in("qywx_id", idList);
            deptQuery.eq("is_deleted", Boolean.FALSE);
            List<Dept> deptList = deptService.list(deptQuery);

            Map<Long, List<Dept>> collect = deptList.stream().filter(d -> d.getQywxId() != null)
                    .collect(Collectors.groupingBy(e -> e.getQywxId()));
            for (Map.Entry<Long, List<Dept>> entry : collect.entrySet()) {
                deptMap.put(entry.getKey(), entry.getValue().get(0));
            }

            // 修复部门名称, 与企业微信保持一致
            List<Dept> insertDeptList = Lists.newArrayList();
            for (WxDept wxDept : wxDepts) {
                Long id = wxDept.getId();
                String name = wxDept.getName();
                Long parentId = wxDept.getParentId();

                Dept dept = deptMap.get(id);
                if (dept != null) {
                    dept.setName(name);
                    dept.setQywxParentId(parentId);
                    insertDeptList.add(dept);
                } else {
                    Dept newDept = new Dept(wxDept);
                    insertDeptList.add(newDept);
                }
            }
            deptService.saveOrUpdateBatch(insertDeptList);

            // 修复部门上下级关系, 与企业微信保持一致
            Map<Long, List<Dept>> qwAndDeptMap = insertDeptList.stream().collect(Collectors.groupingBy(e -> e.getQywxId()));
            for (Dept dept : insertDeptList) {
                Long qywxParentId = dept.getQywxParentId();

                Long parentId = Dept.TOP;
                if (!qywxParentId.equals(Dept.TOP)) {
                    parentId = qwAndDeptMap.get(qywxParentId).get(0).getId();
                }
                dept.setParentId(parentId);
            }
            deptService.updateBatchById(insertDeptList);

            // 修复员工: 查漏补缺
            List<Emp> insertEmpList = Lists.newArrayList();
            List<WxUser> wxUsersAll = Lists.newArrayList();
            for (Dept dept : insertDeptList) {
                Long qywxId = dept.getQywxId();
                List<WxUser> wxUsers = this.userList(qywxId, 0);

                wxUsersAll.addAll(wxUsers);
            }
            // 去重
            List<WxUser> wxUserList = wxUsersAll.stream()
                    .filter(u -> StrUtil.isNotEmpty(u.getUserId()))
                    .filter(distinctByKey(user -> user.getUserId()))
                    .collect(Collectors.toList());
            List<String> userIdList = wxUserList.stream().map(WxUser::getUserId).collect(Collectors.toList());
            QueryWrapper<Emp> empQuery = new QueryWrapper<>();
            empQuery.in("qywx_user_id", userIdList);
            List<Emp> empList = empService.list(empQuery);

            // qywxUserId:emp
            Map<String, Emp> qwEmpMap = Maps.newHashMap();
            Map<String, List<Emp>> collect1 = empList.stream().collect(Collectors.groupingBy(e -> e.getQywxUserId()));
            for (Map.Entry<String, List<Emp>> entry : collect1.entrySet()) {
                qwEmpMap.put(entry.getKey(), entry.getValue().get(0));
            }
            for (WxUser wxUser : wxUserList) {
                String userId = wxUser.getUserId();
                if (qwEmpMap.get(userId) != null) {
                    Emp emp = qwEmpMap.get(userId);
                    BeanUtil.copyProperties(wxUser, emp);
                    insertEmpList.add(emp);
                } else {
                    Emp emp = new Emp(wxUser);
                    insertEmpList.add(emp);
                }
            }
            empService.saveOrUpdateBatch(insertEmpList);

            // 修复部门员工关系
            List<DeptEmp> insertDeptEmpList = Lists.newArrayList();
            List<Long> empIdList = insertEmpList.stream().map(Emp::getId).collect(Collectors.toList());
            QueryWrapper<DeptEmp> deptEmpQuery = new QueryWrapper<>();
            deptEmpQuery.in("emp_id", empIdList);
            deptEmpQuery.eq("is_deleted", Boolean.FALSE);
            List<DeptEmp> deptEmpList = deptEmpService.list(deptEmpQuery);
            Map<Long, List<DeptEmp>> empDeptMap = deptEmpList.stream().collect(Collectors.groupingBy(e -> e.getEmpId()));

            for (Emp emp : insertEmpList) {
                String qywxUserId = emp.getQywxUserId();
                List<Long> department = emp.getDepartment();
                List<Integer> isLeaderInDept = emp.getIsLeaderInDept();

                if (CollUtil.isNotEmpty(empDeptMap.get(emp.getId()))) {
                    // 更新dept 关系
                    DeptEmp deptEmp = empDeptMap.get(emp.getId()).get(0);
                    deptEmp.setIsDeleted(Boolean.FALSE);
                    deptEmp.setIsMainDept(Boolean.TRUE);

                    Long mainDepartment = qwEmpMap.get(qywxUserId).getMainDepartment();
                    Boolean isLeader = Boolean.FALSE;
                    for (int i = 0; i < department.size(); i++) {
                        Long tmpDepartment = department.get(i);
                        Integer tmpIsLeader = isLeaderInDept.get(i);
                        if (tmpDepartment.equals(mainDepartment) && tmpIsLeader == 1) {
                            isLeader = Boolean.TRUE;
                        }
                    }

                    deptEmp.setDeptId(mainDepartment);
                    deptEmp.setIsLeader(isLeader);

                    insertDeptEmpList.add(deptEmp);
                } else {
                    Long mainDepartment = qwEmpMap.get(qywxUserId).getMainDepartment();
                    Boolean isLeader = Boolean.FALSE;
                    for (int i = 0; i < department.size(); i++) {
                        Long tmpDepartment = department.get(i);
                        Integer tmpIsLeader = isLeaderInDept.get(i);
                        if (tmpDepartment.equals(mainDepartment) && tmpIsLeader == 1) {
                            isLeader = Boolean.TRUE;
                        }
                    }

                    // 新增dept 关系
                    DeptEmp deptEmp = new DeptEmp();
                    deptEmp.setDeptId(mainDepartment);
                    deptEmp.setIsLeader(isLeader);
                    deptEmp.setIsMainDept(Boolean.TRUE);
                    deptEmp.setIsDeleted(Boolean.FALSE);
                    deptEmp.setEmpId(emp.getId());

                    insertDeptEmpList.add(deptEmp);
                }
            }

            deptEmpService.saveOrUpdateBatch(insertDeptEmpList);
        }
    }

    /**
     * check 是否是leader
     *
     * @param emp
     * @return Boolean 是否是leader
     * @author Caixiaowei
     * @updateTime 2021/2/25 15:09
     */
    private Boolean checkIsLeader(Emp emp) {
        Boolean isLeader = Boolean.FALSE;
        List<Long> department = emp.getDepartment();
        List<Integer> isLeaderInDept = emp.getIsLeaderInDept();
        Long mainDepartment = emp.getMainDepartment();
        for (int i = 0; i < department.size(); i++) {
            Long tmpDepartment = department.get(i);
            Integer integer = isLeaderInDept.get(i);
            if (mainDepartment.equals(tmpDepartment) && integer == 1) {
                isLeader = Boolean.TRUE;
            }
        }
        return isLeader;
    }

    /**
     * steam 按照指定属性去重
     *
     * @param
     * @return
     * @author Caixiaowei
     * @updateTime 2021/2/25 15:09
     */
    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
