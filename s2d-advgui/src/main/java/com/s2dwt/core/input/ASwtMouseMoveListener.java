package com.s2dwt.core.input;

public abstract class ASwtMouseMoveListener {
    // -------------------------------------------------------------------------------------------------------------------------
    public abstract boolean onMouseMove(float screenX, float screenY, int pointer);

    // -------------------------------------------------------------------------------------------------------------------------
    public abstract boolean onMouseDown(float screenX, float screenY, int button);

    // -------------------------------------------------------------------------------------------------------------------------
    public abstract boolean onMouseUp(float screenX, float screenY, int button);

    // -------------------------------------------------------------------------------------------------------------------------
}
