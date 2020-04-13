package de.s2d_advgui.core.awidget;

import com.badlogic.gdx.scenes.scene2d.InputEvent;

@FunctionalInterface
public interface ISwtInputEventCaller {
    // -------------------------------------------------------------------------------------------------------------------------
    boolean call(InputEvent Event);

    // -------------------------------------------------------------------------------------------------------------------------
}