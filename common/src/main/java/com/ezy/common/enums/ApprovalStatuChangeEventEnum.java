package com.ezy.common.enums;

import org.apache.commons.lang3.EnumUtils;

import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName SpStatusEnum.java
 * @Description 审批申请状态变化类型
 * @createTime 2020年07月17日 15:54:00
 */
public enum ApprovalStatuChangeEventEnum {

    // 审批申请状态变化类型：1-提单；2-同意；3-驳回；4-转审；5-催办；6-撤销；8-通过后撤销；10-添加备注


    /**
     * 提单
     */
    APPLY(1, "提单"),

    /**
     * 同意
     */
    APPROVE(2, "同意"),

    /**
     * 驳回
     */
    DISMISSE(3, "驳回"),

    /**
     * 转审
     */
    REFERRAL(4, "转审"),

    /**
     * 催办
     */
    URGE(5, "催办"),

    /**
     * 已撤销
     */
    REVOKE(6, "已撤销"),

    /**
     * 通过后撤销
     */
    APPROVED_REVOKE(8, "通过后撤销"),

    /**
     * 添加备注
     */
    ADD_COMMENT(10, "添加备注");

    private Integer status;

    private String desc;

    public static List<ApprovalStatuChangeEventEnum> list = EnumUtils.getEnumList(ApprovalStatuChangeEventEnum.class);

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static List<ApprovalStatuChangeEventEnum> getList() {
        return list;
    }

    public static void setList(List<ApprovalStatuChangeEventEnum> list) {
        ApprovalStatuChangeEventEnum.list = list;
    }

    ApprovalStatuChangeEventEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static String getDesc(int status) {
        for (ApprovalStatuChangeEventEnum e : list) {
            if (e.status.intValue() == status) {
                return e.desc;
            }
        }
        return null;
    }

    public static Integer getStatus(String desc){
        for (ApprovalStatuChangeEventEnum e : list) {
            if (e.getDesc().equals(desc)){
                return e.getStatus();
            }
        }
        return null;
    }

}
