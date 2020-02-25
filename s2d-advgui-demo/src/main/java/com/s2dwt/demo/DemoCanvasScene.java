package com.s2dwt.demo;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.s2dwt.core.awidget.IMyWidgetUpdateHandler;
import com.s2dwt.core.canvas.ICanvasRenderer;
import com.s2dwt.core.rendering.SwtDrawerManager;
import com.s2dwt.core.rendering.SwtDrawer_Batch;
import com.s2dwt.core.rendering.SwtDrawer_Shapes;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.stage.ASwtStage;

public class DemoCanvasScene extends ICanvasRenderer<AResourceManager, SwtDrawerManager<AResourceManager>>
        implements IMyWidgetUpdateHandler {

    private double time = 0;

    public DemoCanvasScene(ASwtStage<?, ?> stage, SwtDrawerManager<AResourceManager> pDrawerManager) {
        super(stage, pDrawerManager);
    }

    @Override
    public void act(float delta) {
        time += delta;
    }

    @Override
    protected void _drawIt(Rectangle dimensions) {
        this.cameraHolder.setWantedZoom(1f);
        this.cameraHolder.getCamera().update();

        Batch bb = this.drawerManager.getBatch();
        bb.setProjectionMatrix(this.cameraHolder.getCamera().combined);
        ShapeRenderer sr = this.drawerManager.getShapeRenderer();
        sr.setProjectionMatrix(this.cameraHolder.getCamera().combined);

        try (SwtDrawer_Shapes sd = this.drawerManager.startShapesDrawer()) {
            sd.drawCross(0, 0, 100);
            sd.rect(-dimensions.width / 2f + 1, -dimensions.height / 2f + 1, dimensions.width - 2,
                    dimensions.height - 2);
        }

        AResourceManager rm = this.drawerManager.getResourceManager();
        try (SwtDrawer_Batch<AResourceManager> bd = this.drawerManager.startBatchDrawer()) {
            float atx = ((int) (time * 500f) % 500) - 250f - 192f / 2f;
            float aty = -108f / 2f;
            bd.draw(rm.getTextureRegion("ui2/scene-1.png"), atx, aty, 192, 108);
        }
    }

}
