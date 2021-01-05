package com.ezy.common.model;

import java.util.List;

/**
 * @Author: Kevin Liu
 * @CreateDate: 2020/7/15 14:03
 * @Desc 分页数据封装类
 * @Version: 1.0
 */
public class CommonPage<T> {

    private Integer pageNum;
    private Integer pageSize;
    private Long total;
    private List<T> list;
    private int code;
    private String message;

    public static <T> CommonPage<T> buildPage(Integer pageNum, Integer pageSize, Long total, List<T> list) {
        CommonPage<T> result = new CommonPage<T>();
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        result.setTotal(total);
        result.setList(list);
        return result;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public List<T> getList() {
        return list;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
