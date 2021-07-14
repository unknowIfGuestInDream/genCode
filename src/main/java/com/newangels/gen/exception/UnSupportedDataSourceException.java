package com.newangels.gen.exception;

/**
 * 不支持的数据库类型异常
 *
 * @author: TangLiang
 * @date: 2021/7/14 10:48
 * @since: 1.0
 */
public class UnSupportedDataSourceException extends RuntimeException {
    public UnSupportedDataSourceException() {
    }

    public UnSupportedDataSourceException(String message) {
        super(message);
    }

    public UnSupportedDataSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnSupportedDataSourceException(Throwable cause) {
        super(cause);
    }
}
