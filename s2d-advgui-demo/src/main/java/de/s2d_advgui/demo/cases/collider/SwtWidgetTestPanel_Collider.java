package de.s2d_advgui.demo.cases.collider;

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
import de.s2d_advgui.demo.cases.collider.SwtCanvas_Collider.SceneInfo;

public class SwtWidgetTestPanel_Collider extends ASwtWidgetTestPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWidgetTestPanel_Collider(ISwtWidget<? extends Group> pParent) {
        super(pParent);

        DemoResourceManager rm = (DemoResourceManager) this.getContext().getResourceManager();
        SwtDrawerManager<DemoResourceManager> drawerManager = new SwtDrawerManager<>(rm);
        SwtCanvas_Collider canvas = new SwtCanvas_Collider(this, drawerManager);
        canvas.setBounds(0, 25, 0, -25);

        this.addDrawer(WidetLayer.FOREGROUND, false, new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                SceneInfo inf = canvas.info;
                String pStrg = "";
                pStrg += "collider count: " + inf.colidercount + "\n";
                pStrg += "constructing duration: " + inf.constuctionduration + " msec\n";
                pStrg += "collider vs collider: " + inf.checks + "\n";
                pStrg += "intersections: " + inf.hits + "\n";
                pStrg += "with draw: " + inf.withDraw + " msec\n";
                pStrg += "without draw: " + inf.withoutDraw + " msec\n";
                pBatch.drawText(pStrg, ShapeUtils.explode(pDims, 20), Align.bottom, 1f, true, Color.CYAN);
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
