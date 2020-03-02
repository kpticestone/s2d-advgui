package de.s2d_advgui.commons;

import javax.annotation.Nullable;

public class Info implements IInfoSupport {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    private final String label;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    private final String iconImage;

    // -------------------------------------------------------------------------------------------------------------------------
    public Info(@Nullable String label) {
        this.label = label;
        this.iconImage = null;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Info(@Nullable String label, @Nullable String iconImage) {
        this.label = label;
        this.iconImage = iconImage;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public String getLabel() {
        return this.label;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public String getIconImage() {
        return this.iconImage;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
