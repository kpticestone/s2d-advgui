package de.s2d_advgui.core.canvas;

import javax.annotation.Nonnull;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import de.s2d_advgui.core.awidget.IInternalWidgetDrawer;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.rendering.ISwtBatchSaver;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.screens.MasterViewport;
import de.s2d_advgui.core.screens.SlaveViewport;
import de.s2d_advgui.core.stage.ASwtStage;
import de.s2d_advgui.core.utils.CalcUtils;

public abstract class ICanvasRenderer<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>>
        implements IInternalWidgetDrawer {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final CameraHolder cameraHolder;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final ASwtStage<?, ?> stage;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final DM drawerManager;

    // -------------------------------------------------------------------------------------------------------------------------
    protected ISwtWidget<?> canvas;

    // -------------------------------------------------------------------------------------------------------------------------
    public ICanvasRenderer(ASwtStage<?, ?> stage, DM pDrawerManager) {
        this.stage = stage;
        this.drawerManager = pDrawerManager;
        this.cameraHolder = pDrawerManager.getCameraHolder();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final DM getDrawerManager() {
        return this.drawerManager;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void coord123(Vector2 in, Vector2 out) {
        this.cameraHolder.unproject(in, out);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void drawIt(ISwtDrawerManager<?> pDrawerManager, Vector2 pScreenCoords, Rectangle lDims) {
        Batch pBatch = pDrawerManager.getBatch();
        try (ISwtBatchSaver saver = this.drawerManager.batchSave()) {
            pBatch.end();
            MasterViewport vp = this.stage.getViewport();
            Rectangle rDims = this.stage.getStageDimensions();
            float gs = this.stage.getGuiScale();
            this.cameraHolder.setViewport(pScreenCoords.x, (rDims.height * gs) - (pScreenCoords.y), lDims.width * gs,
                    lDims.height * gs);
            try (SlaveViewport slaveViewPort = vp.runSlaveViewport(this.cameraHolder)) {
                OrthographicCamera cam = this.cameraHolder.getCamera();
                cam.zoom = CalcUtils.approachExponential(cam.zoom, this.cameraHolder.getWantedZoom(),
                        Gdx.graphics.getDeltaTime());
                cam.update();
                this._drawIt(this.cameraHolder.getLastDims());
            }
        } finally {
            pBatch.begin();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected abstract void _drawIt(Rectangle dimensions);

    // -------------------------------------------------------------------------------------------------------------------------
}
