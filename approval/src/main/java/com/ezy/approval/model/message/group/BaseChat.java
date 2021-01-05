package com.ezy.approval.model.message.group;

import lombok.Data;

/**
 * @author Caixiaowei
 * @ClassName BaseChat.java
 * @Description 群聊基础信息
 * @createTime 2020年06月28日 16:56:00
 */
@Data
public class BaseChat {

    /**
     * 群聊id
     */
    private String chatid;

    /**
     * 消息类型
     */
    private String msgtype;

    /**
     * 表示是否是保密消息，0表示否，1表示是，默认0
     */
    private int safe;
}
