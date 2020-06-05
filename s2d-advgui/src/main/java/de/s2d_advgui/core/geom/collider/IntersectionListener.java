package de.s2d_advgui.core.geom.collider;

@FunctionalInterface
public interface IntersectionListener {
    // -------------------------------------------------------------------------------------------------------------------------
    static IntersectionListener EMPTY = new IntersectionListener() {
        @Override
        public void intersects(IntersectionContext pContext, float x, float y) {
            // DON
        }
    };

    // -------------------------------------------------------------------------------------------------------------------------
    void intersects(IntersectionContext pContext, float x, float y);

    // -------------------------------------------------------------------------------------------------------------------------
}