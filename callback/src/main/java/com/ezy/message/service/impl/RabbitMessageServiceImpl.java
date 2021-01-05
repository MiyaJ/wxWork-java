package com.ezy.message.service.impl;

import com.ezy.message.entity.RabbitMessage;
import com.ezy.message.mapper.RabbitMessageMapper;
import com.ezy.message.service.IRabbitMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * rabbit mq 消息 服务实现类
 * </p>
 *
 * @author Caixiaowei
 * @since 2020-08-06
 */
@Service
public class RabbitMessageServiceImpl extends ServiceImpl<RabbitMessageMapper, RabbitMessage> implements IRabbitMessageService {

}
