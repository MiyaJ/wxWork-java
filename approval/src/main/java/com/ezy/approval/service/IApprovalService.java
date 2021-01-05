package com.ezy.approval.service;

import com.alibaba.fastjson.JSONObject;
import com.ezy.approval.model.apply.ApprovalApplyDTO;
import com.ezy.common.model.CommonResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Caixiaowei
 * @ClassName IApprovalService.java
 * @Description 审批应用业务接口
 * @createTime 2020年07月27日 15:17:00
 */
public interface IApprovalService {

    /**
     * 获取审批应用token
     * @description
     * @author Caixiaowei
     * @updateTime 2020/7/27 15:19
     * @return String
     */
    String getAccessToken();

    /**
     * 获取审批模板详情
     * @description 企业可通过审批应用或自建应用Secret调用本接口，获取企业微信“审批应用”内指定审批模板的详情
     * @author Caixiaowei
     * @param templateId: String 模板id
     * @updateTime 2020/7/27 16:20 
     * @return JSONObject
     */
    JSONObject getTemplateDetail(String templateId);

    /**
     * 上传审批附件
     * @description
     * @author Caixiaowei
     * @param file: 附件信息
     * @updateTime 2020/7/28 11:09
     * @return
     */
    CommonResult uploadAnnex(MultipartFile file);

    /**
     * 获取素材
     * @description
     * @author Caixiaowei
     * @param mediaId: string 素材id
     * @updateTime 2020/7/28 13:46
     * @return
     */
    String getMedia(String mediaId, HttpServletResponse response);

    /**
     * 提交审批申请
     * @description
     * @author Caixiaowei
     * @param approvalApplyDTO 审批申请信息
     * @updateTime 2020/7/29 16:14 
     * @return 
     */
    JSONObject applyEvent(ApprovalApplyDTO approvalApplyDTO);

    /**
     * 获取审批申请详情
     *
     * @param spNo 审批单编号
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/7 14:05
     */
    JSONObject getApprovalDetail(String spNo);
}
