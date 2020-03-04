package de.s2d_advgui.core.geom.animations;

import com.badlogic.gdx.math.Rectangle;

@FunctionalInterface
public interface IAnimationListener_ChangeBounds {
    // -------------------------------------------------------------------------------------------------------------------------
    void onChange(Rectangle pRect);

    // -------------------------------------------------------------------------------------------------------------------------
}
