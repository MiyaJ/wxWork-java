package com.ezy.approval.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName BaseResult.java
 * @Description TODO
 * @createTime 2020年07月28日 13:22:00
 */
@Data
public class BaseResult implements Serializable {
    private static final long serialVersionUID = -5444509795608607686L;

    /**
     * 出错返回码，为0表示成功，非0表示调用失败
     */
    private Integer errCode;

    /**
     * 返回码提示语
     */
    private String errMsg;


}
