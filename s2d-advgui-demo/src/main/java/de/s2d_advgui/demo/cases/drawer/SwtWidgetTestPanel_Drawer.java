package de.s2d_advgui.demo.cases.drawer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanel;

public class SwtWidgetTestPanel_Drawer extends ASwtWidgetTestPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWidgetTestPanel_Drawer(ISwtWidget<? extends Group> pParent) {
        super(pParent);
        this.addDrawerClipableForeground(new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle xDims) {
                pBatch.setColor(new Color(0x00aaff99));
                Rectangle pDims = new Rectangle(xDims.x + 10, xDims.y + 10, xDims.width - 20, xDims.height - 20);
                pBatch.drawBorder("borders/white-round-5.png", pDims);

                pBatch.drawText("TL1\nTL2", pDims, Align.topLeft, .5f, true, Color.CYAN);
                pBatch.drawText("T1\nT2", pDims, Align.top, .5f, true, Color.CYAN);
                pBatch.drawText("TR1\nTR2", pDims, Align.topRight, .5f, true, Color.CYAN);
                pBatch.drawText("L1\nL2", pDims, Align.left, .5f, true, Color.CYAN);
                pBatch.drawText("C1\nC2", pDims, Align.center, .5f, true, Color.CYAN);
                pBatch.drawText("R1\nR2", pDims, Align.right, .5f, true, Color.CYAN);
                pBatch.drawText("BL1\nBL2", pDims, Align.bottomLeft, .5f, true, Color.CYAN);
                pBatch.drawText("B1\nB2", pDims, Align.bottom, .5f, true, Color.CYAN);
                pBatch.drawText("BR1\nBR2", pDims, Align.bottomRight, .5f, true, Color.CYAN);

            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
