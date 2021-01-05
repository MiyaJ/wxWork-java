package com.ezy.approval.handler;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ezy.approval.entity.ApprovalApply;
import com.ezy.approval.entity.ApprovalTemplateSystem;
import com.ezy.approval.model.message.MsgVO;
import com.ezy.approval.model.message.personal.TextMsg;
import com.ezy.approval.service.IApprovalApplyService;
import com.ezy.approval.service.IApprovalTemplateSystemService;
import com.ezy.approval.service.IMessageService;
import com.ezy.approval.utils.OkHttpClientUtil;
import com.ezy.common.enums.ApprovalCallbackStatusEnum;
import com.ezy.common.enums.ApprovalStatusEnum;
import com.ezy.common.enums.MsgTypeEnum;
import com.ezy.common.model.CommonResult;
import com.ezy.common.model.ResultCode;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Caixiaowei
 * @ClassName NoticeHandler
 * @Description 通知处理器
 * @createTime 2020/9/17$ 10:12$
 */
@Service
@Slf4j
public class NoticeHandler {

    @Value("${qywx.msg-agentid:1000002}")
    private int MESSAGE_AGENT_ID;
    @Value("${admin.qw-userid}")
    private String ADMIN_QW_USER_ID;

    @Autowired
    private IApprovalApplyService approvalApplyService;
    @Autowired
    private IApprovalTemplateSystemService approvalTemplateSystemService;
    @Autowired
    private IMessageService messageService;

    /**
     * 审批结果回调通知调用方
     *
     * @param spNo 审批单编号
     * @return void
     * @author Caixiaowei
     * @updateTime 2020/9/14 15:04
     */
    @Async
    public void approvalResultCallback(String spNo) {
        ApprovalApply apply = approvalApplyService.getApprovalApply(spNo);
        if (apply == null) {
            return;
        }
        Integer status = apply.getStatus();
        String templateId = apply.getTemplateId();
        String systemCode = apply.getSystemCode();

        // 审批流程结束, 通知对应企微联系人
        QueryWrapper<ApprovalTemplateSystem> wrapper = new QueryWrapper<>();
        wrapper.eq("system_code", systemCode);
        wrapper.eq("template_id", templateId);
        ApprovalTemplateSystem approvalTemplateSystem = approvalTemplateSystemService.getOne(wrapper);
        if (approvalTemplateSystem != null) {
            String qwContactPerson = approvalTemplateSystem.getQwContactPerson();
            if (StrUtil.isNotEmpty(qwContactPerson)) {
                String content = "单据编号: " + spNo + "\n"
                        + "审批结果: " + ApprovalStatusEnum.getDesc(status);
                this.notifyText(qwContactPerson, content);
            }
        }

        // 回调通知调用方
        String callbackUrl = apply.getCallbackUrl();
        if (StrUtil.isNotEmpty(callbackUrl)) {
            try {
                Map<String, String> params = Maps.newHashMap();
                params.put("spNo", spNo);
                params.put("status", String.valueOf(status));
                String doGet = OkHttpClientUtil.doGet(callbackUrl, null, params);


                CommonResult commonResult = JSONObject.parseObject(doGet, CommonResult.class);
                if (commonResult != null && commonResult.getCode() == ResultCode.SUCCESS.getCode()) {
                    apply.setCallbackStatus(ApprovalCallbackStatusEnum.SUCCESS.getStatus());
                } else {
                    apply.setCallbackStatus(ApprovalCallbackStatusEnum.FAIL.getStatus());

                    // 回调失败, 企微通知审批管理员
                    String content = "单据编号: " + spNo + "\n"
                            + "审批结果: " + ApprovalStatusEnum.getDesc(status) + "\n"
                            + "回调结果: 失败 \n"
                            + "失败原因: " + commonResult.getMessage();
                    this.notifyText(ADMIN_QW_USER_ID, content);
                }
                // 更新回调结果到审批单据
                apply.setCallbackResult(doGet);
                approvalApplyService.updateById(apply);
            } catch (Exception e) {
                log.error("审批结果回调通知调用方 错误, 审批单号: {}, 异常:{}", spNo, e);
            }
        }
    }

    /**
     * 消费失败消息通知
     *
     * @param
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/18 13:26
     */
    public void consumerFail(String spNo) {
        String qwContactPerson = StrUtil.EMPTY;

        ApprovalApply apply = approvalApplyService.getApprovalApply(spNo);
        if (apply != null) {
            String templateId = apply.getTemplateId();
            String systemCode = apply.getSystemCode();

            QueryWrapper<ApprovalTemplateSystem> wrapper = new QueryWrapper<>();
            wrapper.eq("system_code", systemCode);
            wrapper.eq("template_id", templateId);
            ApprovalTemplateSystem approvalTemplateSystem = approvalTemplateSystemService.getOne(wrapper);
            if (approvalTemplateSystem != null) {
                qwContactPerson = approvalTemplateSystem.getQwContactPerson();
            }
        } else {
            return;
        }

        if (StrUtil.isNotEmpty(qwContactPerson)) {
            try {
                // 企微通知对应系统联系人
                String content = "单据编号: " + spNo + "\n"
                        + "审批情况: MQ消费失败";
                this.notifyText(qwContactPerson, content);
            } catch (Exception e) {
                log.error("消费失败消息通知 错误, 审批单号: {}, 异常:{}", spNo, e);
            }
        }
    }

    /**
     * 发送文本消息
     *
     * @param toUser 通知目标
     * @param content 通知内容
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/21 15:47
     */
    public void notifyText(String toUser, String content) {
        String errmsg = StrUtil.EMPTY;
        try {
            TextMsg textMsg = new TextMsg();
            textMsg.setTouser(toUser);
            textMsg.setToparty(StringUtils.EMPTY);
            textMsg.setTotag(StringUtils.EMPTY);
            textMsg.setMsgtype(MsgTypeEnum.TEXT.getValue());
            textMsg.setAgentid(MESSAGE_AGENT_ID);
            textMsg.setText(new TextMsg.TextBean(content));
            textMsg.setSafe(0);
            textMsg.setEnable_id_trans(0);
            textMsg.setEnable_duplicate_check(0);
            textMsg.setDuplicate_check_interval(1800);

            MsgVO msgVO = this.messageService.sendTextMsg(textMsg);
            errmsg = msgVO.getErrmsg();
        } catch (Exception e) {
            log.error("消息通知异常, 通知对象: {}, 通知内容:{}, 异常: {}", toUser, content, errmsg);
        }
    }

    /**
     * 尝试会调通知3次失败, 通知管理员
     *
     * @param spNo 审批单据
     * @param status 审批状态
     * @param message 回调返回消息
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/23 13:49
     */
    public void retryCallback(String spNo, Integer status, String message) {
        String content = "单据编号: " + spNo + "\n"
                + "审批结果: " + ApprovalStatusEnum.getDesc(status) + "\n"
                + "回调结果: 失败 \n"
                + "失败原因: " + message;
        this.notifyText(ADMIN_QW_USER_ID, content);
    }

    /**
     * 尝试消费3次失败, 通知管理员
     *
     * @param spNo 审批单据
     * @param spNo 消息id
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/23 13:48
     */
    public void retryConsume(String spNo, String messageId) {
        String content = "单据编号: " + spNo + "\n"
                + "消息id: " + messageId + "\n"
                + "消费失败";
        this.notifyText(ADMIN_QW_USER_ID, content);
    }
}
