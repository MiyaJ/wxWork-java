package com.ezy.message.model.callback.approval;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName Comments.java
 * @Description 审批申请备注信息
 * @createTime 2020年07月07日 15:40:00
 */
@Data
@XStreamAlias("Comments")
public class Comments implements Serializable {
    private static final long serialVersionUID = -1226692736650265430L;

    /**
     * 备注人信息
     */
    @XStreamAlias("CommentUserInfo")
    private CommentUserInfo commentUserInfo;

    /**
     * 备注提交时间
     */
    @XStreamAlias("CommentTime")
    private Integer commentTime;

    /**
     * 备注文本内容
     */
    @XStreamAlias("CommentContent")
    private String commentContent;

    /**
     * 备注id
     */
    @XStreamAlias("CommentId")
    private String commentId;


}
