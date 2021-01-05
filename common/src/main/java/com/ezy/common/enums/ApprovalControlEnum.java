package com.ezy.common.enums;

import org.apache.commons.lang3.EnumUtils;

import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName ApprovalControlEnum.java
 * @Description 审批控件枚举
 * @createTime 2020年07月30日 10:41:00
 */
public enum ApprovalControlEnum {

    TEXT("Text", "文本"),

    TEXTAREA("Textarea", "多行文本"),

    NUMBER("Number", "数字"),

    MONEY("Money", "金钱"),

    DATE("Date", "日期"),

    DATE_RANGE("DateRange", "时长"),

    SELECTOR("Selector", "选择"),

    CONTACT("Contact", "成员/部门"),

    LOCATION("Location", "位置"),

    FILE("File", "附件");

    private String control;

    private String desc;

    public static List<ApprovalControlEnum> list = EnumUtils.getEnumList(ApprovalControlEnum.class);

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    ApprovalControlEnum(String control, String desc) {
        this.control = control;
        this.desc = desc;
    }
}
