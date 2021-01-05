package com.ezy.approval.model.message;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName GroupChatCreateDTO.java
 * @Description 创建群聊dto
 * @createTime 2020年06月28日 14:26:00
 */
@Data
public class GroupChatCreateDTO implements Serializable {
    private static final long serialVersionUID = -25823511462923207L;

    /**
     * 群名称. 必填
     */
    private String name;

    /**
     * 群主, 非必填
     */
    private String owner;

    /**
     * 群成员id列表, 必填至少2人
     */
    private List<String> userlist;

    /**
     * 群id,非必填会随机生成
     */
    private String chatid;

    /**
     * 添加成员id列表
     */
    private List<String> addUserList;

    /**
     * 删除成员列表
     */
    private List<String> delUserList;
}
