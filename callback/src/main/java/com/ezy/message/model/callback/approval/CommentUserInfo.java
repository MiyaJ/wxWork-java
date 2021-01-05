package com.ezy.message.model.callback.approval;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName CommentUserInfo.java
 * @Description 备注人信息
 * @createTime 2020年07月08日 11:00:00
 */
@Data
@XStreamAlias("CommentUserInfo")
public class CommentUserInfo implements Serializable {
    private static final long serialVersionUID = -7145115175217292552L;

    /**
     * 备注人userid
     */
    @XStreamAlias("UserId")
    private String userId;
}
