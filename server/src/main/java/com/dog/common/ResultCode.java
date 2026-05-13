package com.dog.common;

/**
 * 统一响应状态码
 */
public class ResultCode {

    /** 成功 */
    public static final int SUCCESS = 200;

    /** 未授权 */
    public static final int UNAUTHORIZED = 401;

    /** 禁止访问 */
    public static final int FORBIDDEN = 403;

    /** 资源不存在 */
    public static final int NOT_FOUND = 404;

    /** 服务器内部错误 */
    public static final int ERROR = 500;

    /** 参数校验失败 */
    public static final int BAD_REQUEST = 400;
}
