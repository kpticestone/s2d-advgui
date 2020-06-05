package de.s2d_advgui.demo.cases.planetchunk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.leo.spheres.core.chunksystem.AChunkSystem;
import com.leo.spheres.core.chunksystem.Chunk;
import com.leo.spheres.core.chunksystem.PlanetChunkSystem;
import com.leo.spheres.core.chunksystem.PlanetGenerator;
import com.leo.spheres.core.chunksystem.PlanetLightMapCalculator;

import de.s2d_advgui.commons.TOldCompatibilityCode;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.canvas.SwtCanvas;
import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.rendering.SwtDrawer_Shapes;
import de.s2d_advgui.demo.DemoResourceManager;

public final class SwtCanvas_PlanetChunk
        extends SwtCanvas<DemoResourceManager, SwtDrawerManager<DemoResourceManager>> {
    // -------------------------------------------------------------------------------------------------------------------------
    float sunDirection = -90f;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtCanvas_PlanetChunk(ISwtWidget<? extends Group> pParent,
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
        cameraHolder.setWantedZoom(.01f);
//        cam.zoom = .05f;
//        cam.rotate(.5f);
        // cam.settranslate(0, 100);
//        cam.up.set(0, 1, 0);
        cam.position.x = AChunkSystem.CHUNKSIZE;
        cam.position.y = AChunkSystem.CHUNKSIZE;
        // cam.direction.set(1, 1, 0);
//        cam.update();

//        OrthographicCamera camera = new OrthographicCamera(48, 48);
//        camera.update();

//        System.err.println(this.actor.getWidth() + "x" + this.actor.getHeight());

        this.sunDirection += Gdx.graphics.getDeltaTime();
        //this.sunDirection = this.sunDirection % 90 + 90;
        this.sunDirection = 33;
        this.sunDirection = 54;

        Batch bb = this.drawerManager.getBatch();
        bb.setProjectionMatrix(cam.combined);

        ShapeRenderer sr = this.drawerManager.getShapeRenderer();
        sr.setProjectionMatrix(cam.combined);

        try (SwtDrawer_Shapes sdr = this.drawerManager.startShapesDrawer()) {
            PlanetLightMapCalculator.calcChunkBasics(this.sunDirection, sdr);
            sdr.getRenderer().translate(AChunkSystem.CHUNKSIZE, 0, 0);
            PlanetLightMapCalculator.calcChunkBasics(this.sunDirection, sdr);
            sdr.getRenderer().translate(0, AChunkSystem.CHUNKSIZE, 0);
            PlanetLightMapCalculator.calcChunkBasics(this.sunDirection, sdr);
            sdr.getRenderer().translate(-AChunkSystem.CHUNKSIZE, 0, 0);
            PlanetLightMapCalculator.calcChunkBasics(this.sunDirection, sdr);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
