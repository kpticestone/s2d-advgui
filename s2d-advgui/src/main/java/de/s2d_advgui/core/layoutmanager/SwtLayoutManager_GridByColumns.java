package de.s2d_advgui.core.layoutmanager;

import java.util.Collection;

import de.s2d_advgui.core.awidget.ISwtWidget;

public class SwtLayoutManager_GridByColumns extends ASwtLayoutManager {
    // -------------------------------------------------------------------------------------------------------------------------
    private final int cols;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtLayoutManager_GridByColumns(int pCols) {
        this.cols = pCols;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calculate(ISwtWidget<?> pWidget, float width, float height) {
        float colWidth = width / this.cols;
        int pX = 0;
        int pY = 0;
        int cnt = 0;
        Collection<ISwtWidget<?>> c1 = pWidget.getChildren();
        double rowC = Math.ceil((float)c1.size() / this.cols);
        float pHeight = (float) ( height / rowC );
        for (ISwtWidget<?> a : c1) {
            a.setBounds(pX, pY, colWidth, pHeight);
            pX += colWidth;
            if (++cnt % this.cols == 0) {
                pX = 0;
                pY += pHeight;
            }
        }

    }

    // -------------------------------------------------------------------------------------------------------------------------
}
