package com.ezy.message.model.callback.approval;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName Notifyer.java
 * @Description 抄送信息
 * @createTime 2020年07月07日 15:39:00
 */
@Data
@XStreamAlias("Notifyer")
public class Notifyer implements Serializable {
    private static final long serialVersionUID = -3351359753949948885L;

    /**
     * 节点抄送人userid
     */
    @XStreamAlias("UserId")
    private String userId;
}
