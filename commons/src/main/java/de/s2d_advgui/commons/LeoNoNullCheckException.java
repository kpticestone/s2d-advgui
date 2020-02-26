package de.s2d_advgui.commons;

public class LeoNoNullCheckException extends LeoTodoException {
    // -------------------------------------------------------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;

    // -------------------------------------------------------------------------------------------------------------------------
    public LeoNoNullCheckException() {
        super();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public LeoNoNullCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public LeoNoNullCheckException(String message) {
        super(message);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public LeoNoNullCheckException(Throwable cause) {
        super(cause);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
