package de.s2d_advgui.core.canvas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.RayHandler;
import de.s2d_advgui.commons.TOldCompatibilityCode;
import de.s2d_advgui.core.awidget.IInternalWidgetDrawer;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.rendering.ISwtBatchSaver;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.screens.MasterViewport;
import de.s2d_advgui.core.screens.SlaveViewport;
import de.s2d_advgui.core.stage.ISwtStage;
import de.s2d_advgui.core.utils.AffineHelper;
import de.s2d_advgui.core.utils.CalcUtils;

public abstract class LightsDrawer implements IInternalWidgetDrawer {
    // -------------------------------------------------------------------------------------------------------------------------
    protected final World world;
    protected final RayHandler rayHandler;
    private Matrix4 oldMatrix;
    protected final SwtDrawerManager<?> drawerManager;
    protected final ISwtStage<?, ?> stage;

    // -------------------------------------------------------------------------------------------------------------------------
    public LightsDrawer(SwtDrawerManager<?> pDrawerManager, ISwtStage<?, ?> pSwtStage) {
        this.drawerManager = pDrawerManager;
        this.stage = pSwtStage;
        this.world = new World(new Vector2(0, -10), true);
        this.rayHandler = new RayHandler(this.world);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final World getWorld() {
        return this.world;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final RayHandler getRayHandler() {
        return this.rayHandler;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void drawIt(ISwtDrawerManager<?> pDrawerManagerOfGui, Vector2 pScreenCoords, Rectangle pDims) {
        Rectangle rDims = this.stage.getStageDimensions();
        try (ISwtBatchSaver jf = pDrawerManagerOfGui.batchSave(true)) {
            CameraHolder cameraHolder = this.drawerManager.getCameraHolder();
            OrthographicCamera cam = cameraHolder.getCamera();
            if (!AffineHelper.equals(this.oldMatrix, cam.combined)) {
                Rectangle ld = cameraHolder.getLastDims();
                this.rayHandler.setBlurNum(0);
                this.rayHandler.setBlur(true);
                this.rayHandler.setShadows(true);
                this.oldMatrix = new Matrix4(cam.combined);
                this.rayHandler.setCombinedMatrix(cam);
                this.rayHandler.useCustomViewport((int) (rDims.x + ld.x), (int) (rDims.y + ld.y), (int) ld.width,
                        (int) ld.height);
                this.rayHandler.resizeFBO((int) ld.width, (int) ld.height);
            }
            this.world.step(Gdx.graphics.getDeltaTime(), 8, 3);
            
            this._onUpdate();
            
            this.rayHandler.updateAndRender();
        }

        // Der RayHandler verursacht aus irgendeinem Grund eine Zerstörung der Viewports bzw.
        // der Camera, obwohl es keinen direkten Hinweis auf deren Verwendung gibt.
        // Ich kann mir aber vorstellen, dass irgendein intern verwendeter Shader irgendwelche
        // "Dinge" an das grafische Subsystem übergibt, die sonst durch die Einrichtung der Viewports
        // gemacht werden.
        if (TOldCompatibilityCode.TRUE) {
            MasterViewport vp = this.stage.getViewport();
            float gs = this.stage.getGuiScale();
            CameraHolder cameraHolder = pDrawerManagerOfGui.getCameraHolder();
            cameraHolder.setViewport(pScreenCoords.x, (rDims.height * gs) - (pScreenCoords.y), pDims.width * gs,
                    pDims.height * gs);
            try (SlaveViewport slaveViewPort = vp.runSlaveViewport(cameraHolder)) {
                OrthographicCamera cam = cameraHolder.getCamera();
                cam.zoom = CalcUtils.approachExponential(cam.zoom, cameraHolder.getWantedZoom(),
                        Gdx.graphics.getDeltaTime());
                cam.update();
            }
        }
    }

    protected abstract void _onUpdate();

    // -------------------------------------------------------------------------------------------------------------------------
}
