package com.ezy.approval.model.message;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName GroupChatCreateDTO.java
 * @Description 修改群聊dto
 * @createTime 2020年06月28日 14:26:00
 */
@Data
public class GroupChatUpdateDTO implements Serializable {
    private static final long serialVersionUID = -25823511462923207L;

    /**
     * 群聊id
     */
    private String chatid;

    /**
     * 新的群聊名。若不需更新，请忽略此参数。最多50个utf8字符，超过将截断
     */
    private String name;

    /**
     * 新群主的id。若不需更新，请忽略此参数
     */
    private String owner;

    /**
     * 添加成员的id列表
     */
    private List<String> add_user_list;

    /**
     * 踢出成员的id列表
     */
    private List<String> del_user_list;
}
