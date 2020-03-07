package de.s2d_advgui.core.input;

public interface ISwtMouseMoveListener {
    // -------------------------------------------------------------------------------------------------------------------------
    /**
     * 
     * @param screenX
     * @param screenY
     * @param button
     * @return
     */
    default boolean onMouseMove(float screenX, float screenY, int button) {
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    /**
     * 
     * @param screenX
     * @param screenY
     * @param button
     * @return
     */
    default boolean onMouseDown(float screenX, float screenY, int button) {
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    /**
     * 
     * @param screenX
     * @param screenY
     * @param button
     * @return
     */
    default boolean onMouseUp(float screenX, float screenY, int button) {
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
