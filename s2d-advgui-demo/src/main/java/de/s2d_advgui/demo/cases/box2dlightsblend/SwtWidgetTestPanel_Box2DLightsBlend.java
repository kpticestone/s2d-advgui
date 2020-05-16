package de.s2d_advgui.demo.cases.box2dlightsblend;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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

public class SwtWidgetTestPanel_Box2DLightsBlend extends ASwtWidgetTestPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWidgetTestPanel_Box2DLightsBlend(ISwtWidget<? extends Group> pParent) {
        super(pParent);
        DemoResourceManager rm = (DemoResourceManager) this.getContext().getResourceManager();

        int[] sources = new int[] { GL20.GL_ZERO, GL20.GL_ONE, GL20.GL_DST_COLOR, GL20.GL_ONE_MINUS_DST_COLOR,
                GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_SRC_ALPHA_SATURATE };
        

        int i = 0;
        WidetLayer layer = WidetLayer.FOREGROUND;
        // for (int i = 0; i < 1; i++)
        for (int useSource : sources) {
            for (int useDest : sources) {
                int fi = i++;
                SwtDrawerManager<DemoResourceManager> drawerManager2 = new SwtDrawerManager<>(rm);
                LightsDrawer ldr = new LightsDrawer(drawerManager2, pParent.getContext()) {
                    @Override
                    protected void _onUpdate() {
                        rayHandler.simpleBlendFunc.set(useSource, useDest);
//                        rayHandler.simpleBlendFunc.set(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
                        rayHandler.simpleBlendFunc.apply();

//                        rayHandler.shadowBlendFunc.set(useSource, useDest);
                        rayHandler.shadowBlendFunc.set(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
//                        rayHandler.shadowBlendFunc.set(GL20.GL_DST_COLOR, GL20.GL_SRC_ALPHA_SATURATE);
                        rayHandler.shadowBlendFunc.apply();
                    }
                };
                SwtCanvas_Box2DLightsBlend canvas2 = new SwtCanvas_Box2DLightsBlend(this, ldr, drawerManager2);
                canvas2.addDrawer(WidetLayer.FOREGROUND, false, new InternalWidgetDrawerBatch() {
                    @Override
                    protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                        String pStrg = "xyz " + fi + " (layer: " + layer + ")";
                        pBatch.drawText(pStrg, ShapeUtils.explode(pDims, 20), Align.bottom, .5f, true, Color.CYAN);
                    }
                });
                canvas2.addDrawer(layer, false, ldr);
            }
        }
        this.setLayoutManager(new SwtLayoutManager_GridByColumns(sources.length));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
