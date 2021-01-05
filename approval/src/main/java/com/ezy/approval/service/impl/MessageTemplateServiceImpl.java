package com.ezy.approval.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ezy.approval.entity.MessageTemplate;
import com.ezy.approval.entity.MessasgeTemplateSystem;
import com.ezy.approval.mapper.MessageTemplateMapper;
import com.ezy.approval.model.message.template.MessageTemplateBindDTO;
import com.ezy.approval.model.message.template.MessageTemplateDTO;
import com.ezy.approval.service.IMessageTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezy.approval.service.IMessasgeTemplateSystemService;
import com.ezy.common.enums.MsgTypeEnum;
import com.ezy.common.model.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 消息模板  服务实现类
 * </p>
 *
 * @author CaiXiaowei
 * @since 2020-07-27
 */
@Service
public class MessageTemplateServiceImpl extends ServiceImpl<MessageTemplateMapper, MessageTemplate> implements IMessageTemplateService {

    @Autowired
    private IMessasgeTemplateSystemService messasgeTemplateSystemService;

    /**
     * 创建消息模板
     *
     * @param messageTemplateDTO
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/16 10:43
     */
    @Override
    public CommonResult create(MessageTemplateDTO messageTemplateDTO) {
        String name = messageTemplateDTO.getName();
        String content = messageTemplateDTO.getContent();
        String type = messageTemplateDTO.getType();
        if (StrUtil.isEmpty(name) || StrUtil.isEmpty(content) || StrUtil.isEmpty(type)) {
            return CommonResult.failed("参数缺失!");
        }

        QueryWrapper<MessageTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        MessageTemplate messageTemplate = this.getOne(queryWrapper);
        if (messageTemplate != null) {
            return CommonResult.failed("模板名称已被占用!");
        }
        messageTemplate = new MessageTemplate();
        messageTemplate.setName(name);
        messageTemplate.setContent(content);
        messageTemplate.setType(type);
        messageTemplate.setIsDeleted(false);
        messageTemplate.setStatus(true);
        messageTemplate.setCreateTime(LocalDateTime.now());
        messageTemplate.setUpdateTime(LocalDateTime.now());

        // 构建请求参数
        // TODO: 2020/9/17  构建请求参数
        JSONObject requestParam = buildRequestParam(type);
        messageTemplate.setRequestParam(requestParam.toJSONString());
        
        this.save(messageTemplate);
        return CommonResult.success("新增消息模板成功!");
    }

    private JSONObject buildRequestParam(String type) {
        JSONObject requestParam = new JSONObject();
        if (type.equalsIgnoreCase(MsgTypeEnum.TEXT.getValue())) {
            requestParam.put("type", MsgTypeEnum.TEXT.name());
        }
        return requestParam;
    }

    /**
     * 系统绑定消息模板
     *
     * @param bindDTO
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/16 10:57
     */
    @Override
    public CommonResult bind(MessageTemplateBindDTO bindDTO) {
        Long messageTemplateId = bindDTO.getMessageTemplateId();
        String systemCode = bindDTO.getSystemCode();
        QueryWrapper<MessasgeTemplateSystem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("system_code", systemCode);
        queryWrapper.eq("message_template_id", messageTemplateId);
        MessasgeTemplateSystem templateSystem = messasgeTemplateSystemService.getOne(queryWrapper);
        if (templateSystem != null) {
            return CommonResult.failed("消息模板与系统已经绑定!");
        }
        templateSystem = new MessasgeTemplateSystem();
        templateSystem.setMessageTemplateId(messageTemplateId);
        templateSystem.setSystemCode(systemCode);
        messasgeTemplateSystemService.save(templateSystem);

        return CommonResult.success("模板绑定成功!");
    }
}
