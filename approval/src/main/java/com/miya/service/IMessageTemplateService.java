package com.miya.service;

import com.miya.entity.MessageTemplate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.miya.model.message.template.MessageTemplateBindDTO;
import com.miya.model.message.template.MessageTemplateDTO;
import com.miya.model.CommonResult;

/**
 * <p>
 * 消息模板  服务类
 * </p>
 *
 * @author CaiXiaowei
 * @since 2020-07-27
 */
public interface IMessageTemplateService extends IService<MessageTemplate> {

    /**
     * 创建消息模板
     *
     * @param messageTemplateDTO
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/16 10:43
     */
    CommonResult create(MessageTemplateDTO messageTemplateDTO);

    /**
     * 系统绑定消息模板
     *
     * @param bindDTO
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/16 10:57
     */
    CommonResult bind(MessageTemplateBindDTO bindDTO);
}
