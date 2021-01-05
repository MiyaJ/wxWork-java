package com.miya.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.miya.entity.ApprovalApply;
import com.miya.model.apply.ApprovalListVO;
import com.miya.model.apply.ApprovalQueryDTO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  审批申请记录 Mapper 接口
 * </p>
 *
 * @author CaiXiaowei
 * @since 2020-07-27
 */
public interface ApprovalApplyMapper extends BaseMapper<ApprovalApply> {

    IPage<ApprovalListVO> list(IPage<ApprovalListVO> queryPage, @Param("approvalQuery") ApprovalQueryDTO approvalQuery);
}
