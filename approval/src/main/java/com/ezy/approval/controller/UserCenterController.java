package com.ezy.approval.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezy.approval.model.sys.SystemListVO;
import com.ezy.common.model.CommonResult;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName UserCenterController
 * @Description
 * @createTime 2020/9/25$ 16:14$
 */
@RestController
@RequestMapping("/user-center")
public class UserCenterController {

    @GetMapping("/systemList")
    public CommonResult systemList(String companyName, Long pageNum, Long pageSize) {
        List<SystemListVO> records = Lists.newArrayList();
        SystemListVO crm = new SystemListVO();
        crm.setSystemCode("ezy-crm");
        crm.setSystemName("crm");
        crm.setDescription("客户拜访管理平台");
        crm.setSystemUrl("https://zycrmfront.test.zyde.vip/#/login?redirect=%2FclueManager");

        SystemListVO jarvis = new SystemListVO();
        jarvis.setSystemCode("ezy-jarvis");
        jarvis.setSystemName("大管家");
        jarvis.setDescription("智阳智能数据平台");
        jarvis.setSystemUrl("https://tjarvis.ezhiyang.com/");

        records.add(crm);
        records.add(jarvis);

        IPage<SystemListVO> page = new Page<>();
        page.setCurrent(1L);
        page.setSize(10);
        page.setPages(1L);
        page.setTotal(records.size());
        page.setRecords(records);

        return CommonResult.success(page);
    }
}
