package com.ezy.approval.task;

import cn.hutool.core.util.StrUtil;
import com.ezy.approval.service.IApprovalTaskService;
import com.ezy.approval.service.RedisService;
import com.ezy.common.constants.RedisConstans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * @author Caixiaowei
 * @ClassName ApprovalTask
 * @Description
 * @createTime 2020/9/7$ 15:31$
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ApprovalTask {

    @Autowired
    private IApprovalTaskService approvalTaskService;
    @Autowired
    private RedisService redisService;


    /**
     * 每隔5分钟补偿审批单据
     *
     * @param
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/7 16:17
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    private void compensateApproval() {
        approvalTaskService.compensateApproval(StrUtil.EMPTY);
    }

    /**
     * 每日4点清除审批超时缓存
     *
     * @param
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/7 16:17
     */
    @Scheduled(cron = "0 0 4 * * ?")
    private void cleanApprovalTimeount() {
        redisService.delete(RedisConstans.APPROVAL_TIME_OUT_NO);
    }

}
