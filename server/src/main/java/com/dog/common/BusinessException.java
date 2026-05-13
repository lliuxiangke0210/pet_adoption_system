package com.dog.common;

/**
 * 自定义业务异常
 */
public class BusinessException extends RuntimeException {

    /** 异常状态码 */
    private int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
        this.code = ResultCode.ERROR;
    }

    public int getCode() {
        return code;
    }
}
