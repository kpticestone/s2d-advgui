package de.s2d_advgui.demo.cases.box2dlights;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.awidget.WidetLayer;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.utils.ShapeUtils;
import de.s2d_advgui.demo.DemoResourceManager;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanel;

public class SwtWidgetTestPanel_Box2DLights extends ASwtWidgetTestPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWidgetTestPanel_Box2DLights(ISwtWidget<? extends Group> pParent) {
        super(pParent);

        DemoResourceManager rm = (DemoResourceManager) this.getContext().getResourceManager();
        SwtDrawerManager<DemoResourceManager> drawerManager = new SwtDrawerManager<>(rm);
        SwtCanvas_Box2DLights canvas = new SwtCanvas_Box2DLights(this, drawerManager);
        canvas.setBounds(0, 25, 0, -25);
        canvas.setBounds(100, 50, -150, -100);

        this.addDrawer(WidetLayer.FOREGROUND, false, new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                String pStrg = "xyz";
                pBatch.drawText(pStrg, ShapeUtils.explode(pDims, 20), Align.bottom, 1f, true, Color.CYAN);
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
