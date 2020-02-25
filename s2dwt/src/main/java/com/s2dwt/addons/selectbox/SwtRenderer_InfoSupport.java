package com.s2dwt.addons.selectbox;

import com.leo.commons.utils.IInfoSupport;

public final class SwtRenderer_InfoSupport implements IMyRenderer<IInfoSupport> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final static SwtRenderer_InfoSupport INSTANCE = new SwtRenderer_InfoSupport();

    // -------------------------------------------------------------------------------------------------------------------------
    public static SwtRenderer_InfoSupport getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private SwtRenderer_InfoSupport() {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public String getIconImage(IInfoSupport item) {
        return item == null ? null : item.getIconImage();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public String getTitle(IInfoSupport item) {
        return item == null ? null : item.getLabel();
    }

    // -------------------------------------------------------------------------------------------------------------------------
}