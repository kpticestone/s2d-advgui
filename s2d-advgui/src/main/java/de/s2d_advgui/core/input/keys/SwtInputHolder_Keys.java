package de.s2d_advgui.core.input.keys;

import com.badlogic.gdx.controllers.PovDirection;
import de.s2d_advgui.core.input.ISwtScrollListener;

import java.util.LinkedHashSet;
import java.util.Set;

public final class SwtInputHolder_Keys {
    // -------------------------------------------------------------------------------------------------------------------------
    private int[] keyCodes;
    private int[] boxCodes;
    private ISwtActionRunner_Keys runner;
    private PovDirection[] povs;
    private Set<ISwtScrollListener> scrollListeners = new LinkedHashSet<>();
    private int[] mouseButtons;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtInputHolder_Keys(int[] keyCodes) {
        this.keyCodes = keyCodes;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final SwtInputHolder_Keys box(int... boxCode) {
        this.boxCodes = boxCode;
        return this;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtInputHolder_Keys target(ISwtActionRunner_Keys pRunner) {
        this.runner = pRunner;
        return this;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtInputHolder_Keys targetDown(ISwtActionRunner_KeyDown pRunner) {
        this.runner = down -> {
            if (down) return pRunner.run();
            return false;
        };
        return this;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public int[] getKeyCodes(boolean pControllerMode) {
        if (pControllerMode) return this.boxCodes != null ? this.boxCodes : new int[0];
        return this.keyCodes != null ? this.keyCodes : new int[0];
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ISwtActionRunner_Keys getRunner() {
        return this.runner;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtInputHolder_Keys pov(PovDirection... directions) {
        this.povs = directions;
        return this;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public PovDirection[] getPovs() {
        return this.povs != null ? this.povs : new PovDirection[0];
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtInputHolder_Keys scroll(ISwtScrollListener pScrollListener) {
        this.scrollListeners.add(pScrollListener);
        return this;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Set<ISwtScrollListener> getScrollListeners() {
        return this.scrollListeners;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtInputHolder_Keys buttons(int... buttons) {
        this.mouseButtons = buttons;
        return this;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public int[] getMouseButtons() {
        return this.mouseButtons != null ? this.mouseButtons : new int[0];
    }

    // -------------------------------------------------------------------------------------------------------------------------
}