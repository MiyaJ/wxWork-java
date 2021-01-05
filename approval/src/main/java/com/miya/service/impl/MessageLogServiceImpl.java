package com.miya.service.impl;

import com.miya.entity.MessageLog;
import com.miya.mapper.MessageLogMapper;
import com.miya.service.IMessageLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息发送记录  服务实现类
 * </p>
 *
 * @author CaiXiaowei
 * @since 2020-07-27
 */
@Service
public class MessageLogServiceImpl extends ServiceImpl<MessageLogMapper, MessageLog> implements IMessageLogService {

}
