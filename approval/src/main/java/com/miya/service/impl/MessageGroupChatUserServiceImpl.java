package com.miya.service.impl;

import com.miya.entity.MessageGroupChatUser;
import com.miya.mapper.MessageGroupChatUserMapper;
import com.miya.service.IMessageGroupChatUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 群聊成员 服务实现类
 * </p>
 *
 * @author CaiXiaowei
 * @since 2020-07-27
 */
@Service
public class MessageGroupChatUserServiceImpl extends ServiceImpl<MessageGroupChatUserMapper, MessageGroupChatUser> implements IMessageGroupChatUserService {

}
