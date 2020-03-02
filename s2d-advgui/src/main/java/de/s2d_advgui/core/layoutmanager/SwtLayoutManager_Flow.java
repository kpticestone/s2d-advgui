package de.s2d_advgui.core.layoutmanager;

import de.s2d_advgui.core.awidget.ISwtWidget;

public class SwtLayoutManager_Flow extends ASwtLayoutManager {
    // -------------------------------------------------------------------------------------------------------------------------
    private final int useWidth;

    // -------------------------------------------------------------------------------------------------------------------------
    private final int useHeight;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtLayoutManager_Flow(int useWidth, int useHeight) {
        this.useWidth = useWidth;
        this.useHeight = useHeight;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calculate(ISwtWidget<?> pWidget, float width, float height) {
        int nr = 0;
        int cols = (int) (width / this.useWidth);
        if (cols > 0) {
            for (ISwtWidget<?> ch : pWidget.getChildren()) {
                float useX = Math.floorMod(nr, cols) * this.useWidth;
                float useY = Math.floorDiv(nr, cols) * this.useHeight;
                ch.setBounds(useX, useY, this.useWidth, this.useHeight);
                nr++;
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
