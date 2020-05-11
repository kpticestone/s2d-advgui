package de.s2d_advgui.demo.cases.particleeffects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter.SpawnShape;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.canvas.SwtCanvas;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.demo.DemoResourceManager;

public final class SwtCanvas_ParticleEffects
        extends SwtCanvas<DemoResourceManager, SwtDrawerManager<DemoResourceManager>> {
    // -------------------------------------------------------------------------------------------------------------------------
    private ParticleEffect additiveEffect;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtCanvas_ParticleEffects(ISwtWidget<? extends Group> pParent,
            SwtDrawerManager<DemoResourceManager> pDrawerManager) {
        super(pParent, pDrawerManager);

        additiveEffect = new ParticleEffect();
        additiveEffect.load(Gdx.files.classpath("particles/snow1"), Gdx.files.classpath("icons/128"));
        additiveEffect.setEmittersCleanUpBlendFunction(false);
        additiveEffect.flipY();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void _drawIt(Rectangle dimensions) {
        super._drawIt(dimensions);

        CameraHolder cameraHolder = this.drawerManager.getCameraHolder();
        OrthographicCamera cam = cameraHolder.getCamera();
//        cameraHolder.setWantedZoom(.05f);
        cameraHolder.setWantedZoom(.1f);
//        cam.zoom = .05f;
//        cam.rotate(.5f);
        // cam.settranslate(0, 100);
//        cam.up.set(0, 1, 0);
//        cam.position.x = 0;
        cam.position.y = 100;
        // cam.direction.set(1, 1, 0);
//        cam.update();

//        OrthographicCamera camera = new OrthographicCamera(48, 48);
//        camera.update();

        Batch bb = this.drawerManager.getBatch();
        bb.setProjectionMatrix(cam.combined);

        ShapeRenderer sr = this.drawerManager.getShapeRenderer();
        sr.setProjectionMatrix(cam.combined);

        try (SwtDrawer_Batch<?> sdr = this.drawerManager.startBatchDrawer()) {

            sdr.setColor(Color.WHITE);

            ParticleEmitter emmi = additiveEffect.getEmitters().get(0);
            emmi.getSpawnShape().setShape(SpawnShape.point);
            // emmi.setMinParticleCount(0);
            // emmi.setMaxParticleCount(2000);
            emmi.getSpawnWidth().setLow(0);
            emmi.getSpawnWidth().setHigh(0);

            emmi.getSpawnHeight().setLow(0);
            emmi.getSpawnHeight().setHigh(0);

            emmi.getLife().setHigh(10000);
            emmi.getGravity().setHigh(0, 0);
            emmi.getAngle().setHighMin(45);
            emmi.getAngle().setHighMax(180);
            emmi.getWind().setHighMin(10);
            emmi.getWind().setHighMax(100);
//            emmi.flipY();
            emmi.getXOffsetValue().setLow(0, 0); // keine auswirkung. :(
            emmi.getYOffsetValue().setLow(-200, 200); // -200, 200 ist richtig!

            float delta = getContext().getDelta();
            additiveEffect.draw(sdr.getBatch(), delta);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
