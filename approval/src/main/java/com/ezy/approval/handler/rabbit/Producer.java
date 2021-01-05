package com.ezy.approval.handler.rabbit;

import com.ezy.approval.config.RabbitConfig;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Caixiaowei
 * @ClassName Sender.java
 * @Description mq 生产者
 * @createTime 2020年07月30日 17:20:00
 */
@Component
public class Producer {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send() {
        String context = "hello " + new Date();
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("hello", context);
    }

    /**
     * 写入mq
     * @description
     * @author Caixiaowei
     * @param queue string 队列名称
     * @param context string 消息数据
     * @updateTime 2020/7/31 10:45
     * @return
     */
    public void send(String queue, String context) {
        this.rabbitTemplate.convertAndSend(queue, context);
    }

    public void  sendMessage(String message){
        CorrelationData correlationData = new CorrelationData("111");

        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_APPROVAL, RabbitConfig.ROUTING_KEY_APPROVAL, message,
                correlationData);
    }
}
