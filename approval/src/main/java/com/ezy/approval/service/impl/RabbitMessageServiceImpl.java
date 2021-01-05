package com.ezy.approval.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezy.approval.entity.RabbitMessage;
import com.ezy.approval.mapper.RabbitMessageMapper;
import com.ezy.approval.service.IRabbitMessageService;
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
