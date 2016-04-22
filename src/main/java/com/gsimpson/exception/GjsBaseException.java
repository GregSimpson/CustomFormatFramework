package com.gsimpson.exception;

/**
 * Created by gjsimpso on 2/26/2016.
 */
public class GjsBaseException extends RuntimeException {
    public GjsBaseException() {
    }

    public GjsBaseException(String message) { super(message); }

    public GjsBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public GjsBaseException(Throwable cause) {
        super(cause);
    }

    public GjsBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
