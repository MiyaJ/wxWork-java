package com.ezy.approval.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName BaseQueryDTO
 * @Description
 * @createTime 2020/9/22$ 15:35$
 */
@Data
public class BaseQueryDTO implements Serializable {

    private Long pageNum;

    private Long pageSize;
}
