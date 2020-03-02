package de.s2d_advgui.animations.impl;

import de.s2d_advgui.animations.AAnimation;
import de.s2d_advgui.animations.IAnimationListener_Close;

public final class Animation_Sleep extends AAnimation {
    // -------------------------------------------------------------------------------------------------------------------------
    public Animation_Sleep(float startTime, float endTime, IAnimationListener_Close pCloseListener) {
        super(startTime, endTime);
        this.setCloseListener(pCloseListener);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void _calc(float i) throws Exception {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
