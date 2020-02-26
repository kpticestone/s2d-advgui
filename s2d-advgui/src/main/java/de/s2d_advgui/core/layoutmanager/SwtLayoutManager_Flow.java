package de.s2d_advgui.core.layoutmanager;

import de.s2d_advgui.core.awidget.ISwtWidget;

public class SwtLayoutManager_Flow extends ASwtLayoutManager {

    private final int useWidth;
    private final int useHeight;

    public SwtLayoutManager_Flow(int useWidth, int useHeight) {
        this.useWidth = useWidth;
        this.useHeight = useHeight;
    }

    @Override
    public void calculate(ISwtWidget<?> pWidget, float width, float height) {
        int nr = 0;
        int cols = (int) (width / useWidth);
        for (ISwtWidget<?> ch : pWidget.getChildren()) {
            float useX = Math.floorMod(nr, cols) * useWidth;
            float useY = Math.floorDiv(nr, cols) * useHeight;
            ch.setBounds(useX, useY, useWidth, useHeight);
            nr++;
        }
    }

}
