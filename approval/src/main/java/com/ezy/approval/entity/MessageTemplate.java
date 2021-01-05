package com.ezy.approval.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 消息模板 
 * </p>
 *
 * @author CaiXiaowei
 * @since 2020-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_message_template")
public class MessageTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 消息类型: text-文本消息; image-图片消息; voice-语音消息; video-视频消息; file-文件消息; textcard-文本卡片消息;
     * news-图文消息; mpnews-图文消息; markdown-markdown消息; miniprogram_notice-小程序消息; taskcard-任务卡消息'
     *
     */
    private String type;

    /**
     * 模板内容
     */
    private String content;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态: 启用/未启用
     */
    private Boolean status;

    /**
     * 删除标识: 0未删除; 1 已删除
     */
    private Boolean isDeleted;

    private String requestParam;

    private String response;


}
