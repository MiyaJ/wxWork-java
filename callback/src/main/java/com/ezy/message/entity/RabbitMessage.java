package com.ezy.message.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * rabbit mq 消息
 * </p>
 *
 * @author Caixiaowei
 * @since 2020-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_rabbit_message")
public class RabbitMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String MESSAGE_TYPE_APPROVAL = "approval";
    public static final String MESSAGE_TYPE_CONTACT = "contact";

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 消息id
     */
    private String messageId;

    /**
     * 消息类型
     */
    private String type;

    /**
     * 审批单编号
     */
    private String spNo;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 是否已发送: 0 false 未发送(默认); 1 true 已发送
     */
    private Boolean isSend;

    /**
     * 是否已消费: 0 false 未消费(默认); 1 true 已消费
     */
    private Boolean isConsumed;

    /**
     * 消息产生时间
     */
    private Long createTime;

    /**
     * 是否已删除: 0 false 未删除(默认); 1 true 已删除
     */
    private Boolean isDeleted;

}
