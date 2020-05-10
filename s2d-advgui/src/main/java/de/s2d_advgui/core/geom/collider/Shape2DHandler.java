package de.s2d_advgui.core.geom.collider;

import javax.annotation.Nonnull;

import com.badlogic.gdx.math.Shape2D;

interface Shape2DHandler<T extends Shape2D> {
    // -------------------------------------------------------------------------------------------------------------------------
    ColliderItem<T> detectBoxBoundaries(@Nonnull T pShape);

    // -------------------------------------------------------------------------------------------------------------------------
}