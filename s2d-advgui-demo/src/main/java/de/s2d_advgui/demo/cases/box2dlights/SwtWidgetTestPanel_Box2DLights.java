package de.s2d_advgui.demo.cases.box2dlights;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.awidget.WidetLayer;
import de.s2d_advgui.core.canvas.LightsDrawer;
import de.s2d_advgui.core.layoutmanager.SwtLayoutManager_GridByColumns;
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

        //WidetLayer[] layers = new WidetLayer[] { WidetLayer.BACKGROUND }
        int i = 0;
        //for(WidetLayer layer: WidetLayer.values())
        WidetLayer layer = WidetLayer.BACKGROUND;
        //for (int i = 0; i < 1; i++)
        {
            int fi = i++;
            SwtDrawerManager<DemoResourceManager> drawerManager2 = new SwtDrawerManager<>(rm);
            LightsDrawer ldr = new LightsDrawer(drawerManager2, pParent.getContext()) {
                @Override
                protected void _onUpdate() {
                    // DON
                }
            };
            SwtCanvas_Box2DLights2 canvas2 = new SwtCanvas_Box2DLights2(this, ldr, drawerManager2);
            canvas2.addDrawer(WidetLayer.FOREGROUND, false, new InternalWidgetDrawerBatch() {
                @Override
                protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                    String pStrg = "xyz " + fi + " (layer: " + layer + ")";
                    pBatch.drawText(pStrg, ShapeUtils.explode(pDims, 20), Align.bottom, 1f, true, Color.CYAN);
                }
            });
            canvas2.addDrawer(layer, false, ldr);
        }
        this.setLayoutManager(new SwtLayoutManager_GridByColumns(1));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
