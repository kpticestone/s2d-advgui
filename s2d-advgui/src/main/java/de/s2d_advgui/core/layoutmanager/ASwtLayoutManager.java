package de.s2d_advgui.core.layoutmanager;

import de.s2d_advgui.core.awidget.ISwtWidget;

public abstract class ASwtLayoutManager {
    // -------------------------------------------------------------------------------------------------------------------------
    public abstract void calculate(ISwtWidget<?> pWidget, float width, float height);

    // -------------------------------------------------------------------------------------------------------------------------
}
