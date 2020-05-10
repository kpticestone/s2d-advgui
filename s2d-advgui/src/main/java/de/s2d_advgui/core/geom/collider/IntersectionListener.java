package de.s2d_advgui.core.geom.collider;

@FunctionalInterface
public interface IntersectionListener {
    // -------------------------------------------------------------------------------------------------------------------------
    void intersects(IntersectionContext pContext, float x, float y);

    // -------------------------------------------------------------------------------------------------------------------------
}