package com.leo.commons.singstar;

public abstract class ASingStarDescriptor<T> implements ISingStarDescriptor<T> {
    private final T id;

    public ASingStarDescriptor(T id) {
        this.id = id;
    }

    @Override
    public final T getID() {
        return id;
    }
}
