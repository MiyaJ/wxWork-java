package com.ezy.approval.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ezy.approval.entity.ApprovalApply;
import com.ezy.approval.model.apply.ApprovalApplyDTO;
import com.ezy.approval.model.apply.ApprovalListVO;
import com.ezy.approval.model.apply.ApprovalQueryDTO;
import com.ezy.common.model.CommonResult;

/**
 * <p>
 *  审批申请记录 服务类
 * </p>
 *
 * @author CaiXiaowei
 * @since 2020-07-27
 */
public interface IApprovalApplyService extends IService<ApprovalApply> {

    /**
     * 审批申请
     * @description
     * @author Caixiaowei
     * @param approvalApplyDTO ApprovalApplyDTO 申请数据
     * @updateTime 2020/7/29 15:19
     * @return
     */
    CommonResult apply(ApprovalApplyDTO approvalApplyDTO);

    /**
     * 根据单号查询审批单据
     * @description
     * @author Caixiaowei
     * @param systemCode string 调用方系统标识
     * @param spNo string 审批单号
     * @updateTime 2020/7/31 11:28
     * @return
     */
    CommonResult detail(String systemCode, String spNo);

    /**
     * 查询系统应用的审批单
     * @description
     * @author Caixiaowei
     * @param systemCode string 系统标识
     * @param startDate string 开始日期
     * @param endDate string 结束日期
     * @updateTime 2020/8/3 9:33
     * @return
     */
    CommonResult listBySystemCode(String systemCode, String startDate, String endDate);

    /**
     * 根据单号查询审批单据
     *
     * @param spNo 审批单编号
     * @return ApprovalApply
     * @author Caixiaowei
     * @updateTime 2020/9/22 10:55
     */
    ApprovalApply getApprovalApply(String spNo);


    /**
     * 分页查询审批单据列表
     *
     * @param approvalQueryDTO 查询条件
     * @return IPage<ApprovalErrorListVO>
     * @author Caixiaowei
     * @updateTime 2020/9/23 9:48
     */
    IPage<ApprovalListVO> list(ApprovalQueryDTO approvalQueryDTO);

    /**
     * 查号是审批单据列表
     *
     * @param approvalQueryDTO 查询条件
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/23 10:12
     */
    IPage<ApprovalListVO> timeOutList(ApprovalQueryDTO approvalQueryDTO);

    /**
     * 重试发送回调通知
     *
     * @param spNo 审批单据编号
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/23 10:37
     */
    CommonResult retryCallback(String spNo);
}
