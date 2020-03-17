package de.s2d_advgui.demo.cases.mandelbrot;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.animations.AnimationManager;
import de.s2d_advgui.animations.IAnimationListener_Close;
import de.s2d_advgui.animations.impl.Animation_Single;
import de.s2d_advgui.animations.impl.IAnimationListener_Single;
import de.s2d_advgui.commons.TOldCompatibilityCode;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.canvas.SwtCanvas;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.rendering.SwtDrawer_Shapes;
import de.s2d_advgui.demo.DemoResourceManager;

public final class SwtCanvas_Mandelbrot extends SwtCanvas<DemoResourceManager, SwtDrawerManager<DemoResourceManager>> {
    // -------------------------------------------------------------------------------------------------------------------------
    float useTop = 0.30380f;
    float useLeft = -1.0019f;
    double zoom = 3d;
    boolean autozoom = false;
    int steps = 32;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtCanvas_Mandelbrot(ISwtWidget<? extends Group> pParent,
            SwtDrawerManager<DemoResourceManager> pDrawerManager) {
        super(pParent, pDrawerManager);
        this.asi();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    void asi() {
        Animation_Single aa = new Animation_Single(0f, 10000f, 1f, 100f, new IAnimationListener_Single() {
            @Override
            public void onHit(float cur) throws Exception {
                // zoom = Math.pow(cur, 2);
                if (autozoom) {
                    zoom = cur;
                }
            }
        });
        aa.setCloseListener(new IAnimationListener_Close() {
            @Override
            public void onAnimationClosed() throws Exception {
                asi();
            }
        });
        AnimationManager.getInstance().startAnimation(aa);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void _drawIt(Rectangle dimensions) {
        super._drawIt(dimensions);

        CameraHolder cameraHolder = this.drawerManager.getCameraHolder();
        OrthographicCamera cam = cameraHolder.getCamera();
        cam.zoom = 1f;
//        cam.rotate(.5f);
        cam.up.set(0, 1, 0);
        cam.direction.set(0, 0, -1);
        cam.update();

        Batch bb = this.drawerManager.getBatch();
        bb.setProjectionMatrix(cam.combined);

        ShapeRenderer sr = this.drawerManager.getShapeRenderer();
        sr.setProjectionMatrix(cam.combined);

        Rectangle pDims = dimensions;
        TextureRegion rras = this.drawerManager.getResourceManager().getColorTextureRegion(Color.WHITE);

        double sca = 1f / Math.pow(this.zoom, 4); // 100000f;
//        sca /= 100f;
//        sca = this.zoom;
//        sca = 1f / 6000000f;
        double ia = 2 * 1f / sca;
        if (ia > 256) {
            ia = 256;
        }
        double sx = (float) (this.useLeft / sca);
        double sy = (float) (this.useTop / sca);

//        int steps = 30 + (int) (this.zoom / 10f); // +(int) (100/sca);
//        if (steps > 256) steps = 256;
//
//        steps = 256;
        if( autozoom )
        {
            steps = (int) (this.zoom * 10);
        }

        Color[] useCs = new Color[steps];
        for (int i = 0; i < steps; i++) {
            float ax = i / (float) steps;
            useCs[i] = new Color(0, ax, 0, 1f);
        }
        useCs[steps - 1] = Color.BLACK;

        if (TOldCompatibilityCode.FALSE) {
            try (SwtDrawer_Batch<DemoResourceManager> pBatch = this.drawerManager.startBatchDrawer()) {
                for (int x = (int) -ia; x < ia; x++) {
                    for (int y = (int) -ia; y < ia; y++) {
                        int kl = ch.checkC(
                                (x + sx) * sca,
                                (y + sy) * sca,
                                steps - 1);
                        pBatch.setColor(useCs[kl]);
                        pBatch.draw(rras, (float) x, (float) y, 1f, 1f);
                    }
                }
            }
        } else {
            try (SwtDrawer_Batch<DemoResourceManager> pBatch = this.drawerManager.startBatchDrawer()) {
                double l = sca;
                double ax = (-ia + sx) * sca;
                double das = (-ia + sy) * sca;
                double ay;
                int kl;
                for (int x = (int) -ia; x < ia; x++) {
                    ay = das;
                    for (int y = (int) -ia; y < ia; y++) {
                        kl = ch.checkC(ax, ay, steps - 1);
                        pBatch.setColor(useCs[kl]);
                        pBatch.draw(rras, x, y, 1f, 1f);
                        ay += l;
                    }
                    ax += l;
                }
            }
        }

        if (TOldCompatibilityCode.FALSE) {
            try (SwtDrawer_Shapes sdr = this.drawerManager.startShapesDrawer()) {
                sdr.getRenderer().ellipse(-100, -100, 200, 200);
                sdr.getRenderer().line(0, 0, 0, 100);
                sdr.getRenderer().line(0, 0, 100, 0);
            }
        }
    }
    
    CheckHolder ch = new CheckHolder();

    static class CheckHolder {
        // -------------------------------------------------------------------------------------------------------------------------
        double reZ = 0;
        double imZ = 0;
        double reZ_minus1 = 0;
        double imZ_minus1 = 0;
        int i;

        // -------------------------------------------------------------------------------------------------------------------------
        public int checkC(double pX, double pY, int steps) {
            reZ = imZ = reZ_minus1 = imZ_minus1 = 0;
            for (i = 0; i < steps; i++) {
                imZ = 2 * reZ_minus1 * imZ_minus1 + pY;
                reZ = reZ_minus1 * reZ_minus1 - imZ_minus1 * imZ_minus1 + pX;
                if (reZ * reZ + imZ * imZ > 4) return i;
                reZ_minus1 = reZ;
                imZ_minus1 = imZ;
            }
            return i;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
