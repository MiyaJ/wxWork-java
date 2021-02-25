package com.miya.controller;

import com.miya.handler.NoticeHandler;
import com.miya.service.IApprovalTaskService;
import com.miya.service.IContactService;
import com.miya.service.IMessageService;
import com.miya.model.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Caixiaowei
 * @ClassName TestController
 * @Description
 * @createTime 2020/9/16$ 16:48$
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private IApprovalTaskService approvalTaskService;
    @Autowired
    private NoticeHandler noticeHandler;
    @Autowired
    private IMessageService messageService;

    @GetMapping("/compensateApproval")
    public CommonResult compensateApproval(String spNo) {
        try {
            approvalTaskService.compensateApproval(spNo);
        } catch (Exception e) {
            log.error("测试-补偿审批单据异常!--->{}", e);
            return CommonResult.failed();
        }
        return CommonResult.success("测试-补偿审批单据成功!");
    }

    @GetMapping("/approvalResultCallback")
    public CommonResult approvalResultCallback(String spNo) {
        try {
            noticeHandler.approvalResultCallback(spNo);
        } catch (Exception e) {
            log.error("测试-审批结果回调通知调用方异常!--->{}", e);
            return CommonResult.failed();
        }
        return CommonResult.success("测试-审批结果回调通知调用方!");
    }

    /**
     * 发送消息
     *
     * @param
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/18 14:22
     */
    @GetMapping("/sendMsg")
    public CommonResult sendMsg(String to, String content) {
        return messageService.test(to, content);
    }

    @Autowired
    private IContactService contactService;

    @GetMapping("/initDept")
    public CommonResult initDept() {
        contactService.initDeptAndEmp(null);
        return CommonResult.success();
    }

    @GetMapping("/repairDeptAndEmp")
    public CommonResult repairDeptAndEmp() {
        contactService.repairDeptAndEmp(null);
        return CommonResult.success();
    }
}
