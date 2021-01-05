package com.miya.enums;

import cn.hutool.core.collection.CollUtil;
import com.miya.model.IdNameVO;
import org.apache.commons.lang3.EnumUtils;

import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName SpStatusEnum.java
 * @Description 审核状态枚举
 * @createTime 2020年07月17日 15:54:00
 */
public enum ApprovalStatusEnum {

    // 1-审批中；2-已通过；3-已驳回；4-已撤销；6-通过后撤销；7-已删除；10-已支付

    /**
     * 申请失败
     */
    FAIL(0, "申请失败"),

    /**
     * 审批中
     */
    IN_REVIEW(1, "审批中"),

    /**
     * 已同意
     */
    APPROVED(2, "已同意"),

    /**
     * 已驳回
     */
    DISMISSED(3, "已驳回"),

    /**
     * 已撤销
     */
    REVOKED(4, "已撤销"),

    /**
     * 通过后撤销
     */
    APPROVED_REVOKED(6, "通过后撤销"),

    /**
     * 已删除
     */
    DELETED(7, "已删除"),

    /**
     * 已支付
     */
    PAID(10, "已支付");

    private Integer status;

    private String desc;

    public static List<ApprovalStatusEnum> list = EnumUtils.getEnumList(ApprovalStatusEnum.class);

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

    public static List<ApprovalStatusEnum> getList() {
        return list;
    }

    public static void setList(List<ApprovalStatusEnum> list) {
        ApprovalStatusEnum.list = list;
    }

    ApprovalStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static String getDesc(int status) {
        for (ApprovalStatusEnum e : list) {
            if (e.status.intValue() == status) {
                return e.desc;
            }
        }
        return null;
    }

    public static Integer getStatus(String desc){
        for (ApprovalStatusEnum e : list) {
            if (e.getDesc().equals(desc)){
                return e.getStatus();
            }
        }
        return null;
    }

    /**
     * 将枚举转成id-name形式
     * @return id-name集合
     */
    public static List<IdNameVO> idNameList(){
        List<IdNameVO> list= CollUtil.newArrayList();
        for (ApprovalStatusEnum approvalStatusEnum : ApprovalStatusEnum.values()) {
            IdNameVO idName = new IdNameVO();
            idName.setId(approvalStatusEnum.getStatus());
            idName.setName(approvalStatusEnum.getDesc());
            list.add(idName);
        }
        return list;
    }

}
