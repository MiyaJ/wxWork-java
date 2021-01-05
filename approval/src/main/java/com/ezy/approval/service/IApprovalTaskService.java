package com.ezy.approval.service;

public interface IApprovalTaskService {

    /**
     * 补偿审批单据
     * @description 超过1h未审批的单据, 去查询企微获取最新审批信息, 同步更新到MySQL
     * @param
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/7 16:15
     */
    void compensateApproval(String spNo);
}

