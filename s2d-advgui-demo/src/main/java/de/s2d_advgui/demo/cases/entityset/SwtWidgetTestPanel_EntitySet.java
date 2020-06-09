package de.s2d_advgui.demo.cases.entityset;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.leo.spheres.core.chunksystem.EntitySet;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.awidget.WidetLayer;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.utils.ShapeUtils;
import de.s2d_advgui.demo.DemoResourceManager;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanel;

public class SwtWidgetTestPanel_EntitySet extends ASwtWidgetTestPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWidgetTestPanel_EntitySet(ISwtWidget<? extends Group> pParent) {
        super(pParent);
        DemoResourceManager rm = (DemoResourceManager) this.getContext().getResourceManager();
        SwtDrawerManager<DemoResourceManager> drawerManager2 = new SwtDrawerManager<>(rm);
        SwtCanvas_EntitySet canvas2 = new SwtCanvas_EntitySet(this, drawerManager2);
        canvas2.addDrawer(WidetLayer.FOREGROUND, false, new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                EntitySet<DemoColEnt> ena = canvas2.entities;
                String pStrg = "entities: " + ena.size() + "\n";
                pStrg+= "chunk-size: " + ena.chunkSize + "\n";               
                pStrg+= "duration: " + ena.stats[5] + " msec\n";
                pStrg+= "add: " + ena.stats[0] + "\n";
                pStrg+= "upd: " + ena.stats[1] + "\n";
                pStrg+= "del-x: " + ena.stats[2] + "\n";
                pStrg+= "del-y: " + ena.stats[3] + "\n";
                pStrg+= "av cu: " + ena.stats[4] + "\n";
                pStrg+= "active chunks: " + ena.stats[5] + "\n";
                
                
                pBatch.drawText(pStrg, ShapeUtils.explode(pDims, 20), Align.bottomLeft, 1f, true, Color.CYAN);
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
