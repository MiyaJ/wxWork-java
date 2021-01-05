package com.ezy.approval.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 审批模板控件信息表
 * </p>
 *
 * @author CaiXiaowei
 * @since 2020-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_approval_template_control")
public class ApprovalTemplateControl implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 模板id
     */
    private String templateId;

    /**
     * 控件：Text-文本；Textarea-多行文本；Number-数字；Money-金额；Date-日期/日期+时间；Selector-单选/多选；Contact-成员/部门；Tips-说明文字；File-附件；Table-明细；Attendance-假勤控件；Vacation-请假控件
     */
    private String control;

    /**
     * 控件id
     */
    private String controlId;

    /**
     * 控件名称
     */
    private String title;

    /**
     * 类型
     */
    private String type;

    /**
     * 控件说明
     */
    private String placeholder;

    /**
     * 是否必填：1-必填；0-非必填
     */
    private Integer required;

    /**
     * 是否参与打印：1-不参与打印；0-参与打印
     */
    private Integer unPrint;

    /**
     * 模板控件配置，包含了部分控件类型的附加类型、属性，详见附录说明
     */
    private String config;


}
