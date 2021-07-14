package com.newangels.gen.exception;

/**
 * 不支持的代码模板异常
 *
 * @author: TangLiang
 * @date: 2021/7/14 10:48
 * @since: 1.0
 */
public class UnSupportedProcedureModelException extends RuntimeException {
    public UnSupportedProcedureModelException() {
    }

    public UnSupportedProcedureModelException(String message) {
        super(message);
    }

    public UnSupportedProcedureModelException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnSupportedProcedureModelException(Throwable cause) {
        super(cause);
    }
}
