package de.s2d_advgui.commons;

@FunctionalInterface
public interface ConsumerWithException<T> {
    // -------------------------------------------------------------------------------------------------------------------------
    void accept(T t) throws Exception;

    // -------------------------------------------------------------------------------------------------------------------------
}