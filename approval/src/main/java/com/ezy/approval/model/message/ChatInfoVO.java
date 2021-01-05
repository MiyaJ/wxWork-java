package com.ezy.approval.model.message;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName ChatInfoVO.java
 * @Description TODO
 * @createTime 2020年06月28日 15:22:00
 */
@Data
public class ChatInfoVO implements Serializable {
    private static final long serialVersionUID = 8072638427535180900L;


    /**
     * chatid : CHATID
     * name : NAME
     * owner : userid2
     * userlist : ["userid1","userid2","userid3"]
     */

    /**
     * 群聊id
     */
    private String chatid;

    /**
     * 群名称
     */
    private String name;

    /**
     * 群主
     */
    private String owner;

    /**
     * 群成员列表
     */
    private List<String> userlist;

}
