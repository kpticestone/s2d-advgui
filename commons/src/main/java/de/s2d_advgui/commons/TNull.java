package de.s2d_advgui.commons;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class TNull {
    // -------------------------------------------------------------------------------------------------------------------------
    private TNull() {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public static final <T> T checkNull(@Nullable T any) {
        if (any == null) throw new NullPointerException();
        return any;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
