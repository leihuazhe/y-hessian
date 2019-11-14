package com.yunji.dubbo.exception;

/**
 * @author Denim.leihz 2019-08-20 2:20 PM
 */
public class BusinessException extends RuntimeException {

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public BusinessException(String message) {
        super(message);
    }
}
