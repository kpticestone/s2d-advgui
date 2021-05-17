package de.s2d_advgui.core.input;

@FunctionalInterface
public interface ISwtScrollListener {
    // -------------------------------------------------------------------------------------------------------------------------
    boolean onScroll(float amountX, float amountY);

    // -------------------------------------------------------------------------------------------------------------------------
}