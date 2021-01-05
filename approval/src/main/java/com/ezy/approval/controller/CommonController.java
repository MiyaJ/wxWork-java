package com.ezy.approval.controller;

import com.ezy.approval.service.IApprovalService;
import com.ezy.common.model.CommonResult;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Caixiaowei
 * @ClassName CommonController.java
 * @Description 通用api
 * @createTime 2020年07月28日 09:51:00
 */
@RestController
@RequestMapping("/v1/common")
@Slf4j
public class CommonController {



    @Autowired
    private IApprovalService approvalService;

    /**
     * 上传附件
     * @description 目前只支持 图片, 文件 两种类型
     * @author Caixiaowei
     * @param file: MultipartFile 上传的文件
     * @updateTime 2020/7/28 9:57 
     * @return 
     */
    @PostMapping("/uploadAnnex")
    public CommonResult uploadAnnex(@RequestParam("file") MultipartFile file) {

        return approvalService.uploadAnnex(file);
    }

    /**
     * 获取素材
     * @description
     * @author Caixiaowei
     * @param mediaId: 素材id
     * @updateTime 2020/7/28 13:45
     * @return
     */
    @GetMapping("/getMedia")
    public CommonResult getMedia(String mediaId, HttpServletResponse response) {
        String mediaUrl = approvalService.getMedia(mediaId, response);
        Map<String, String> result = Maps.newHashMap();
        result.put("mediaUrl", mediaUrl);
        return CommonResult.success(result);
    }

}
