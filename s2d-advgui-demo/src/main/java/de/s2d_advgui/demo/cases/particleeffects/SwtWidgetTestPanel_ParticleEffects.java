package de.s2d_advgui.demo.cases.particleeffects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.awidget.WidetLayer;
import de.s2d_advgui.core.layoutmanager.SwtLayoutManager_GridByColumns;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.utils.ShapeUtils;
import de.s2d_advgui.demo.DemoResourceManager;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanel;

public class SwtWidgetTestPanel_ParticleEffects extends ASwtWidgetTestPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWidgetTestPanel_ParticleEffects(ISwtWidget<? extends Group> pParent) {
        super(pParent);
        DemoResourceManager rm = (DemoResourceManager) this.getContext().getResourceManager();
        for (int i = 0; i < 1; i++) {
            int fi = i;
            SwtDrawerManager<DemoResourceManager> drawerManager2 = new SwtDrawerManager<>(rm);
            SwtCanvas_ParticleEffects canvas2 = new SwtCanvas_ParticleEffects(this, drawerManager2);
            canvas2.addDrawer(WidetLayer.FOREGROUND, false, new InternalWidgetDrawerBatch() {
                @Override
                protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                    String pStrg = "xyz " + fi;
                    pBatch.drawText(pStrg, ShapeUtils.explode(pDims, 20), Align.bottom, 1f, true, Color.CYAN);
                }
            });
        }
        this.setLayoutManager(new SwtLayoutManager_GridByColumns(1));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
