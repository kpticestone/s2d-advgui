package de.s2d_advgui.core.window;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.layoutmanager.ASwtLayoutManager;

// -------------------------------------------------------------------------------------------------------------------------
final class LayoutManager_SwtWindow extends ASwtLayoutManager {
    // -------------------------------------------------------------------------------------------------------------------------
    private final int prefWidth;
    private final int prefHeight;

    // -------------------------------------------------------------------------------------------------------------------------
    public LayoutManager_SwtWindow(int prefWidth, int prefHeight) {
        this.prefWidth = prefWidth;
        this.prefHeight = prefHeight;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calculate(ISwtWidget<?> pWidget, float width, float height) {
        float useWidth = Math.min(this.prefWidth, width);
        float useHeight = Math.min(this.prefHeight, height);
        float ax = (width - useWidth) / 2f;
        float ay = (height - useHeight) / 2f;
        for (ISwtWidget<?> a : pWidget.getChildren()) {
            a.setBounds(ax, ay, useWidth, useHeight);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}