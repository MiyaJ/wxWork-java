package com.ezy.approval.service.impl;

import com.ezy.approval.entity.MessageLog;
import com.ezy.approval.mapper.MessageLogMapper;
import com.ezy.approval.service.IMessageLogService;
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
