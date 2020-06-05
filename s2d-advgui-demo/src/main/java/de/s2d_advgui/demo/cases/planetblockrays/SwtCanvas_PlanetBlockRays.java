package de.s2d_advgui.demo.cases.planetblockrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.leo.commons.geom.Point;
import com.leo.spheres.core.chunksystem.RayBlockHandler;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.canvas.SwtCanvas;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Shapes;
import de.s2d_advgui.demo.DemoResourceManager;

public final class SwtCanvas_PlanetBlockRays
        extends SwtCanvas<DemoResourceManager, SwtDrawerManager<DemoResourceManager>> {
    // -------------------------------------------------------------------------------------------------------------------------
    float sunDirection = -90f;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtCanvas_PlanetBlockRays(ISwtWidget<? extends Group> pParent,
            SwtDrawerManager<DemoResourceManager> pDrawerManager) {
        super(pParent, pDrawerManager);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void _drawIt(Rectangle dimensions) {
        super._drawIt(dimensions);

        CameraHolder cameraHolder = this.drawerManager.getCameraHolder();
        OrthographicCamera cam = cameraHolder.getCamera();
        //         cameraHolder.setWantedZoom(.05f);
        cameraHolder.setWantedZoom(.1f);
        //        cam.zoom = .05f;
        //        cam.rotate(.5f);
        // cam.settranslate(0, 100);
        //        cam.up.set(0, 1, 0);
        cam.position.x = 0;
        cam.position.y = 0;
        // cam.direction.set(1, 1, 0);
        //        cam.update();

        //        OrthographicCamera camera = new OrthographicCamera(48, 48);
        //        camera.update();

        //        System.err.println(this.actor.getWidth() + "x" + this.actor.getHeight());

        this.sunDirection += Gdx.graphics.getDeltaTime() * 32;
        //this.sunDirection = this.sunDirection % 90 + 90;
        //        this.sunDirection = 33;
        //        this.sunDirection = 54;

        Batch bb = this.drawerManager.getBatch();
        bb.setProjectionMatrix(cam.combined);

        ShapeRenderer sr = this.drawerManager.getShapeRenderer();
        sr.setProjectionMatrix(cam.combined);

        RayBlockHandler rayBlockHandler = new RayBlockHandler(new Vector2(), 1000f, this.sunDirection);
        
        try (SwtDrawer_Shapes sdr = this.drawerManager.startShapesDrawer()) {
            sdr.draw(rayBlockHandler.getRay());
            for (int[] p : rayBlockHandler) {
                sdr.draw(new Rectangle(p[0], p[1], 1, 1));
            }
        }
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
}
