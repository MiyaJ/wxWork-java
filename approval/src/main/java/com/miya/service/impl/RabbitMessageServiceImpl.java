package com.miya.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miya.entity.RabbitMessage;
import com.miya.mapper.RabbitMessageMapper;
import com.miya.service.IRabbitMessageService;
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
