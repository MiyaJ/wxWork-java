package com.ezy.approval.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ezy.approval.entity.ApprovalTemplate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ezy.approval.model.template.TemplateListVO;
import com.ezy.approval.model.template.TemplateQueryDTO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 审批模板  Mapper 接口
 * </p>
 *
 * @author CaiXiaowei
 * @since 2020-07-27
 */
public interface ApprovalTemplateMapper extends BaseMapper<ApprovalTemplate> {

    IPage<TemplateListVO> listPage(@Param("page") IPage<TemplateListVO> page, @Param("templateQuery") TemplateQueryDTO templateQueryDTO);
}
