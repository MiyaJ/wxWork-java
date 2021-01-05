package com.ezy.common.model;

/**
 * @Author: Kevin Liu
 * @CreateDate: 2020/7/15 14:10
 * @Desc 常用API操作码枚举
 * @Version: 1.0
 */
public enum ResultCode implements IErrorCode {
	/**
	 * 操作成功
	 */
	SUCCESS(200, "操作成功"),
	/**
	 * 操作失败
	 */
	FAILED(500, "操作失败"),
	/**
	 * 参数检验失败
	 */
	VALIDATE_FAILED(404, "参数检验失败"),
	/**
	 * 暂未登录或token已经过期
	 */
	UNAUTHORIZED(401, "暂未登录或token已经过期"),
	/**
	 * 请求头中的token为空
	 */
	AUTHORIZATION_HEADER_IS_EMPTY(600, "请求头中的token为空"),
	/**
	 * 获取token异常
	 */
	GET_TOKEN_ERROR(601, "获取token异常"),
	/**
	 * token校验异常
	 */
	JWT_TOKEN_EXPIRE(603, "token校验异常"),
	/**
	 * 后端服务触发流控
	 */
	TOMANY_REQUEST_ERROR(429, "后端服务触发流控"),
	/**
	 * 后端服务触发降级
	 */
	BACKGROUD_DEGRADE_ERROR(604, "后端服务触发降级"),
	/**
	 * 网关服务异常
	 */
	BAD_GATEWAY(502, "网关服务异常"),
	/**
	 * 没有相关权限
	 */
	FORBIDDEN(403, "没有相关权限");

	private int code;
	private String message;

	private ResultCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
