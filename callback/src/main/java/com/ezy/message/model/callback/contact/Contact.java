package com.ezy.message.model.callback.contact;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName Contact.java
 * @Description 通讯录回调解析类
 * @createTime 2020年08月03日 14:04:00
 */
@Data
@XStreamAlias("xml")
public class Contact implements Serializable {
    private static final long serialVersionUID = 875529955182563340L;

    /******************** 共有属性 ***************************/

    /**
     * 企业微信CorpID
     */
    @XStreamAlias("ToUserName")
    private String toUserName;

    /**
     * 消息创建时间 （整型）
     */
    @XStreamAlias("FromUserName")
    private String fromUserName;

    /**
     * 消息创建时间 （整型）
     */
    @XStreamAlias("CreateTime")
    private Long createTime;

    /**
     * 消息的类型
     */
    @XStreamAlias("MsgType")
    private String msgType;

    /**
     * 事件的类型
     */
    @XStreamAlias("Event")
    private String event;

    /**
     * create_party, update_party, del_party  创建/更新/删除 部门
     * create_user, update_user, del_user  创建/更新/删除 成员
     */
    @XStreamAlias("ChangeType")
    private String changeType;

    /**
     * 成员/部门名称
     */
    @XStreamAlias("Name")
    private String name;

    /******************** 部门变更 ***************************/

    /**
     * 部门Id
     */
    @XStreamAlias("Id")
    private String id;

    /**
     * 父部门id
     */
    @XStreamAlias("ParentId")
    private String parentId;

    /**
     * 部门排序
     */
    @XStreamAlias("Order")
    private String order;

    /******************* 标签变更 *********************/
    /**
     * 标签Id
     */
    @XStreamAlias("TagId")
    private String tagId;

    /**
     * 标签中新增的成员userid列表，用逗号分隔
     */
    @XStreamAlias("AddUserItems")
    private String addUserItems;

    /**
     * 标签中删除的成员userid列表，用逗号分隔
     */
    @XStreamAlias("DelUserItems")
    private String delUserItems;

    /**
     * 标签中新增的部门id列表，用逗号分隔
     */
    @XStreamAlias("AddPartyItems")
    private String addPartyItems;

    /**
     * 标签中删除的部门id列表，用逗号分隔
     */
    @XStreamAlias("DelPartyItems")
    private String delPartyItems;

    /********************** 成员变更 ***************************/

    /**
     * 成员UserID
     */
    @XStreamAlias("UserID")
    private String userID;

    /**
     *
     */
    @XStreamAlias("NewUserID")
    private String newUserID;

    /**
     * 成员部门列表，仅返回该应用有查看权限的部门id
     */
    @XStreamAlias("Department")
    private String department;

    /**
     * 表示所在部门是否为上级，0-否，1-是，顺序与Department字段的部门逐一对应
     */
    @XStreamAlias("IsLeaderInDept")
    private String isLeaderInDept;

    /**
     * 职位信息
     */
    @XStreamAlias("Position")
    private String position;

    /**
     * 手机号码
     */
    @XStreamAlias("Mobile")
    private String mobile;

    /**
     * 性别，1表示男性，2表示女性
     */
    @XStreamAlias("Gender")
    private String gender;

    /**
     * 邮箱
     */
    @XStreamAlias("Email")
    private String email;

    /**
     * 激活状态：1=已激活 2=已禁用 4=未激活 已激活代表已激活企业微信或已关注微工作台（原企业号）。
     */
    @XStreamAlias("Status")
    private String status;

    /**
     * 头像url
     */
    @XStreamAlias("Avatar")
    private String avatar;

    /**
     * 成员别名
     */
    @XStreamAlias("Alias")
    private String alias;

    /**
     * 座机
     */
    @XStreamAlias("Telephone")
    private String telephone;

    /**
     * 地址
     */
    @XStreamAlias("Address")
    private String address;

    /**
     * 扩展属性
     */
    @XStreamAlias("ExtAttr")
    private ExtAttr extAttr;

    @XStreamAlias("IsLeader")
    private String isLeader;

    /**
     * 是否是主部门
     */
    @XStreamAlias("MainDepartment")
    private Integer mainDepartment;

}
