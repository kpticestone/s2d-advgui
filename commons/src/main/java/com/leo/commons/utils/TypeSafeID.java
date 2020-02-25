package com.leo.commons.utils;

import java.util.Objects;

public abstract class TypeSafeID {
    // -------------------------------------------------------------------------------------------------------------------------
    private final String internalId;

    // -------------------------------------------------------------------------------------------------------------------------
    protected TypeSafeID(String id) {
        this.internalId = id;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(internalId, ((TypeSafeID) o).internalId);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return internalId;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public int hashCode() {
        return Objects.hash(internalId);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
