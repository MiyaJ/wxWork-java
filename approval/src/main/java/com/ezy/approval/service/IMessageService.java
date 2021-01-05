package com.ezy.approval.service;

import com.alibaba.fastjson.JSONObject;
import com.ezy.approval.model.message.*;
import com.ezy.approval.model.message.personal.ImageMsg;
import com.ezy.approval.model.message.personal.NewsMsg;
import com.ezy.approval.model.message.personal.TextMsg;
import com.ezy.common.model.CommonResult;

/**
 * 消息推送接口
 */
public interface IMessageService {

    /**
     * 获取消息推送token
     *
     * @param
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/15 11:23
     */
    String getAccessToken();

    /**
     * 发送消息
     *
     * @param msg Json 消息内容
     * @return MsgVO
     * @author Caixiaowei
     * @updateTime 2020/9/15 11:34
     */
    MsgVO sendMsg(JSONObject msg);

    /**
     * 发送文本消息
     *
     * @param msg TextMsg 文本消息
     * @return MsgVO
     * @author Caixiaowei
     * @updateTime 2020/9/15 11:35
     */
    MsgVO sendTextMsg(TextMsg msg);

    /**
     * 发送图片消息
     *
     * @param msg ImageMsg 图片消息
     * @return MsgVO
     * @author Caixiaowei
     * @updateTime 2020/9/15 11:35
     */
    MsgVO sendImage(ImageMsg msg);

    /**
     * 发送图文消息
     *
     * @param msg NewsMsg
     * @return MsgVO
     * @author Caixiaowei
     * @updateTime 2020/9/15 11:35
     */
    MsgVO sendNews(NewsMsg msg);

    /**
     * 创建群聊
     *
     * @param groupChatCreateDTO
     * @return String 群聊id
     * @author Caixiaowei
     * @updateTime 2020/9/15 11:36
     */
    String createGroupChat(GroupChatCreateDTO groupChatCreateDTO);

    /**
     * 修改群聊
     *
     * @param updateData
     * @return 
     * @author Caixiaowei
     * @updateTime 2020/9/15 11:36
     */
    JSONObject updateGroupChat(JSONObject updateData);

    /**
     * 获取群聊
     *
     * @param chatid 群聊id
     * @return ChatInfoVO
     * @author Caixiaowei
     * @updateTime 2020/9/15 11:36
     */
    ChatInfoVO getGroupChat(String chatid);

    /**
     * 发送群聊消息
     *
     * @param msg json 消息内容
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/15 11:37
     */
    void sendGroupChat(JSONObject msg);

    /**
     * 发送企微消息
     *
     * @param 
     * @return 
     * @author Caixiaowei
     * @updateTime 2020/9/18 16:31
     */
    CommonResult test(String to, String content);
}
