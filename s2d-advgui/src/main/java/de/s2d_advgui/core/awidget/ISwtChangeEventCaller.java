package de.s2d_advgui.core.awidget;

import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

@FunctionalInterface
public interface ISwtChangeEventCaller {
    // -------------------------------------------------------------------------------------------------------------------------
    void call(ChangeEvent pEvent);

    // -------------------------------------------------------------------------------------------------------------------------
}
