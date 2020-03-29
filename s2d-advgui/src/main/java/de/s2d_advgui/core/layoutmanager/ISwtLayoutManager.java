package de.s2d_advgui.core.layoutmanager;

import de.s2d_advgui.core.awidget.ISwtWidget;

@FunctionalInterface
public interface ISwtLayoutManager {
    // -------------------------------------------------------------------------------------------------------------------------
    void calculate(ISwtWidget<?> pWidget, float width, float height);

    // -------------------------------------------------------------------------------------------------------------------------
}
