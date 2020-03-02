package de.s2d_advgui.animations.impl;

import de.s2d_advgui.animations.AAnimation;

public class Animation_Single extends AAnimation {
    // -------------------------------------------------------------------------------------------------------------------------
    private final float from;

    // -------------------------------------------------------------------------------------------------------------------------
    private final float till;

    // -------------------------------------------------------------------------------------------------------------------------
    private final IAnimationListener_Single listener;

    // -------------------------------------------------------------------------------------------------------------------------
    public Animation_Single(float startTime, float endTime, float from, float till,
            IAnimationListener_Single pListener) {
        super(startTime, endTime);
        this.from = from;
        this.till = till;
        this.listener = pListener;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void _calc(float i) throws Exception {
        float cur = interpol(this.from, this.till);
        this.listener.onHit(cur);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
