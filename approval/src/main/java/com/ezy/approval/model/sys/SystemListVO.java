package com.ezy.approval.model.sys;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName SystemListVO
 * @Description
 * @createTime 2020/9/25$ 16:16$
 */
@Data
public class SystemListVO implements Serializable {
    private static final long serialVersionUID = 7706908494278826034L;

    private String systemCode;

    private String systemName;

    private String systemUrl;

    private String description;

}
