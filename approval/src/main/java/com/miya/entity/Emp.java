package com.miya.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.miya.model.contact.WxUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

/**
 * <p>
 * 员工信息
 * </p>
 *
 * @author Caixiaowei
 * @since 2021-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_emp")
@NoArgsConstructor
@AllArgsConstructor
public class Emp implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 员工名称
     */
    private String name;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 性别。0表示未定义，1表示男性，2表示女性
     */
    private Integer gender;

    /**
     * 头像url
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 企业微信成员UserID
     */
    private String qywxUserId;

    /**
     * 删除标识:0-未删除; 1-已删除
     */
    private Boolean isDeleted;

    @TableField(exist = false)
    private List<Long> department;
    @TableField(exist = false)
    private List<Integer> isLeaderInDept;
    @TableField(exist = false)
    private List<Long> sort;
    @TableField(exist = false)
    private Long mainDepartment;

    public Emp(WxUser wxUser) {
        this.avatar = wxUser.getAvatar();
        this.email = wxUser.getEmail();
        this.gender = wxUser.getGender();
        this.isDeleted = Boolean.FALSE;
        this.mobile = wxUser.getMobile();
        this.name = wxUser.getName();
        this.qywxUserId = wxUser.getUserId();
        this.department = wxUser.getDepartment();
        this.isLeaderInDept = wxUser.getIsLeaderInDept();
        this.sort = wxUser.getOrder();
        this.mainDepartment = wxUser.getMainDepartment();
    }
}
