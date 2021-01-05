package com.ezy.approval.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezy.approval.entity.ApprovalTemplate;
import com.ezy.approval.entity.ApprovalTemplateControl;
import com.ezy.approval.entity.ApprovalTemplateSystem;
import com.ezy.approval.mapper.ApprovalTemplateMapper;
import com.ezy.approval.model.apply.ApplyDataContent;
import com.ezy.approval.model.apply.ApplyDataItem;
import com.ezy.approval.model.template.*;
import com.ezy.approval.service.IApprovalService;
import com.ezy.approval.service.IApprovalTemplateControlService;
import com.ezy.approval.service.IApprovalTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezy.approval.service.IApprovalTemplateSystemService;
import com.ezy.approval.utils.OkHttpClientUtil;
import com.ezy.common.constants.CommonConstan;
import com.ezy.common.enums.ApprovalControlEnum;
import com.ezy.common.model.CommonResult;
import com.ezy.common.model.ResultCode;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 审批模板  服务实现类
 * </p>
 *
 * @author CaiXiaowei
 * @since 2020-07-27
 */
@Service
@Slf4j
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ApprovalTemplateServiceImpl extends ServiceImpl<ApprovalTemplateMapper, ApprovalTemplate> implements IApprovalTemplateService {

    @Autowired
    private IApprovalService approvalService;
    @Autowired
    private IApprovalTemplateSystemService templateSystemService;
    @Autowired
    private IApprovalTemplateControlService templateControlService;
    @Autowired
    private ApprovalTemplateMapper templateMapper;

    /**
     * 新增审批模板
     *
     * @param approvalTemplateAddDTO : ApprovalTemplateAddDTO 审批模板新增dto
     * @return CommonResult
     * @description
     * @author Caixiaowei
     * @updateTime 2020/7/27 15:02
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult add(ApprovalTemplateAddDTO approvalTemplateAddDTO) {
        String templateId = approvalTemplateAddDTO.getTemplateId();
        String systemCode = approvalTemplateAddDTO.getSystemCode();
        String callbackUrl = approvalTemplateAddDTO.getCallbackUrl();
        String patternImage = approvalTemplateAddDTO.getPatternImage();
        String description = approvalTemplateAddDTO.getDescription();
        Boolean isEnable = approvalTemplateAddDTO.getIsEnable() == null ? true : approvalTemplateAddDTO.getIsEnable();

        // 验证是否已存在
        QueryWrapper<ApprovalTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("template_id", templateId);
        List<ApprovalTemplate> list = this.list(queryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            return CommonResult.failed("此审批模板已存在!");
        }

        // 1. 根据 templateId 查询企微审批模板详情
        JSONObject detail = approvalService.getTemplateDetail(templateId);
        if (detail == null || detail.getIntValue("errcode") != 0) {
            return CommonResult.failed("模板查询异常, 请检查模板id 是否正确.");
        }

        // 2. 保存模板入库
        ApprovalTemplate template = new ApprovalTemplate();
        // 模板名称
        JSONArray templateNames = detail.getJSONArray("template_names");
        String templateName = templateNames.getJSONObject(0).getString("text");

        // 模板内容
        JSONObject templateContent = detail.getJSONObject("template_content");
        log.info("templateContent--->{}", templateContent.toString());
//        List<TemplateControl> templateControls = buildTemplateRequestParams(templateContent);
//        String requestParam = JSONObject.toJSONString(templateControls);

        List<ApprovalTemplateControl> templateControls = getTemplateControls(templateId);
        if (CollectionUtil.isNotEmpty(templateControls)) {
            List<Long> controlIds = templateControls.stream().map(ApprovalTemplateControl::getId).collect(Collectors.toList());
            templateControlService.removeByIds(controlIds);
        }

        List<TemplateRequestParam> requestParams = buildTemplateRequestParams(templateId, templateContent);
        String requestParam = JSONObject.toJSONString(requestParams);
//        List<ApplyDataContent> applyDataContents = buildTemplateRequestParams2(templateId, templateContent);
//        String requestParam = JSONObject.toJSONString(applyDataContents);


        template.setTemplateId(templateId);
        template.setTemplateName(templateName);
        template.setIsDeleted(0);
        template.setContent(JSONObject.toJSONString(templateContent));
        template.setPatternImage(patternImage);
        template.setIsEnable(isEnable);
        // TODO: 2020/7/27 模板出入参待完善
        template.setRequestParam(requestParam);
        template.setResponseParam("");
        template.setDescription(description);
        // TODO: 2020/7/27 创建人/更新人 待完善
        template.setCreateTime(LocalDateTime.now());
        template.setUpdateTime(LocalDateTime.now());

        this.save(template);

        // 3. 维护模板与调用方关系
        if (StrUtil.isNotEmpty(systemCode)) {
            ApprovalTemplateSystem approvalTemplateSystem = new ApprovalTemplateSystem();
            approvalTemplateSystem.setTemplateId(templateId);
            approvalTemplateSystem.setSystemCode(systemCode);
            approvalTemplateSystem.setCallbackUrl(callbackUrl);

            templateSystemService.save(approvalTemplateSystem);
        }
        return CommonResult.success("新增审批模板成功!");
    }


    /**
     * 查询审批模板详情
     *
     * @param templateId : string 模板id
     * @param systemCode : string 调用方系统标识
     * @return TemplateDetailVO
     * @description 需要先校验调用方是否注册次模板
     * @author Caixiaowei
     * @updateTime 2020/7/29 13:51
     */
    @Override
    public CommonResult detail(String templateId, String systemCode) {
        TemplateDetailVO detailVO = new TemplateDetailVO();

        // 校验调用方是否注册次模板
//        QueryWrapper<ApprovalTemplateSystem> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("template_id", templateId)
//                .eq("system_code", systemCode);
//        ApprovalTemplateSystem one = templateSystemService.getOne(queryWrapper);
//        if (one == null) {
//            return CommonResult.success("没有注册这个模板, 请联系管理员.");
//        }

        ApprovalTemplate template = getByTemplateId(templateId);
        // 校验模板
        String errMsg = checkTemplate(template);
        if (StrUtil.isNotEmpty(errMsg)) {
            return CommonResult.failed(errMsg);
        }

        // build 模板详情
        detailVO = TemplateDetailVO.builder()
                .templateId(template.getTemplateId())
                .templateName(template.getTemplateName())
                .description(template.getDescription())
                .patternImage(template.getPatternImage())
                .isEnable(template.getIsEnable())
                .build();
        String requestParam = template.getRequestParam();
//        List<ApplyDataContent> templateControls = JSONObject.parseArray(requestParam, ApplyDataContent.class);
//        detailVO.setRequestParam(templateControls);
        List<TemplateRequestParam> templateControls = JSONObject.parseArray(requestParam, TemplateRequestParam.class);
        detailVO.setRequestParam(templateControls);
        return CommonResult.success(detailVO);
    }

    /**
     * 根据模板id查询模板
     * @description
     * @author Caixiaowei
     * @param templateId: string 模板id
     * @updateTime 2020/7/29 14:06
     * @return ApprovalTemplate
     */
    @Override
    public ApprovalTemplate getByTemplateId(String templateId) {
        QueryWrapper<ApprovalTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("template_id", templateId);
        return this.getOne(queryWrapper);
    }

    /**
     * 系统配置审批模板
     *
     * @param approvalTemplateAddDTO
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/16 13:56
     */
    @Override
    public CommonResult bind(ApprovalTemplateAddDTO approvalTemplateAddDTO) {

        String templateId = approvalTemplateAddDTO.getTemplateId();
        String systemCode = approvalTemplateAddDTO.getSystemCode();
        String callbackUrl = approvalTemplateAddDTO.getCallbackUrl();
        String qwContactPerson = approvalTemplateAddDTO.getQwContactPerson();

        ApprovalTemplate template = getByTemplateId(templateId);
        if (template == null) {
            return CommonResult.failed("模板不存在, 请联系管理员!");
        }

        QueryWrapper<ApprovalTemplateSystem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("system_code", systemCode);
        queryWrapper.eq("template_id", templateId);
        ApprovalTemplateSystem templateSystem = templateSystemService.getOne(queryWrapper);
        if (templateSystem != null) {
            return CommonResult.failed("模板已经配置!");
        }
        templateSystem = new ApprovalTemplateSystem();
        templateSystem.setTemplateId(templateId);
        templateSystem.setSystemCode(systemCode);
        templateSystem.setCallbackUrl(callbackUrl);
        templateSystem.setQwContactPerson(qwContactPerson);
        templateSystemService.save(templateSystem);
        return CommonResult.success("配置模板成功!");
    }

    /**
     * 解绑系统与审批模板关系
     *
     * @param approvalTemplateAddDTO
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/16 14:03
     */
    @Override
    public CommonResult unbind(ApprovalTemplateAddDTO approvalTemplateAddDTO) {
        String templateId = approvalTemplateAddDTO.getTemplateId();
        String systemCode = approvalTemplateAddDTO.getSystemCode();
        QueryWrapper<ApprovalTemplateSystem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("system_code", systemCode);
        queryWrapper.eq("template_id", templateId);
        ApprovalTemplateSystem templateSystem = templateSystemService.getOne(queryWrapper);
        if (templateSystem == null) {
            return CommonResult.failed("查不到这个模板, 请联系管理员!");
        }
        Long id = templateSystem.getId();
        templateSystemService.removeById(id);
        return CommonResult.success("解绑成功!");
    }

    /**
     * 查询系统已配置绑定的模板
     *
     * @param systemCode 系统编码
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/16 14:22
     */
    @Override
    public CommonResult listOfSystem(String systemCode) {
        List<TemplateListVO> list = Lists.newArrayList();

        QueryWrapper<ApprovalTemplateSystem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("system_code", systemCode);
        List<ApprovalTemplateSystem> templateSystemList = templateSystemService.list(queryWrapper);
        if (CollectionUtil.isNotEmpty(templateSystemList)) {
            List<String> templateIds = templateSystemList.stream().map(ApprovalTemplateSystem::getTemplateId)
                    .distinct().collect(Collectors.toList());
            QueryWrapper<ApprovalTemplate> templateQueryWrapper = new QueryWrapper<>();
            templateQueryWrapper.eq("is_deleted", false);
            templateQueryWrapper.in("template_id", templateIds);
            List<ApprovalTemplate> approvalTemplates = this.list(templateQueryWrapper);

            convertToListVO(list, approvalTemplates);
        }
        return CommonResult.success(list);
    }

    /**
     * 查询模板列表
     *
     * @param templateQueryDTO 筛选条件
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/16 14:43
     */
    @Override
    public IPage<TemplateListVO> list(TemplateQueryDTO templateQueryDTO) {

        IPage<TemplateListVO> page = new Page<>();
        page.setCurrent(templateQueryDTO.getPageNum() == null ? 1L : templateQueryDTO.getPageNum());
        page.setSize(templateQueryDTO.getPageSize() == null ? 10L : templateQueryDTO.getPageSize());

        page = templateMapper.listPage(page, templateQueryDTO);
        return page;
    }

    /**
     * 启用/禁用审批模板
     *
     * @param approvalTemplateAddDTO@return
     * @author Caixiaowei
     * @updateTime 2020/9/18 10:41
     */
    @Override
    public CommonResult enable(ApprovalTemplateAddDTO approvalTemplateAddDTO) {
        String templateId = approvalTemplateAddDTO.getTemplateId();
        Boolean isEnable = approvalTemplateAddDTO.getIsEnable();
        if (StrUtil.isEmpty(templateId) || isEnable == null) {
            return CommonResult.failed("参数为空!");
        }
        ApprovalTemplate byTemplateId = getByTemplateId(templateId);
        if (byTemplateId == null) {
            return CommonResult.failed("模板不存在, 请联系管理员!");
        }
        ApprovalTemplate approvalTemplate = byTemplateId.setIsEnable(isEnable);
        this.updateById(approvalTemplate);
        return CommonResult.success("操作成功!");
    }

    /**
     * 删除模板
     *
     * @param templateId
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/25 14:51
     */
    @Override
    @Transactional
    public CommonResult delete(String templateId) {


        QueryWrapper<ApprovalTemplateSystem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("template_id", templateId);
        List<ApprovalTemplateSystem> list = templateSystemService.list(queryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            return CommonResult.failed("模板有绑定的系统, 请解绑后再删除");
        }

        ApprovalTemplate template = this.getByTemplateId(templateId);
        this.removeById(template.getId());
        return CommonResult.success(ResultCode.SUCCESS);
    }

    /**
     * 查询模板控件
     *
     * @param templateId 模板id
     * @return List<ApprovalTemplateControl>
     * @author Caixiaowei
     * @updateTime 2020/9/24 16:22
     */
    @Override
    public List<ApprovalTemplateControl> getTemplateControls(String templateId) {
        QueryWrapper<ApprovalTemplateControl> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("template_id", templateId);
        return templateControlService.list(queryWrapper);
    }

    /**
     * 获取模板中的中文文本
     *
     * @param textPropertyList
     * @return String
     * @author Caixiaowei
     * @updateTime 2020/9/22 14:31
     */
    @Override
    public String getTextZhCN(List<TextProperty> textPropertyList) {
        String text = StrUtil.EMPTY;
        if (CollectionUtil.isNotEmpty(textPropertyList)) {
            text = textPropertyList.get(0).getText();
            for (TextProperty textProperty : textPropertyList) {
                String lang = textProperty.getLang();
                if (lang.equalsIgnoreCase(CommonConstan.ZH_CN)) {
                    text = textProperty.getText();
                }
            }
        }
        return text;
    }

    /*********************************** 私有方法 *************************************/

    private void convertToListVO(List<TemplateListVO> list, List<ApprovalTemplate> approvalTemplates) {
        if (CollectionUtil.isNotEmpty(approvalTemplates)) {
            for (ApprovalTemplate template : approvalTemplates) {
                TemplateListVO templateListVO = new TemplateListVO();
                String templateId = template.getTemplateId();
                String templateName = StrUtil.isEmpty(template.getTemplateName()) ? StrUtil.EMPTY : template.getTemplateName();
                String description = StrUtil.isEmpty(template.getDescription()) ? StrUtil.EMPTY : template.getDescription();
                Boolean isEnable = template.getIsEnable();
                String patternImage = StrUtil.isEmpty(template.getPatternImage()) ? StrUtil.EMPTY : template.getPatternImage();

                templateListVO.setTemplateId(templateId);
                templateListVO.setTemplateName(templateName);
                templateListVO.setDescription(description);
                templateListVO.setIsEnable(isEnable);
                templateListVO.setPatternImage(patternImage);
                list.add(templateListVO);
            }
        }
    }
    /**
     * 构建模板请求参数
     * @description
     * @author Caixiaowei
     * @param templateContent: JSONObject 控件信息
     * @updateTime 2020/7/29 11:01
     * @return
     */
    private List<TemplateRequestParam> buildTemplateRequestParams(String templateId, JSONObject templateContent) {
        List<ApprovalTemplateControl> controlList = Lists.newArrayList();
        List<TemplateRequestParam> requestParams = Lists.newArrayList();

        // 所有控件
        JSONArray controls = templateContent.getJSONArray("controls");
        if (controls != null && controls.size() > 0) {
            for (Object controlObject : controls) {
                JSONObject controlJson = (JSONObject) controlObject;
                JSONObject property = controlJson.getJSONObject("property");
                JSONObject config = controlJson.getJSONObject("config");

                String id = property.getString("id");
                String control = property.getString("control");
                Integer require = property.getInteger("require");
                Integer unPrint = property.getInteger("un_print");
                List<TextProperty> titleList = JSONArray.parseArray(property.getString("title"), TextProperty.class);
                List<TextProperty> placeholderList = JSONArray.parseArray(property.getString("placeholder"), TextProperty.class);
                String title = getTextZhCN(titleList);
                String placeholder = getTextZhCN(placeholderList);
                String type = StrUtil.EMPTY;
                JSONArray options = new JSONArray();

                if (control.equalsIgnoreCase(ApprovalControlEnum.DATE.getControl())) {
                    // 日期/时间
                    type = config.getJSONObject("date").getString("type");
                } else if (control.equalsIgnoreCase(ApprovalControlEnum.DATE_RANGE.getControl())) {
                    // 时长
                    type = config.getJSONObject("date_range").getString("type");
                } else if (control.equalsIgnoreCase(ApprovalControlEnum.SELECTOR.getControl())) {
                    // 选择
                    type = config.getJSONObject("selector").getString("type");
                    options = config.getJSONObject("selector").getJSONArray("options");
                } else if (control.equalsIgnoreCase(ApprovalControlEnum.CONTACT.getControl())) {
                    // 成员/部门
                    type = config.getJSONObject("contact").getString("type");
                    String mode = config.getJSONObject("contact").getString("mode");
                    if ("user".equalsIgnoreCase(mode)) {
                        // 成员

                    } else if ("department".equalsIgnoreCase(mode)) {
                        // 部门

                    }
                } else if (control.equalsIgnoreCase(ApprovalControlEnum.LOCATION.getControl())) {
                    // 位置
                    type = config.getJSONObject("location").getString("type");
                }

                // build 模板请求参数
                TemplateRequestParam requestParam = TemplateRequestParam.builder()
                        .control(control)
                        .id(id)
                        .placeholder(placeholder)
                        .require(require)
                        .title(title)
                        .unPrint(unPrint)
                        .type(type)
                        .options(options)
                        .build();
                requestParams.add(requestParam);

                // build 模板内容控件
                ApprovalTemplateControl templateControl = new ApprovalTemplateControl();
                templateControl.setTemplateId(templateId);
                templateControl.setControl(control);
                templateControl.setControlId(id);
                templateControl.setTitle(title);
                templateControl.setPlaceholder(placeholder);
                templateControl.setRequired(require);
                templateControl.setUnPrint(unPrint);
                templateControl.setConfig(config != null ? config.toString() : null);
                templateControl.setType(type);
                controlList.add(templateControl);
            }

            // 模板内容控件入库
            templateControlService.saveBatch(controlList);
        }

        return requestParams;
    }


    /**
     * 构建审批内容参数
     * @description
     * @author Caixiaowei
     * @param templateContent: JSONObject 内容组件
     * @updateTime 2020/7/30 13:50
     * @return List<ApplyDataContent>
     */
    private List<ApplyDataContent> buildTemplateRequestParams2(String templateId, JSONObject templateContent) {
        List<ApplyDataContent> contents = Lists.newArrayList();
        List<ApprovalTemplateControl> controlList = Lists.newArrayList();
        // 所有控件
        JSONArray controls = templateContent.getJSONArray("controls");
        if (controls != null && controls.size() > 0) {
            for (Object controlObject : controls) {

                JSONObject controlJson = (JSONObject) controlObject;
                JSONObject property = controlJson.getJSONObject("property");
                JSONObject config = controlJson.getJSONObject("config");

                // 控件属性
                String control = property.getString("control");
                String id = property.getString("id");
                Integer require = property.getInteger("require");
                Integer unPrint = property.getInteger("un_print");
                List<TextProperty> title = JSONArray.parseArray(property.getString("title"), TextProperty.class);
                List<TextProperty> placeholder = JSONArray.parseArray(property.getString("placeholder"), TextProperty.class);

                JSONObject value = new JSONObject();

                ApplyDataContent content = ApplyDataContent.builder()
                        .control(control)
                        .id(id)
                        .title(title)
                        .require(require)
                        .build();

                ApprovalTemplateControl templateControl = new ApprovalTemplateControl();
                templateControl.setTemplateId(templateId);
                templateControl.setControl(control);
                templateControl.setControlId(id);
                templateControl.setTitle(getTextZhCN(title));
                templateControl.setPlaceholder(getTextZhCN(placeholder));
                templateControl.setRequired(require);
                templateControl.setUnPrint(unPrint);
                templateControl.setConfig(config != null ? config.toString() : null);
                controlList.add(templateControl);

                // 根据控件类型来组装 value
                if (control.equalsIgnoreCase(ApprovalControlEnum.TEXT.getControl())
            || control.equalsIgnoreCase(ApprovalControlEnum.TEXTAREA.getControl())) {
                    // 文本控件 , 多行文本
                    value.put("text", "");
                } else if (control.equalsIgnoreCase(ApprovalControlEnum.NUMBER.getControl())) {
                    // 数字控件
                    value.put("new_number", "");
                } else if (control.equalsIgnoreCase(ApprovalControlEnum.MONEY.getControl())) {
                    // 金钱控件
                    value.put("new_money", "");
                } else if (control.equalsIgnoreCase(ApprovalControlEnum.DATE.getControl())) {
                    // 日期/日期+时间控件
                    JSONObject date = config.getJSONObject("date");
                    String type = date.getString("type");
                    date.put("s_timestamp", "");
                    value.put("date", date);

                    templateControl.setType(type);
                } else if (control.equalsIgnoreCase(ApprovalControlEnum.DATE_RANGE.getControl())) {
                    // 时长
                    JSONObject dateRange = config.getJSONObject("date_range");
                    Long perday_duration = dateRange.getLong("perday_duration");
                    dateRange.put("new_begin", "");
                    dateRange.put("new_end", "");
                    dateRange.put("new_duration", perday_duration);

                    value.put("date_range", dateRange);

                } else if (control.equalsIgnoreCase(ApprovalControlEnum.SELECTOR.getControl())) {
                    // 选择
                    JSONObject selector = config.getJSONObject("selector");
                    value.put("selector", selector);
                    String type = selector.getString("type");

                    templateControl.setType(type);
//
//                    JSONObject option = new JSONObject();
//                    option.put("key", "");
//
//                    JSONArray optionsValue = new JSONArray();
//                    optionsValue.add(new TextProperty());
//                    option.put("value", optionsValue);
//
//                    JSONArray options = new JSONArray();
//                    options.add(option);
//
//                    JSONObject newSelector = new JSONObject();
//                    newSelector.put("type", type);
//                    newSelector.put("options", options);


                } else if (control.equalsIgnoreCase(ApprovalControlEnum.CONTACT.getControl())) {
                    // 成员/部门
                    JSONObject contact = config.getJSONObject("contact");
                    String type = contact.getString("type");
                    String mode = contact.getString("mode");

                    templateControl.setType(type);
                    if ("user".equalsIgnoreCase(mode)) {
                        // 成员
                        JSONObject member = new JSONObject();
                        member.put("userid", "");
                        member.put("name", "");

                        JSONArray members = new JSONArray();
                        members.add(member);

                        value.put("members", members);
                    } else if ("department".equalsIgnoreCase(mode)) {
                        // 部门
                        JSONObject department = new JSONObject();
                        department.put("openapi_id", "");
                        department.put("name", "");

                        JSONArray departments = new JSONArray();
                        departments.add(department);

                        value.put("departments", departments);
                    }
                } else if (control.equalsIgnoreCase(ApprovalControlEnum.LOCATION.getControl())) {
                    // 位置
                    JSONObject location = new JSONObject();
                    location.put("latitude", "");
                    location.put("longitude", "");
                    location.put("title", "");
                    location.put("address", "");
                    location.put("time", "");

                    value.put("location", location);

                } else if (control.equalsIgnoreCase(ApprovalControlEnum.LOCATION.getControl())) {

                    JSONObject file = new JSONObject();
                    file.put("file_id", "文件的media_id");

                    JSONArray files = new JSONArray();
                    files.add(file);

                    value.put("files", files);
                }

                content.setValue(value);
                contents.add(content);
            }
        }
        templateControlService.saveBatch(controlList);
        return contents;
    }


    /**
     * check 模板
     * @description
     * @author Caixiaowei
     * @param template: ApprovalTemplate审批模板
     * @updateTime 2020/7/29 14:38
     * @return String check 信息
     */
    private String checkTemplate(ApprovalTemplate template) {
        String errMsg = StrUtil.EMPTY;
        if (template == null) {
            return "模板不存在, 请联系审批管理";
        }
        Integer isDeleted = template.getIsDeleted();
        if (isDeleted == 1) {
            return "模板已删除, 请联系审批管理";
        }
        return errMsg;
    }


}
