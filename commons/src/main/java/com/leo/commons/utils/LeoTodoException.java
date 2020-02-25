package com.leo.commons.utils;

/**
 * @author lhilbert@communicode.de
 */
public class LeoTodoException extends RuntimeException {
    // -------------------------------------------------------------------------------------------------------------------------
    public LeoTodoException() {
        super();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public LeoTodoException(String message, Throwable cause) {
        super(message, cause);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public LeoTodoException(String message) {
        super(message);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public LeoTodoException(Throwable cause) {
        super(cause);
    }
}
