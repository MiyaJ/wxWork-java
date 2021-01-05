package com.ezy.approval.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ezy.approval.entity.ApprovalTemplate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ezy.approval.entity.ApprovalTemplateControl;
import com.ezy.approval.model.template.*;
import com.ezy.common.model.CommonResult;

import java.util.List;

/**
 * <p>
 * 审批模板  服务类
 * </p>
 *
 * @author CaiXiaowei
 * @since 2020-07-27
 */
public interface IApprovalTemplateService extends IService<ApprovalTemplate> {

    /**
     * 新增审批模板
     * @description
     * @author Caixiaowei
     * @param approvalTemplateAddDTO: ApprovalTemplateAddDTO 审批模板新增dto
     * @updateTime 2020/7/27 15:02
     * @return CommonResult
     */
    CommonResult add(ApprovalTemplateAddDTO approvalTemplateAddDTO);

    /**
     * 查询审批模板详情
     * @description 需要先校验调用方是否注册次模板
     * @author Caixiaowei
     * @param templateId: string 模板id
     * @param systemCode: string 调用方系统标识
     * @updateTime 2020/7/29 13:51
     * @return CommonResult
     */
    CommonResult detail(String templateId, String systemCode);

    /**
     * 根据模板id 查询审批模板
     * @description
     * @author Caixiaowei
     * @param templateId string 模板id
     * @updateTime 2020/7/29 15:52
     * @return ApprovalTemplate
     */
    ApprovalTemplate getByTemplateId(String templateId);

    /**
     * 系统配置审批模板
     *
     * @param approvalTemplateAddDTO
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/16 13:57
     */
    CommonResult bind(ApprovalTemplateAddDTO approvalTemplateAddDTO);

    /**
     * 解绑系统与审批模板关系
     *
     * @param approvalTemplateAddDTO
     * @return 
     * @author Caixiaowei
     * @updateTime 2020/9/16 14:03
     */
    CommonResult unbind(ApprovalTemplateAddDTO approvalTemplateAddDTO);

    /**
     * 查询系统已配置绑定的模板
     *
     * @param systemCode 系统编码
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/16 14:22
     */
    CommonResult listOfSystem(String systemCode);

    /**
     * 查询模板列表
     *
     * @param templateQueryDTO 筛选条件
     * @return 
     * @author Caixiaowei
     * @updateTime 2020/9/16 14:44
     */
    IPage<TemplateListVO> list(TemplateQueryDTO templateQueryDTO);

    /**
     * 启用/禁用审批模板
     *
     * @param 
     * @return 
     * @author Caixiaowei
     * @updateTime 2020/9/18 10:41
     */
    CommonResult enable(ApprovalTemplateAddDTO approvalTemplateAddDTO);

    /**
     * 删除模板
     *
     * @param templateId
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/25 14:51
     */
    CommonResult delete(String templateId);

    /**
     * 获取模板的空间集合
     *
     * @param templateId 模板id
     * @return List<ApprovalTemplateControl> 控件集合
     * @author Caixiaowei
     * @updateTime 2020/9/27 9:54
     */
    List<ApprovalTemplateControl> getTemplateControls(String templateId);

    /**
     * 获取模板中的中文文本
     *
     * @param textPropertyList
     * @return String
     * @author Caixiaowei
     * @updateTime 2020/9/22 14:31
     */
    String getTextZhCN(List<TextProperty> textPropertyList);
}
