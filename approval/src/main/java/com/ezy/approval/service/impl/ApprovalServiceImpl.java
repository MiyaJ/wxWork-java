package com.ezy.approval.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ezy.approval.model.UploadVO;
import com.ezy.approval.model.apply.ApprovalApplyDTO;
import com.ezy.approval.model.apply.Approver;
import com.ezy.approval.model.template.TextProperty;
import com.ezy.approval.service.IApprovalService;
import com.ezy.approval.service.IWxWorkService;
import com.ezy.approval.service.RedisService;
import com.ezy.approval.utils.OkHttpClientUtil;
import com.ezy.common.constants.RedisConstans;
import com.ezy.common.model.CommonResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Caixiaowei
 * @ClassName ApprovalServiceImpl.java
 * @Description 审批应用接口实现
 * @createTime 2020年07月27日 15:18:00
 */
@Service
@Slf4j
public class ApprovalServiceImpl extends WxWorkServiceImpl implements IApprovalService {

    @Value("${qywx.approval-corpsecret}")
    private String APPROVAL_SECRET;

    @Autowired
    private RedisService redisService;

    /**
     * 获取审批应用token
     *
     * @return String
     * @description 每个应用有独立的secret，获取到的access_token只能本应用使用，所以每个应用的access_token应该分开来获取
     * @author Caixiaowei
     * @updateTime 2020/7/27 15:19
     */
    @Override
    public String getAccessToken() {
        String accessToken = StrUtil.EMPTY;
        Object value = redisService.get(RedisConstans.QYWX_ACCESS_TOKEN_KEY_APPROVAL);
        if (value == null) {
            try {
                accessToken = super.getAccessToken(this.APPROVAL_SECRET);
                if (StringUtils.isNotBlank(accessToken)) {
                    redisService.set(RedisConstans.QYWX_ACCESS_TOKEN_KEY_APPROVAL, accessToken, RedisConstans.QYWX_ACCESS_TOKEN_EXPIRATION);
                }
            } catch (Exception e) {
                log.error("获取审批应用access_token 异常--->{}", e);
            }

        } else {
            accessToken = String.valueOf(value);
        }
        return accessToken;
    }

    /**
     * 获取审批模板详情
     *
     * @param templateId : String 模板id
     * @return JSONObject
     * @description 企业可通过审批应用或自建应用Secret调用本接口，获取企业微信“审批应用”内指定审批模板的详情
     * @author Caixiaowei
     * @updateTime 2020/7/27 16:20
     */
    @Override
    public JSONObject getTemplateDetail(String templateId) {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/oa/gettemplatedetail?access_token=" + this.getAccessToken();
        Map<String, Object> params = new HashMap<>();
        params.put("template_id", templateId);
        String result = OkHttpClientUtil.doPost(url, null, params);
        JSONObject data = JSONObject.parseObject(result);
        return data;
    }

    /**
     * 上传审批附件
     *
     * @param file : 附件信息
     * @return
     * @description
     * @author Caixiaowei
     * @updateTime 2020/7/28 11:09
     */
    @Override
    public CommonResult uploadAnnex(MultipartFile file) {
        String accessToken = this.getAccessToken();
        JSONObject upload = super.upload(file, accessToken);
        Integer errcode = upload.getInteger("errcode");
        String errmsg = upload.getString("errmsg");
        if (errcode == null || errcode != 0) {
            return CommonResult.failed(errmsg);
        }

        UploadVO vo = new UploadVO();
        vo.setType(upload.getString("type"));
        vo.setMediaId(upload.getString("media_id"));
        vo.setCreatedAt(upload.getString("created_at"));

        return CommonResult.success(vo);
    }

    /**
     * 获取素材
     *
     * @param mediaId  : string 素材id
     * @param response
     * @return
     * @description
     * @author Caixiaowei
     * @updateTime 2020/7/28 13:46
     */
    @Override
    public String getMedia(String mediaId, HttpServletResponse response) {
        InputStream inputStream = null;
        String url = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
        String accessToken = this.getAccessToken();
        String replacedUrl = url
                .replace("ACCESS_TOKEN", accessToken)
                .replace("MEDIA_ID", mediaId);
        return replacedUrl;
//        try {
//            OkHttpClient client = new OkHttpClient().newBuilder()
//                    .build();
//            Request request = new Request.Builder()
//                    .url(replacedUrl)
//                    .method("GET", null)
//                    .build();
//            Response okresponse = client.newCall(request).execute();
//            inputStream = okresponse.body().byteStream();
//            //获取自己数组
//            byte[] getData = readInputStream(inputStream);
//
//            String fileName = "tmp";
//            response.reset();
//            response.setContentType("application/octet-stream; charset=utf-8");
//            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"),"ISO8859_1"));
//            //获取响应报文输出流对象
//            OutputStream out =response.getOutputStream();
//            out.write(getData);
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//
//        }

    }

    /**
     * 提交审批申请
     *
     * @param approvalApplyDTO 审批申请信息
     * @return
     * @description
     * @author Caixiaowei
     * @updateTime 2020/7/29 16:14
     */
    @Override
    public JSONObject applyEvent(ApprovalApplyDTO approvalApplyDTO) {
        String accessToken = this.getAccessToken();
        String url = "https://qyapi.weixin.qq.com/cgi-bin/oa/applyevent?access_token=ACCESS_TOKEN";
        String replacedUrl = url.replace("ACCESS_TOKEN", accessToken);

        // TODO: 2020/7/29 转换请求数据
        Map<String, Object> data = Maps.newHashMap();

        List<Approver> approver = approvalApplyDTO.getApprover();
        List<String> notifyer = approvalApplyDTO.getNotifyer();
        Integer notifyType = approvalApplyDTO.getNotifyType();
        List<TextProperty> summaryList = approvalApplyDTO.getSummaryList();
        JSONObject applyData = approvalApplyDTO.getApplyData();

        data.put("creator_userid", approvalApplyDTO.getCreatorUserid());
        data.put("template_id", approvalApplyDTO.getTemplateId());
        data.put("use_template_approver", approvalApplyDTO.getUseTemplateApprover());
        data.put("approver", approver);
        data.put("notifyer", notifyer);
        data.put("notify_type", notifyType);
        data.put("apply_data", applyData);

        if (CollectionUtil.isNotEmpty(summaryList)) {
            List<JSONObject> tempSummaryList = Lists.newArrayList();
            for (TextProperty textProperty : summaryList) {
                JSONObject summaryInfo = new JSONObject();
                summaryInfo.put("summary_info", Lists.newArrayList(textProperty));
                tempSummaryList.add(summaryInfo);
            }
            data.put("summary_list", tempSummaryList);

        }
        data.put("summary_list", new JSONArray());

        log.info("data---> {}", JSONObject.toJSONString(data));
        String result = OkHttpClientUtil.doPost(replacedUrl, null, data);
        JSONObject wxResult = JSONObject.parseObject(result);
        return wxResult;
    }

    /**
     * 获取审批申请详情
     *
     * @param spNo 审批单编号
     * @return 
     * @author Caixiaowei
     * @updateTime 2020/9/7 14:05
     */
    @Override
    public JSONObject getApprovalDetail(String spNo) {
        String accessToken = this.getAccessToken();
        String url = "https://qyapi.weixin.qq.com/cgi-bin/oa/getapprovaldetail?access_token=ACCESS_TOKEN";
        String replacedUrl = url.replace("ACCESS_TOKEN", accessToken);

        Map<String, Object> params = Maps.newHashMap();
        params.put("sp_no", spNo);
        String result = OkHttpClientUtil.doPost(replacedUrl, null, params);
        JSONObject data = JSONObject.parseObject(result);

        Integer errcode = data.getInteger("errcode");
        if (errcode != 0 ) {
            return null;
        }
        JSONObject info = data.getJSONObject("info");
        return info;
    }
}
