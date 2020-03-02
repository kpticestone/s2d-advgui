package de.s2d_advgui.core.geom.animations;

import com.badlogic.gdx.math.Rectangle;

import de.s2d_advgui.core.utils.IRectangleSupport;

public final class AnimationListener_RectangleSupport implements IAnimationListener_ChangeBounds {
    // -------------------------------------------------------------------------------------------------------------------------
    private final IRectangleSupport supporter;

    // -------------------------------------------------------------------------------------------------------------------------
    public AnimationListener_RectangleSupport(IRectangleSupport pSupporter) {
        this.supporter = pSupporter;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void onChange(Rectangle pRect) {
        this.supporter.setBounds(pRect);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
