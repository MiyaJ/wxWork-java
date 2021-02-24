package com.miya.model.contact;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName WxUser
 * @Description 企业微信成员
 * @createTime 2021/2/24 14:55
 */
@Data
public class WxUser implements Serializable {
    private static final long serialVersionUID = 9189417382725464317L;

    /**
     * 成员UserID
     */
    @JSONField(name = "userid")
    private String userId;

    /**
     * 成员名称
     */
    private String name;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 成员所属部门id列表
     */
    private List<Integer> department;

    /**
     * 部门内的排序值，默认为0
     */
    private List<Integer> order;

    /**
     * 性别。0表示未定义，1表示男性，2表示女性
     */
    private Integer gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 表示在所在的部门内是否为上级
     */
    @JSONField(name = "is_leader_in_dept")
    private List<Integer> isLeaderInDept;

    /**
     * 头像url
     */
    private String avatar;

    /**
     * 激活状态: 1=已激活，2=已禁用，4=未激活，5=退出企业。
     */
    private Integer status;

    /**
     * 全局唯一。对于同一个服务商，不同应用获取到企业内同一个成员的open_userid是相同的
     */
    @JSONField(name = "open_userid")
    private String openUserid;

    /**
     * 主部门
     */
    @JSONField(name = "main_department")
    private Long mainDepartment;
}
