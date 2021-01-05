package com.ezy.approval.controller;

import com.ezy.common.enums.ApprovalCallbackStatusEnum;
import com.ezy.common.enums.ApprovalStatusEnum;
import com.ezy.common.model.CommonResult;
import com.ezy.common.model.IdNameVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName DictController
 * @Description
 * @createTime 2020/9/24$ 16:29$
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    @GetMapping("/getApprovalStatus")
    public CommonResult getApprovalStatus() {
        List<IdNameVO> list = ApprovalStatusEnum.idNameList();
        return CommonResult.success(list);
    }

    @GetMapping("/getApprovalCallbackStatus")
    public CommonResult getApprovalCallbackStatus() {
        List<IdNameVO> list = ApprovalCallbackStatusEnum.idNameList();
        return CommonResult.success(list);
    }
}
