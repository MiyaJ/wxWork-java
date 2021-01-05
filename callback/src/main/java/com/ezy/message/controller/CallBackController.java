package com.ezy.message.controller;

import cn.hutool.http.Method;
import com.alibaba.fastjson.JSONObject;
import com.ezy.message.config.RabbitConfig;
import com.ezy.message.entity.RabbitMessage;
import com.ezy.message.model.callback.QwEvent;
import com.ezy.message.model.callback.approval.*;
import com.ezy.message.model.callback.contact.Contact;
import com.ezy.message.model.callback.contact.ExtAttr;
import com.ezy.message.model.callback.contact.Item;
import com.ezy.message.model.callback.contact.ItemText;
import com.ezy.message.reception.Producer;
import com.ezy.message.service.IRabbitMessageService;
import com.ezy.message.utils.QywxCallBackUtils;
import com.ezy.message.utils.aes.WXBizMsgCrypt;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Caixiaowei
 * @ClassName CallBackController.java
 * @Description 企微callback 控制层
 * @createTime 2020年07月31日 10:06:00
 */
@RestController
@RequestMapping("/v1/qywx/callback")
@Slf4j
public class CallBackController {

    @Autowired
    private Producer producer;

    @Value("${qywx.approval.token}")
    private String APPROVAL_TOKEN;
    @Value("${qywx.approval.encodingAESKey}")
    private String APPROVAL_ENCODING_AES_KEY;
    @Value("${qywx.approval.secret}")
    private String APPROVAL_SECRET;
    @Value("${qywx.contact.token}")
    private String CONTACT_TOKEN;
    @Value("${qywx.contact.encodingAESKey}")
    private String CONTACT_ENCODING_AES_KEY;
    @Value("${qywx.contact.secret}")
    private String CONTACT_SECRET;
    @Value("${qywx.corpid}")
    private String corpid;

    @Autowired
    private IRabbitMessageService rabbitMessageService;

    /**
     * 审批状态变更回调通知
     * @description 
     * @author Caixiaowei
     * @param
     * @updateTime 2020/8/3 15:24 
     * @return 
     */
    @RequestMapping("/approval")
    public String approval(HttpServletRequest request) {

        String msgSig = request.getParameter("msg_signature");
        String timeStamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        log.info("msgSig:"+msgSig+"======timeStamp:"+timeStamp+"=====nonce:"+nonce+"=====echostr:"+echostr);
        if (Method.GET.name().equalsIgnoreCase(request.getMethod())){
            WXBizMsgCrypt wxcpt = wxcptApproval();
            String sEchoStr; //需要返回的明文
            try {
                sEchoStr = wxcpt.VerifyURL(msgSig, timeStamp, nonce, echostr);
                return sEchoStr;
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage()+"========解析微信校验数据异常");
                throw new RuntimeException("解析微信校验数据异常");
            }
        } else{
            WXBizMsgCrypt wxcpt = wxcptApproval();
            String data = QywxCallBackUtils.getStringInputstream(request);
            log.info("post请求数据: {}", data);
            try {
                String content = wxcpt.DecryptMsg(msgSig,timeStamp,nonce,data);
                log.info("解析后的消息: {}", content);


                XStream xStream = new XStream();
                xStream.alias("xml", QwEvent.class);
                xStream.ignoreUnknownElements();
                xStream.autodetectAnnotations(true);
                xStream.setClassLoader(QwEvent.class.getClassLoader());
                QwEvent qwEvent = (QwEvent)xStream.fromXML(content);
                String event = qwEvent.getEvent();

                log.info("qw--->callback--->event: {}", event);
                if ("sys_approval_change".equalsIgnoreCase(event)) {
                    XStream xstream = new XStream();
                    xstream.ignoreUnknownElements();
                    xstream.autodetectAnnotations(true);
                    //使用注解修改对象名称
                    xstream.processAnnotations(new Class[]{Applyer.class, ApprovalInfo.class, Comments.class,
                            CommentUserInfo.class, Details.class, Notifyer.class, SpRecord.class,
                            ApprovalStatuChangeEvent.class});
                    ApprovalStatuChangeEvent callbackMessage = (ApprovalStatuChangeEvent) xstream.fromXML(content);
                    Long createTime = callbackMessage.getCreateTime();
                    // TODO: 2020/7/1 回调信息返回调用方: mq?
                    log.info("approval--->callbackMessage--->{}", JSONObject.toJSONString(callbackMessage));
                    String spNo = callbackMessage.getApprovalInfo().getSpNo();
                    // 消息入库
                    RabbitMessage rabbitMessage = new RabbitMessage();
                    rabbitMessage.setType(RabbitMessage.MESSAGE_TYPE_APPROVAL);
                    rabbitMessage.setSpNo(spNo);
                    rabbitMessage.setContent(JSONObject.toJSONString(callbackMessage));
                    rabbitMessage.setIsSend(true);
                    rabbitMessage.setIsConsumed(false);
                    rabbitMessage.setCreateTime(createTime);
                    rabbitMessage.setIsDeleted(false);
                    rabbitMessageService.save(rabbitMessage);

                    producer.send(RabbitConfig.QUEUE_APPROVAL, rabbitMessage.getId(), JSONObject.toJSONString(callbackMessage));
                }

                return "success";
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage()+"========解析微信校验数据异常");
                throw new RuntimeException("解析微信校验数据异常");
            }
        }

    }

    /**
     * 通讯录回调通知
     * @description
     * @author Caixiaowei
     * @param
     * @updateTime 2020/8/3 15:24
     * @return
     */
    @RequestMapping("/contact")
    public String contact(HttpServletRequest request) {

        String msgSig = request.getParameter("msg_signature");
        String timeStamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        log.info("msgSig:"+msgSig+"======timeStamp:"+timeStamp+"=====nonce:"+nonce+"=====echostr:"+echostr);
        try {
            WXBizMsgCrypt wxcpt = wxcptContact();
            if (Method.GET.name().equalsIgnoreCase(request.getMethod())){

                //需要返回的明文
                String sEchoStr;
                sEchoStr = wxcpt.VerifyURL(msgSig, timeStamp, nonce, echostr);
                return sEchoStr;

            }else{
                String data = QywxCallBackUtils.getStringInputstream(request);
                log.info("post请求数据: {}", data);
                String content = wxcpt.DecryptMsg(msgSig,timeStamp,nonce,data);
                log.info("解析后的消息: {}", content);

                XStream xstream = new XStream();
                xstream.processAnnotations(new Class[]{Contact.class, ExtAttr.class, Item.class, ItemText.class});
                // 解析通讯类
                Contact callbackMessage = (Contact) xstream.fromXML(content);
                log.info("contact--->callbackMessage--->{}", JSONObject.toJSONString(callbackMessage));
                Long createTime = callbackMessage.getCreateTime();
                // TODO: 2020/7/1 回调信息返回调用方: mq?
                // 消息入库
                RabbitMessage rabbitMessage = new RabbitMessage();
                rabbitMessage.setType(RabbitMessage.MESSAGE_TYPE_CONTACT);
                rabbitMessage.setContent(JSONObject.toJSONString(callbackMessage));
                rabbitMessage.setIsSend(true);
                rabbitMessage.setIsConsumed(false);
                rabbitMessage.setCreateTime(createTime);
                rabbitMessage.setIsDeleted(false);
                rabbitMessageService.save(rabbitMessage);
                producer.send(RabbitConfig.EXCHANGE_CONTACT, RabbitConfig.ROUTING_KEY_CONTACT, rabbitMessage.getId(), JSONObject.toJSONString(callbackMessage));
                return "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("解析微信校验数据异常: {}", e.getMessage());
            throw new RuntimeException("解析微信校验数据异常");
        }

    }

    private WXBizMsgCrypt wxcptApproval(){
        WXBizMsgCrypt wxcpt = null;
        try {
            wxcpt = new WXBizMsgCrypt(APPROVAL_TOKEN, APPROVAL_ENCODING_AES_KEY, corpid);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("解析微信校验数据异常: {}", e.getMessage());
            throw new RuntimeException("企业微信校验异常");
        }
        return wxcpt;
    }

    private WXBizMsgCrypt wxcptContact(){
        WXBizMsgCrypt wxcpt = null;
        try {
            wxcpt = new WXBizMsgCrypt(CONTACT_TOKEN, CONTACT_ENCODING_AES_KEY, corpid);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("解析微信校验数据异常: {}", e.getMessage());
            throw new RuntimeException("企业微信校验异常");
        }
        return wxcpt;
    }

}
