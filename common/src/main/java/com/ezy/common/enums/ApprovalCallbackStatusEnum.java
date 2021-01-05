package com.ezy.common.enums;

import cn.hutool.core.collection.CollUtil;
import com.ezy.common.model.IdNameVO;
import org.apache.commons.lang3.EnumUtils;

import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName SpStatusEnum.java
 * @Description 审核状态枚举
 * @createTime 2020年07月17日 15:54:00
 */
public enum ApprovalCallbackStatusEnum {

    // 1-审批中；2-已通过；3-已驳回；4-已撤销；6-通过后撤销；7-已删除；10-已支付

    /**
     * 回调失败
     */
    FAIL(0, "回调失败"),

    /**
     * 回调成功
     */
    SUCCESS(1, "回调成功");

    private Integer status;

    private String desc;

    public static List<ApprovalCallbackStatusEnum> list = EnumUtils.getEnumList(ApprovalCallbackStatusEnum.class);

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

    public static List<ApprovalCallbackStatusEnum> getList() {
        return list;
    }

    public static void setList(List<ApprovalCallbackStatusEnum> list) {
        ApprovalCallbackStatusEnum.list = list;
    }

    ApprovalCallbackStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static String getDesc(int status) {
        for (ApprovalCallbackStatusEnum e : list) {
            if (e.status.intValue() == status) {
                return e.desc;
            }
        }
        return null;
    }

    public static Integer getStatus(String desc){
        for (ApprovalCallbackStatusEnum e : list) {
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
        for (ApprovalCallbackStatusEnum approvalStatusEnum : ApprovalCallbackStatusEnum.values()) {
            IdNameVO idName = new IdNameVO();
            idName.setId(approvalStatusEnum.getStatus());
            idName.setName(approvalStatusEnum.getDesc());
            list.add(idName);
        }
        return list;
    }

}
