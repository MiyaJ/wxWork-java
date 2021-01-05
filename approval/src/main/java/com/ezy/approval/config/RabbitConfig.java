package com.ezy.approval.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Caixiaowei
 * @ClassName RabbitConfig.java
 * @Description TODO
 * @createTime 2020年08月06日 14:05:00
 */
@Configuration
@Slf4j
public class RabbitConfig {

    public static final String EXCHANGE_APPROVAL = "EXCHANGE_APPROVAL";
    public static final String EXCHANGE_CONTACT = "EXCHANGE_CONTACT";

    public static final String QUEUE_APPROVAL="QUEUE_APPROVAL";
    public static final String QUEUE_CONTACT_UC="QUEUE_CONTACT_UC";
    public static final String QUEUE_CONTACT_JARVIS="QUEUE_CONTACT_JARVIS";

    public static final String ROUTING_KEY_APPROVAL="ROUTING_KEY_APPROVAL";
    public static final String ROUTING_KEY_CONTACT="ROUTING_KEY_CONTACT";

    /**
     * 审批队列
     * @description
     * @author Caixiaowei
     * @param
     * @updateTime 2020/8/6 9:51
     * @return Queue
     */
    @Bean
    public Queue approvalQueue() {
        return new Queue(RabbitConfig.QUEUE_APPROVAL);
    }


    /**
     * 通讯录-uc 队列
     * @description
     * @author Caixiaowei
     * @param
     * @updateTime 2020/8/6 9:52
     * @return Queue
     */
    @Bean
    public Queue contactUcQueue() {
        return new Queue(RabbitConfig.QUEUE_CONTACT_UC);
    }
    /**
     * 通讯录-jarvis 队列
     * @description
     * @author Caixiaowei
     * @param
     * @updateTime 2020/8/6 9:52
     * @return Queue
     */
    @Bean
    public Queue contactJarvisQueue() {
        return new Queue(RabbitConfig.QUEUE_CONTACT_JARVIS);
    }

    /**
     * 创建topic交换机-审批
     * @return
     */
    @Bean
    public TopicExchange approvalTopicExchange() {
        return new TopicExchange(RabbitConfig.EXCHANGE_APPROVAL);
    }

    /**
     * 创建topic交换机-通讯录
     * @return
     */
    @Bean
    public TopicExchange contactTopicExchange() {
        return new TopicExchange(RabbitConfig.EXCHANGE_CONTACT);
    }

    @Bean
    public Binding bindingApproval() {
        //绑定一个队列 to: 绑定到哪个交换机上面 with：绑定的路由建（routingKey）
        return BindingBuilder.bind(approvalQueue()).to(approvalTopicExchange()).with(RabbitConfig.ROUTING_KEY_APPROVAL);
    }

    @Bean
    public Binding bindingContactUc() {
        //绑定一个队列 to: 绑定到哪个交换机上面 with：绑定的路由建（routingKey）
        return BindingBuilder.bind(contactUcQueue()).to(contactTopicExchange()).with(RabbitConfig.ROUTING_KEY_CONTACT);
    }

    @Bean
    public Binding bindingContactJarvis() {
        //绑定一个队列 to: 绑定到哪个交换机上面 with：绑定的路由建（routingKey）
        return BindingBuilder.bind(contactJarvisQueue()).to(contactTopicExchange()).with(RabbitConfig.ROUTING_KEY_CONTACT);
    }

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack) {
                    // 发送成功
                    log.info("rabbit 发送成功 data: {}, ack:{}, cause: {}", JSONObject.toJSONString(correlationData), ack, cause);
                } else {
                    // 发送失败
                    log.info("rabbit 发送失败 data: {}, ack:{}, cause: {}", JSONObject.toJSONString(correlationData), ack, cause);
                }
            }
        });

        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                // 发送回调失败
                log.error("rabbit 回调失败 消息 message:{}, 回应码 replyCode: {}, 回应信息 replyText: {}, 交换机 exchange: {}, 路由键 routingKey: {}",
                        message, replyCode, replyText, exchange, routingKey);
            }
        });

        return rabbitTemplate;
    }
}
