package com.ezy.approval.controller;


import com.ezy.approval.model.message.GroupChatCreateDTO;
import com.ezy.approval.service.IMessageGroupChatService;
import com.ezy.common.model.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 群聊 前端控制器
 * </p>
 *
 * @author CaiXiaowei
 * @since 2020-07-27
 */
@RestController
@RequestMapping("/messageGroupChat")
public class MessageGroupChatController {

    @Autowired
    private IMessageGroupChatService messageGroupChatService;

    /**
     * 创建群聊
     *
     * @param groupChatCreateDTO
     * @return CommonResult
     * @author Caixiaowei
     * @updateTime 2020/9/15 15:04
     */
    @PostMapping("/create")
    public CommonResult create(@RequestBody GroupChatCreateDTO groupChatCreateDTO) {
        return messageGroupChatService.create(groupChatCreateDTO);
    }

    /**
     * 更新群聊
     *
     * @param groupChatCreateDTO
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/15 18:04
     */
    @PostMapping("/update")
    public CommonResult update(@RequestBody GroupChatCreateDTO groupChatCreateDTO) {
        return messageGroupChatService.updateGroupChat(groupChatCreateDTO);
    }

}

