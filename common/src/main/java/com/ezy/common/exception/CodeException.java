package com.ezy.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

/**
 * @Author: zehao.zhang
 * @CreateDate: 2020/06/30 14:45
 * @Desc
 * @Version: 1.0
 */
public class CodeException extends RuntimeException {

    @Getter
    private final HttpStatus status;
    @Getter
    private final String errorMessage;

    public CodeException(String errorMessage) {
        this(HttpStatus.BAD_REQUEST, errorMessage);
    }

    public CodeException(HttpStatus status, String errorMessage) {
        super(errorMessage);
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public CodeException(HttpStatus status, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public static Supplier<CodeException> notFound(String message) {
        return () -> new CodeException(HttpStatus.NOT_FOUND, message);
    }

    public static Supplier<CodeException> badRequest(String message) {
        return () -> new CodeException(HttpStatus.BAD_REQUEST, message);
    }
}
