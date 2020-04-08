package de.s2d_advgui.core.tabledata;

import de.s2d_advgui.core.awidget.ISwtWidget;

@FunctionalInterface
public interface ISwtCustomCalc {
    // -------------------------------------------------------------------------------------------------------------------------
    void calculate(ISwtWidget<?> pWidget, float width, float height, float o1);

    // -------------------------------------------------------------------------------------------------------------------------
}
