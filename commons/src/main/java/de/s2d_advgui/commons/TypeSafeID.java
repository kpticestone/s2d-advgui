package de.s2d_advgui.commons;

import java.util.Objects;

public abstract class TypeSafeID {
    // -------------------------------------------------------------------------------------------------------------------------
    protected final String internalId;
    private final int hash;

    // -------------------------------------------------------------------------------------------------------------------------
    protected TypeSafeID(String id) {
        this.internalId = id;
        this.hash = Objects.hash(this.internalId);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(this.internalId, ((TypeSafeID) o).internalId);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return this.internalId;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public int hashCode() {
        return hash;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
