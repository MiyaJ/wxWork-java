package com.ezy.message.model.callback.approval;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName Applyer.java
 * @Description 审批申请人
 * @createTime 2020年07月07日 15:31:00
 */
@Data
@XStreamAlias("Applyer")
public class Applyer implements Serializable {
    private static final long serialVersionUID = 3335796497807965216L;

    /**
     * 申请人userid
     */
    @XStreamAlias("UserId")
    private String userId;

    /**
     * 申请人所在部门pid
     */
    @XStreamAlias("Party")
    private String party;
}
