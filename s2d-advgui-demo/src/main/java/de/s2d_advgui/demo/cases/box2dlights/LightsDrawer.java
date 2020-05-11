package de.s2d_advgui.demo.cases.box2dlights;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.RayHandler;
import de.s2d_advgui.core.awidget.IInternalWidgetDrawer;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.utils.AffineHelper;
import de.s2d_advgui.demo.DemoResourceManager;

public final class LightsDrawer implements IInternalWidgetDrawer {
    // -------------------------------------------------------------------------------------------------------------------------
    private World world;
    private RayHandler rayHandler;
    private Matrix4 oldMatrix;
    private SwtDrawerManager<?> drawerManager;

    // -------------------------------------------------------------------------------------------------------------------------
    public LightsDrawer(SwtDrawerManager<DemoResourceManager> pDrawerManager) {
        this.drawerManager = pDrawerManager;
        this.world = new World(new Vector2(0, -10), true);
        this.rayHandler = new RayHandler(this.world);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public World getWorld() {
        return this.world;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public RayHandler getRayHandler() {
        return this.rayHandler;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void drawIt(ISwtDrawerManager<?> pDrawerManagerDontUse, Vector2 pScreenCoords, Rectangle pDims) {
        pDrawerManagerDontUse.getBatch().flush();
        this.drawerManager.getBatch().flush();
        
        CameraHolder cameraHolder = this.drawerManager.getCameraHolder();
        OrthographicCamera cam = cameraHolder.getCamera();
        if (!AffineHelper.equals(this.oldMatrix, cam.combined)) {
            Rectangle ld = cameraHolder.getLastDims();
            this.rayHandler.setBlurNum(0);
            this.oldMatrix = new Matrix4(cam.combined);
            //this.rayHandler.setCombinedMatrix(cam.combined, 0, 0, 1, 1);
            this.rayHandler.setCombinedMatrix(cam);
            this.rayHandler.useCustomViewport((int) ld.x, (int) ld.y, (int) ld.width, (int) ld.height);
            this.rayHandler.resizeFBO((int) ld.width, (int) ld.height);
            this.rayHandler.setAmbientLight(Color.BLACK);
        }
//        this.world.step(Gdx.graphics.getDeltaTime(), 8, 3);
        this.world.step(Gdx.graphics.getDeltaTime(), 64, 24);

        this.rayHandler.updateAndRender();
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
