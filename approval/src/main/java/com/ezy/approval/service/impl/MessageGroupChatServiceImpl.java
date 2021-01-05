package com.ezy.approval.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ezy.approval.entity.MessageGroupChat;
import com.ezy.approval.entity.MessageGroupChatUser;
import com.ezy.approval.mapper.MessageGroupChatMapper;
import com.ezy.approval.model.message.GroupChatCreateDTO;
import com.ezy.approval.service.IMessageGroupChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezy.approval.service.IMessageGroupChatUserService;
import com.ezy.approval.service.IMessageService;
import com.ezy.common.model.CommonResult;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 群聊 服务实现类
 * </p>
 *
 * @author CaiXiaowei
 * @since 2020-07-27
 */
@Service
@Slf4j
public class MessageGroupChatServiceImpl extends ServiceImpl<MessageGroupChatMapper, MessageGroupChat> implements IMessageGroupChatService {

    @Autowired
    private IMessageService messageService;
    @Autowired
    private IMessageGroupChatUserService messageGroupChatUserService;

    /**
     * 创建群聊
     *
     * @param groupChatCreateDTO
     * @return CommonResult
     * @author Caixiaowei
     * @updateTime 2020/9/15 15:04
     */
    @Override
    public CommonResult create(GroupChatCreateDTO groupChatCreateDTO) {
        String chatid = messageService.createGroupChat(groupChatCreateDTO);
        if (StrUtil.isNotEmpty(chatid)) {
            // db 群聊入库
            MessageGroupChat groupChat = new MessageGroupChat();
            groupChat.setChatid(chatid);
            groupChat.setName(groupChatCreateDTO.getName());
            groupChat.setOwner(groupChatCreateDTO.getOwner());
            groupChat.setCreateTime(LocalDateTime.now());
            groupChat.setUpdateTime(LocalDateTime.now());

            this.save(groupChat);

            // 聊成员入库
            List<MessageGroupChatUser> groupChatUserList = Lists.newArrayList();
            List<String> userlist = groupChatCreateDTO.getUserlist();
            for (String userid : userlist) {
                MessageGroupChatUser groupChatUser = new MessageGroupChatUser();
                groupChatUser.setChatid(chatid);
                groupChatUser.setUserid(userid);

                groupChatUserList.add(groupChatUser);
            }
            messageGroupChatUserService.saveBatch(groupChatUserList);

        }
        return null;
    }

    /**
     * 更新群聊
     *
     * @param groupChatCreateDTO@return
     * @author Caixiaowei
     * @updateTime 2020/9/15 18:05
     */
    @Override
    public CommonResult updateGroupChat(GroupChatCreateDTO groupChatCreateDTO) {
        String chatid = groupChatCreateDTO.getChatid();
        String name = groupChatCreateDTO.getName();
        String owner = groupChatCreateDTO.getOwner();
        List<String> addUserList = groupChatCreateDTO.getAddUserList();
        List<String> delUserList = groupChatCreateDTO.getDelUserList();
        // 先更新企微, 然后同步本地db
        JSONObject updateData = new JSONObject();
        updateData.put("chatid", chatid);
        if (StrUtil.isNotEmpty(name)) {
            updateData.put("name", name);
        }
        if (StrUtil.isNotEmpty(owner)) {
            updateData.put("owner", owner);
        }
        if (CollectionUtil.isNotEmpty(addUserList)) {
            updateData.put("add_user_list", addUserList);
        }
        if (CollectionUtil.isNotEmpty(delUserList)) {
            updateData.put("del_user_list", delUserList);
        }
        JSONObject result = messageService.updateGroupChat(updateData);
        if (result.getIntValue("errcode") == 0) {
            QueryWrapper<MessageGroupChat> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("chaid", chatid);
            MessageGroupChat groupChat = this.getOne(queryWrapper);
            if (StrUtil.isNotEmpty(name)) {
                groupChat.setName(name);
            }
            if (StrUtil.isNotEmpty(owner)) {
                groupChat.setOwner(owner);
            }
            this.updateById(groupChat);

            // 更新群成员
            if (CollectionUtil.isNotEmpty(delUserList)) {
                QueryWrapper<MessageGroupChatUser> chatUserQueryWrapper = new QueryWrapper<>();
                chatUserQueryWrapper.eq("chatid", chatid);
                chatUserQueryWrapper.in("userid", delUserList);
                messageGroupChatUserService.remove(chatUserQueryWrapper);
            }
            if (CollectionUtil.isNotEmpty(addUserList)) {
                List<MessageGroupChatUser> insertList = Lists.newArrayList();
                for (String userid : addUserList) {
                    MessageGroupChatUser chatUser = new MessageGroupChatUser();
                    chatUser.setChatid(chatid);
                    chatUser.setUserid(userid);

                    insertList.add(chatUser);
                }
                messageGroupChatUserService.saveOrUpdateBatch(insertList);
            }
        } else {
            log.error("更新群聊异常: {}", result.getString("errmsg"));
            return CommonResult.failed("更新群聊异常");
        }
        return CommonResult.success(null);
    }

}
