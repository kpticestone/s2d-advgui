package de.s2d_advgui.singstar;

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
