package com.ezy.common.enums;

import lombok.Data;
import org.apache.commons.lang3.EnumUtils;

import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName ApprovalCodeEnum.java
 * @Description 审批申请错误码
 * @createTime 2020年07月29日 16:25:00
 */
public enum ApprovalCodeEnum {

    /**
     * 无权限
     */
    NO_PERMISSION(301055, "无审批应用权限,或者提单者不在审批应用/自建应用的可见范围"),

    /**
     * 已停用
     */
    TERMINATED(301056, "审批应用已停用"),

    /**
     * 参数错误
     */
    PARAMETER_ERROR(301025, "提交审批单请求参数错误"),

    /**
     * 调用接口失败
     */
    INTERFACE_FAILED(301057, "通用错误码，提交审批单内部接口失败");

    private Integer code;

    private String desc;

    public static List<ApprovalCodeEnum> list = EnumUtils.getEnumList(ApprovalCodeEnum.class);

    ApprovalCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static List<ApprovalCodeEnum> getList() {
        return list;
    }

    public static void setList(List<ApprovalCodeEnum> list) {
        ApprovalCodeEnum.list = list;
    }

    public static String getDesc(Integer code) {
        for (ApprovalCodeEnum e : list) {
            if (e.code.intValue() == code) {
                return e.desc;
            }
        }
        return null;
    }

}
