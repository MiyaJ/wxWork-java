package com.ezy.approval.controller;


import com.ezy.approval.model.message.template.MessageTemplateBindDTO;
import com.ezy.approval.model.message.template.MessageTemplateDTO;
import com.ezy.approval.service.IMessageTemplateService;
import com.ezy.common.model.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 消息模板  前端控制器
 * </p>
 *
 * @author CaiXiaowei
 * @since 2020-07-27
 */
@RestController
@RequestMapping("/messageTemplate")
public class MessageTemplateController {

    @Autowired
    private IMessageTemplateService messageTemplateService;

    /**
     * 创建消息模板
     *
     * @param messageTemplateDTO
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/16 10:43
     */
    @PostMapping
    public CommonResult create(@RequestBody MessageTemplateDTO messageTemplateDTO) {
        return messageTemplateService.create(messageTemplateDTO);
    }

    /**
     * 系统绑定消息模板
     *
     * @param bindDTO
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/16 10:57
     */
    @PostMapping("/bind")
    public CommonResult bind(@RequestBody MessageTemplateBindDTO bindDTO) {
        return messageTemplateService.bind(bindDTO);
    }
}

