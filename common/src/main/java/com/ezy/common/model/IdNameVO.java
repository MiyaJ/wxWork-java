package com.ezy.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName IdNameVO
 * @Description
 * @createTime 2020/9/24$ 16:33$
 */
@Data
public class IdNameVO implements Serializable {

    public Integer id;

    private String name;
}
