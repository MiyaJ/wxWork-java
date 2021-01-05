package com.ezy.approval.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.ezy.approval.model.message.ChatInfoVO;
import com.ezy.approval.model.message.GroupChatCreateDTO;
import com.ezy.approval.model.message.GroupChatUpdateDTO;
import com.ezy.approval.model.message.MsgVO;
import com.ezy.approval.model.message.personal.ImageMsg;
import com.ezy.approval.model.message.personal.NewsMsg;
import com.ezy.approval.model.message.personal.TextMsg;
import com.ezy.approval.service.IMessageService;
import com.ezy.approval.service.RedisService;
import com.ezy.approval.utils.OkHttpClientUtil;
import com.ezy.common.constants.RedisConstans;
import com.ezy.common.enums.MsgTypeEnum;
import com.ezy.common.model.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName MessageServiceImpl
 * @Description
 * @createTime 2020/9/15$ 10:55$
 */
@Slf4j
@Service
public class MessageServiceImpl extends WxWorkServiceImpl implements IMessageService {

    @Value("${qywx.msg-corpsecret}")
    private String MESSAGE_SECRET;
    @Value("${qywx.msg-agentid:1000002}")
    private int MESSAGE_AGENT_ID;

    @Autowired
    private RedisService redisService;

    /**
     * 获取审批应用token
     *
     * @return String
     * @description 每个应用有独立的secret，获取到的access_token只能本应用使用，所以每个应用的access_token应该分开来获取
     * @author Caixiaowei
     * @updateTime 2020/7/27 15:19
     */
    @Override
    public String getAccessToken() {
        String accessToken = StrUtil.EMPTY;
        Object value = redisService.get(RedisConstans.QYWX_ACCESS_TOKEN_KEY_MESSAGE);
        if (value == null) {
            try {
                accessToken = super.getAccessToken(this.MESSAGE_SECRET);
                if (StrUtil.isNotBlank(accessToken)) {
                    redisService.set(RedisConstans.QYWX_ACCESS_TOKEN_KEY_MESSAGE, accessToken, RedisConstans.QYWX_ACCESS_TOKEN_EXPIRATION);
                }
            } catch (Exception e) {
                log.error("获取消息推送应用access_token 异常--->{}", e);
            }

        } else {
            accessToken = String.valueOf(value);
        }
        return accessToken;
    }

    /**
     * 发送消息
     *
     * @param msg Json 消息内容
     * @return MsgVO
     * @author Caixiaowei
     * @updateTime 2020/9/15 11:34
     */
    @Override
    public MsgVO sendMsg(JSONObject msg) {
        String accessToken = this.getAccessToken();
        String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";
        String replacedUrl = url.replace("ACCESS_TOKEN", accessToken);

        log.info("sendMsg --->{}", msg.toJSONString());
        String resultStr = OkHttpClientUtil.doPost(replacedUrl, null, msg);
        log.info("resultStr ---》 data : {}", resultStr);
        MsgVO msgVO = JSONObject.parseObject(resultStr, MsgVO.class);

        return msgVO;
    }

    /**
     * 发送文本消息
     *
     * @param msg TextMsg 文本消息
     * @return MsgVO
     * @author Caixiaowei
     * @updateTime 2020/9/15 11:35
     */
    @Override
    public MsgVO sendTextMsg(TextMsg msg) {
        JSONObject data = (JSONObject) JSONObject.toJSON(msg);
        MsgVO msgVO = this.sendMsg(data);
        return msgVO;
    }

    /**
     * 发送图片消息
     *
     * @param msg ImageMsg 图片消息
     * @return MsgVO
     * @author Caixiaowei
     * @updateTime 2020/9/15 11:35
     */
    @Override
    public MsgVO sendImage(ImageMsg msg) {
        JSONObject data = (JSONObject) JSONObject.toJSON(msg);
        MsgVO msgVO = this.sendMsg(data);
        return msgVO;
    }

    /**
     * 发送图文消息
     *
     * @param msg NewsMsg
     * @return MsgVO
     * @author Caixiaowei
     * @updateTime 2020/9/15 11:35
     */
    @Override
    public MsgVO sendNews(NewsMsg msg) {
        return null;
    }

    /**
     * 创建群聊
     *
     * @param groupChatCreateDTO
     * @return String 群聊id
     * @author Caixiaowei
     * @updateTime 2020/9/15 11:36
     */
    @Override
    public String createGroupChat(GroupChatCreateDTO groupChatCreateDTO) {
        String chatid = StrUtil.EMPTY;

        String url = "https://qyapi.weixin.qq.com/cgi-bin/appchat/create?access_token=" + getAccessToken();
        List<String> userlist = groupChatCreateDTO.getUserlist();
        if (CollectionUtils.isEmpty(userlist) || userlist.size() < 2) {
            log.error("群聊成员不少于2个");
            return chatid;
        }
        JSONObject data = (JSONObject) JSONObject.toJSON(groupChatCreateDTO);
        String resultStr = OkHttpClientUtil.doPost(url, null, data);
        JSONObject result = JSONObject.parseObject(resultStr);
        if (result.getIntValue("errcode") == 0) {
            chatid = result.getString("chatid");
        } else {
            log.error("创建群聊异常: {}", result.getString("errmsg"));
        }
        return chatid;
    }

    /**
     * 修改群聊
     *
     * @param updateData
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/15 11:36
     */
    @Override
    public JSONObject updateGroupChat(JSONObject updateData) {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/appchat/update?access_token=ACCESS_TOKEN";
        String resultStr = OkHttpClientUtil.doPost(url, null, updateData);
        JSONObject result = JSONObject.parseObject(resultStr);
        return result;
    }

    /**
     * 获取群聊
     *
     * @param chatid 群聊id
     * @return ChatInfoVO
     * @author Caixiaowei
     * @updateTime 2020/9/15 11:36
     */
    @Override
    public ChatInfoVO getGroupChat(String chatid) {
        return null;
    }

    /**
     * 发送群聊消息
     *
     * @param msg json 消息内容
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/15 11:37
     */
    @Override
    public void sendGroupChat(JSONObject msg) {

    }

    /**
     * 发送企微消息
     *
     * @param to
     * @param content
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/18 14:23
     */
    @Override
    public CommonResult test(String to, String content) {
        TextMsg textMsg = new TextMsg();
        textMsg.setTouser(to);
        textMsg.setToparty(StringUtils.EMPTY);
        textMsg.setTotag(StringUtils.EMPTY);
        textMsg.setMsgtype(MsgTypeEnum.TEXT.getValue());
        textMsg.setAgentid(MESSAGE_AGENT_ID);
        textMsg.setText(new TextMsg.TextBean(content));
        textMsg.setSafe(0);
        textMsg.setEnable_id_trans(0);
        textMsg.setEnable_duplicate_check(0);
        textMsg.setDuplicate_check_interval(1800);

        this.sendTextMsg(textMsg);


        return CommonResult.success("sucess");
    }
}
