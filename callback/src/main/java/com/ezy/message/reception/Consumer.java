//package com.ezy.message.reception;
//
//import com.ezy.message.config.RabbitConfig;
//import com.rabbitmq.client.Channel;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
///**
// * @author Caixiaowei
// * @ClassName Consumer.java
// * @Description TODO
// * @createTime 2020年07月30日 17:31:00
// */
//@Component
//@Slf4j
//public class Consumer {
//
//    @RabbitHandler
//    public void process(String hello) {
//        System.out.println("Receiver : " + hello);
//    }
//
//    @RabbitListener(queues = RabbitConfig.QUEUE_APPROVAL)
//    public void handleMessage(Message message, Channel channel) throws IOException {
//        try {
//            String json = new String(message.getBody());
////            JSONObject jsonObject = JSONObject.fromObject(json);
//            log.info("handleMessage 消费了消息: {}",  json);
////            int i = 1/0;
//            //业务处理。
//            /**
//             * 防止重复消费，可以根据传过来的唯一ID先判断缓存数据中是否有数据
//             * 1、有数据则不消费，直接应答处理
//             * 2、缓存没有数据，则进行消费处理数据，处理完后手动应答
//             * 3、如果消息 处理异常则，可以存入数据库中，手动处理（可以增加短信和邮件提醒功能）
//             */
//
//            //手动应答
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//        }catch (Exception e){
//            log.error("handleMessage 消费消息失败了 error: {}"+ message.getBody());
//            log.error("OrderConsumer  handleMessage {} , error:",message,e);
//            // 处理消息失败，将消息重新放回队列
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
//        }
//
//    }
//}
