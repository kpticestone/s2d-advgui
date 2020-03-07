package de.s2d_advgui.demo.cases.canvas;

import javax.annotation.Nonnull;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.awidget.IMyWidgetUpdateHandler;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.canvas.SwtCanvas;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.AResourceManager;

public class SwtCanvas_DemoScene2 extends SwtCanvas<AResourceManager, SwtDrawerManager<AResourceManager>>
        implements IMyWidgetUpdateHandler {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtCanvas_DemoScene2(@Nonnull ISwtWidget<? extends Group> pParent) {
        super(pParent, new SwtDrawerManager<>(pParent.getContext().getResourceManager()));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void _drawIt(Rectangle dimensions) {
        CameraHolder cameraHolder = this.drawerManager.getCameraHolder();
        cameraHolder.setWantedZoom(2f);
        cameraHolder.getCamera().update();

        Batch bb = this.drawerManager.getBatch();
        bb.setProjectionMatrix(cameraHolder.getCamera().combined);
        ShapeRenderer sr = this.drawerManager.getShapeRenderer();
        sr.setProjectionMatrix(cameraHolder.getCamera().combined);

        try (SwtDrawer_Batch<AResourceManager> bd = this.drawerManager.startBatchDrawer()) {
            bd.draw("icons/128/convertible_blue.png", -10, -10, 20, 20);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
