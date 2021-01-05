package com.ezy.approval.model.apply;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Caixiaowei
 * @ClassName ApprovalDataBuild
 * @Description
 * @createTime 2020/9/28$ 13:54$
 */
@Data
public class ApprovalDataBuild  implements Serializable {

    /**
     * 控件内容
     */
    private List<ApplyDataItem> contents;

    /**
     * 附件url, 多个以 ',' 隔开
     */
    private String filesUrl;
}
