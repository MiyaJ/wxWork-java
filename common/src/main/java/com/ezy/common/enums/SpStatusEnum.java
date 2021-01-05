package com.ezy.common.enums;

import org.apache.commons.lang3.EnumUtils;

import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName SpStatusEnum.java
 * @Description 审核状态枚举
 * @createTime 2020年07月17日 15:54:00
 */
public enum SpStatusEnum {

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
     * 已转审
     */
    REFERRED(4, "已转审");

    private Integer status;

    private String desc;

    public static List<SpStatusEnum> list = EnumUtils.getEnumList(SpStatusEnum.class);

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

    public static List<SpStatusEnum> getList() {
        return list;
    }

    public static void setList(List<SpStatusEnum> list) {
        SpStatusEnum.list = list;
    }

    SpStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static String getDesc(int status) {
        for (SpStatusEnum e : list) {
            if (e.status.intValue() == status) {
                return e.desc;
            }
        }
        return null;
    }

    public static Integer getStatus(String desc){
        for (SpStatusEnum e : list) {
            if (e.getDesc().equals(desc)){
                return e.getStatus();
            }
        }
        return null;
    }

//    public static List<IdNameDTO> idNameList(){
//        List<IdNameDTO> result = new ArrayList<>();
//        for (ChannelLevelEnum empStatusTypeEnum : list) {
//            IdNameDTO idNameDTO = new IdNameDTO();
//            idNameDTO.setId(empStatusTypeEnum.getType().toString());
//            idNameDTO.setName(empStatusTypeEnum.getName());
//            result.add(idNameDTO);
//        }
//        return result;
//    }
}
