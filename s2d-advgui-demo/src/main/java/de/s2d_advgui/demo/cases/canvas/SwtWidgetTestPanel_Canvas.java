package de.s2d_advgui.demo.cases.canvas;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.canvas.SwtCanvas;
import de.s2d_advgui.core.layoutmanager.SwtLayoutManager_Flow;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.stage.ASwtStage;
import de.s2d_advgui.demo.DemoStage;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanel;

public class SwtWidgetTestPanel_Canvas extends ASwtWidgetTestPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWidgetTestPanel_Canvas(ISwtWidget<? extends Group> pParent) {
        super(pParent);
        for (int i = 0; i < 4; i++) {
            ASwtStage<?, ?> a = (DemoStage) this.context;
            SwtDrawerManager<AResourceManager> b = new SwtDrawerManager<>(this.context.getResourceManager());
            DemoCanvasScene pRenderer = new DemoCanvasScene(a, b);
            SwtCanvas<AResourceManager, SwtDrawerManager<AResourceManager>> canvas = new SwtCanvas<>(this, pRenderer);
            canvas.addDrawerBackground(new InternalWidgetDrawerBatch() {
                @Override
                protected void _drawIt(SwtDrawer_Batch<?> batch, Vector2 pScreenCoords, Rectangle dims) {
                    batch.setColor(0.6f, 0.6f, 0.8f, .75f);
                    batch.draw(context.getTextureRegion("icons/128/cow.png"), dims.x + 10, dims.y + 10, dims.width - 20,
                            dims.height - 20);
                }
            });
            this.addUpdateHandler(pRenderer);
        }

        this.setLayoutManager(new SwtLayoutManager_Flow(200, 200));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
