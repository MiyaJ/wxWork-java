package com.ezy.approval.controller;


import com.ezy.approval.model.template.ApprovalTemplateAddDTO;
import com.ezy.approval.model.template.TemplateQueryDTO;
import com.ezy.approval.service.IApprovalTemplateService;
import com.ezy.common.model.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 审批模板  前端控制器
 * </p>
 *
 * @author CaiXiaowei
 * @since 2020-07-27
 */
@RestController
@RequestMapping("/approval-template")
public class ApprovalTemplateController {

    @Autowired
    private IApprovalTemplateService templateService;

    /**
     * 新增审批模板
     * @description
     * @author Caixiaowei
     * @param approvalTemplateAddDTO: ApprovalTemplateAddDTO 审批模板新增dto
     * @updateTime 2020/7/27 15:02
     * @return CommonResult
     */
    @PostMapping(value = "/add")
    private CommonResult add(@RequestBody ApprovalTemplateAddDTO approvalTemplateAddDTO) {
        CommonResult add = templateService.add(approvalTemplateAddDTO);
        return add;
    }

    /**
     * 获取模板详情
     * @description
     * @author Caixiaowei
     * @param templateId: string 模板id
     * @param systemCode: string 调用方系统标识
     * @updateTime 2020/7/29 13:48
     * @return TemplateDetailVO 模板详情
     */
    @GetMapping("/detail")
    public CommonResult detail(String templateId, String systemCode) {
        return templateService.detail(templateId, systemCode);
    }

    /**
     * 配置系统与审批模板模板
     * @description 调用方系统注册模板, 未注册无法使用
     * @author Caixiaowei
     * @param approvalTemplateAddDTO ApprovalTemplateAddDTO 模板注册信息
     * @updateTime 2020/7/29 15:24
     * @return
     */
    @PostMapping("/bind")
    public CommonResult bind(@RequestBody ApprovalTemplateAddDTO approvalTemplateAddDTO) {
        return templateService.bind(approvalTemplateAddDTO);
    }

    /**
     * 接触系统与模板的绑定
     * @description 调用方系统注册模板, 未注册无法使用
     * @author Caixiaowei
     * @param approvalTemplateAddDTO ApprovalTemplateAddDTO 模板注册信息
     * @updateTime 2020/7/29 15:24
     * @return
     */
    @PostMapping("/unbind")
    public CommonResult unbind(@RequestBody ApprovalTemplateAddDTO approvalTemplateAddDTO) {
        return templateService.unbind(approvalTemplateAddDTO);
    }

    /**
     * 查询系统已配置绑定的模板
     *
     * @param systemCode 系统编码
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/16 14:22
     */
    @GetMapping("/listOfSystem")
    public CommonResult listOfSystem(String systemCode) {
        return templateService.listOfSystem(systemCode);
    }

    /**
     * 查询模板列表
     *
     * @param templateQueryDTO 筛选条件
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/16 14:43
     */
    @GetMapping("/list")
    public CommonResult list(TemplateQueryDTO templateQueryDTO) {
        return CommonResult.success(templateService.list(templateQueryDTO));
    }

    /**
     * 启用/禁用审批模板
     *
     * @param approvalTemplateAddDTO
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/18 10:41
     */
    @PostMapping("/enable")
    public CommonResult enable(@RequestBody ApprovalTemplateAddDTO approvalTemplateAddDTO) {
        return templateService.enable(approvalTemplateAddDTO);
    }

    /**
     * 删除模板
     *
     * @param templateId 模板id
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/25 14:49
     */
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam("templateId") String templateId) {
        return templateService.delete(templateId);
    }
}

