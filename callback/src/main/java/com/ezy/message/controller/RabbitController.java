package com.ezy.message.controller;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSONObject;
import com.ezy.message.config.RabbitConfig;
import com.ezy.message.entity.RabbitMessage;
import com.ezy.message.model.callback.approval.ApprovalStatuChangeEvent;
import com.ezy.message.reception.Producer;
import com.ezy.message.service.IRabbitMessageService;
import com.ezy.message.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Caixiaowei
 * @ClassName RabbitController.java
 * @Description TODO
 * @createTime 2020年07月30日 17:54:00
 */
@RestController
@RequestMapping("/rabbit")
@Slf4j
public class RabbitController {

    @Autowired
    private Producer producer;
    @Autowired
    private IRabbitMessageService rabbitMessageService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send")
    public Object send() {
        ApprovalStatuChangeEvent callbackMessage = new ApprovalStatuChangeEvent();
        Long createTime = System.currentTimeMillis() / 100;
        callbackMessage.setCreateTime(createTime);
//        // TODO: 2020/7/1 回调信息返回调用方: mq?
//        log.info("callbackMessage: {}", JSONObject.toJSONString(callbackMessage));
//        // 消息入库
//        RabbitMessage rabbitMessage = new RabbitMessage();
//        rabbitMessage.setType(RabbitMessage.MESSAGE_TYPE_APPROVAL);
//        rabbitMessage.setContent(JSONObject.toJSONString(callbackMessage));
//        rabbitMessage.setIsSend(false);
//        rabbitMessage.setIsConsumed(false);
//        rabbitMessage.setCreateTime(createTime);
//        rabbitMessage.setIsDeleted(false);
//        rabbitMessageService.save(rabbitMessage);

        String messageId = UUID.randomUUID().toString();
        try {
            producer.send(RabbitConfig.QUEUE_APPROVAL, 1L, JSONObject.toJSONString(callbackMessage));
        } catch (AmqpException e) {
            log.error("重连 rabbit MQ 超过10次, 发送失败");
        }
        return "success";
    }

    @GetMapping("/sendTest")
    public Object send(String queue, String content) {
        ApprovalStatuChangeEvent callbackMessage = new ApprovalStatuChangeEvent();
        Long createTime = System.currentTimeMillis() / 100;
        callbackMessage.setCreateTime(createTime);
        // TODO: 2020/7/1 回调信息返回调用方: mq?
        log.info("callbackMessage: {}", JSONObject.toJSONString(callbackMessage));
        // 消息入库
        RabbitMessage rabbitMessage = new RabbitMessage();
        rabbitMessage.setType(RabbitMessage.MESSAGE_TYPE_APPROVAL);
        rabbitMessage.setContent(JSONObject.toJSONString(callbackMessage));
        rabbitMessage.setIsSend(false);
        rabbitMessage.setIsConsumed(false);
        rabbitMessage.setCreateTime(createTime);
        rabbitMessage.setIsDeleted(false);
        rabbitMessageService.save(rabbitMessage);

        try {
            producer.send(RabbitConfig.QUEUE_APPROVAL, rabbitMessage.getId(), JSONObject.toJSONString(callbackMessage));
        } catch (AmqpException e) {
            log.error("重连 rabbit MQ 超过10次, 发送失败");
        }
        return "success";
    }

    @GetMapping("/sendMessage")
    public Object send(String message) {
        producer.sendMessage(UUID.randomUUID().toString() + System.currentTimeMillis());
        return "sucess";
    }

    @GetMapping(value = "/sendExchange")
    public Object sendExchange() {
        ApprovalStatuChangeEvent callbackMessage = new ApprovalStatuChangeEvent();
        Long createTime = System.currentTimeMillis() / 100;
        callbackMessage.setCreateTime(createTime);
        // 消息入库
        RabbitMessage rabbitMessage = new RabbitMessage();
        rabbitMessage.setType(RabbitMessage.MESSAGE_TYPE_APPROVAL);
        rabbitMessage.setContent(JSONObject.toJSONString(callbackMessage));
        rabbitMessage.setIsSend(true);
        rabbitMessage.setIsConsumed(false);
        rabbitMessage.setCreateTime(createTime);
        rabbitMessage.setIsDeleted(false);
        rabbitMessageService.save(rabbitMessage);

        String messageId = String.valueOf(rabbitMessage.getId());
        Message message = MessageBuilder.withBody(JSONObject.toJSONString(callbackMessage).getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON).setContentEncoding("utf-8")
                .setMessageId(messageId).build();

        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(messageId);
        correlationData.setReturnedMessage(message);
        try {
            rabbitTemplate.send(RabbitConfig.EXCHANGE_APPROVAL + "1", RabbitConfig.ROUTING_KEY_CONTACT, message, correlationData);
        } catch (Exception e) {
            return "fail";
        }
        return "success";
    }
}
