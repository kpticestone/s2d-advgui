package com.leo.commons.utils;

import javax.annotation.Nonnull;

import java.io.Serializable;

public class UUID implements Serializable, UUIDSupport {
    // -------------------------------------------------------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;

    // -------------------------------------------------------------------------------------------------------------------------
    private final String uid;

    // -------------------------------------------------------------------------------------------------------------------------
    private final int h;

    // -------------------------------------------------------------------------------------------------------------------------
    public UUID(@Nonnull String pString) {
        this.uid = pString.intern();
        this.h = this.uid.hashCode();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static boolean equals(UUIDSupport a, UUIDSupport b) {
        if (a == b) return true;
        if (a == null || b == null) return false;
        if (a.getUID().h != b.getUID().h) return false;
        return a.getUID().uid.equals(b.getUID().uid);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public static UUID random() {
        return random(null);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static UUID random(String prefix) {
        return new UUID((prefix != null ? prefix + "-" : "") + java.util.UUID.randomUUID().toString());
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return this.uid;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public int hashCode() {
        return this.uid.hashCode();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UUID) {
            return ((UUID) obj).h == this.h && ((UUID) obj).uid.equals(this.uid);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public int length() {
        return this.uid.length();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public UUID getUID() {
        return this;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static UUID get(UUIDSupport uuidSupport) {
        if (uuidSupport == null) {
            return null;
        }
        return uuidSupport.getUID();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static UUID get(String uuidStr) {
        if (uuidStr == null || uuidStr.isEmpty()) {
            return null;
        }
        return new UUID(uuidStr);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
