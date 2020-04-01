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
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.canvas.SwtCanvas;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
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
        Animation_Single aa = new Animation_Single(0f, 10000f, 1f, 200f, new IAnimationListener_Single() {
            @Override
            public void onHit(float cur) throws Exception {
                // zoom = Math.pow(cur, 2);
                if (SwtCanvas_Mandelbrot.this.autozoom) {
                    SwtCanvas_Mandelbrot.this.zoom = cur;
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

    Color[] useCs = null;
    private int lastIa;
    private double lastSx;
    private double lastSy;
    private double lastSca;
    private int lastSteps;

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
        double ia = 2 * 1f / sca;
        if (ia > 256) {
            ia = 256;
        }
        double sx = (float) (this.useLeft / sca);
        double sy = (float) (this.useTop / sca);

        if (this.autozoom) {
            this.steps = (int) (100+this.zoom*2);
        }

        int useIa = (int) ia;
        if (this.memos == null || this.lastIa != useIa || this.lastSx != sx || this.lastSy != sy || this.lastSca != sca
                || this.lastSteps != this.steps) {
            this.lastSx = sx;
            this.lastSy = sy;
            this.lastSca = sca;
            this.lastSteps = this.steps;
            this.min = Integer.MAX_VALUE;
            this.max = Integer.MIN_VALUE;
            this.lastIa = useIa;
            this.memos = new int[useIa * 2][useIa * 2];
            double l = sca;
            double ax = (-ia + sx) * sca;
            double das = (-ia + sy) * sca;
            double ay;
            int kl;
            for (int x = -useIa; x < useIa; x++) {
                ay = das;
                for (int y = -useIa; y < useIa; y++) {
                    kl = this.ch.checkC(ax, ay, this.steps - 1);
                    this.memos[useIa + x][useIa + y] = kl;
                    this.min = Math.min(kl, this.min);
                    this.max = Math.max(kl, this.max);
                    ay += l;
                }
                ax += l;
            }
            {
                int uu = this.max - this.min + 1;
                this.useCs = new Color[uu];
                for (int i = 0; i < uu; i++) {
                    float axx = i / (float) uu;
                    this.useCs[i] = new Color(axx, axx, .5f + axx / 2f, 1f);
                }
                this.useCs[uu - 1] = Color.BLACK;
            }
        }

        try (SwtDrawer_Batch<DemoResourceManager> pBatch = this.drawerManager.startBatchDrawer()) {
            for (int x = -useIa; x < useIa; x++) {
                for (int y = -useIa; y < useIa; y++) {
                    int kl = this.memos[useIa + x][useIa + y];
                    pBatch.setColor(this.useCs[kl - this.min]);
                    pBatch.draw(rras, x, y, 1f, 1f);
                }
            }
        }
    }

    CheckHolder ch = new CheckHolder();
    private int[][] memos;
    private int min;
    private int max;

    static class CheckHolder {
        // -------------------------------------------------------------------------------------------------------------------------
        double reZ = 0;
        double imZ = 0;
        double reZ_minus1 = 0;
        double imZ_minus1 = 0;
        int i;

        // -------------------------------------------------------------------------------------------------------------------------
        public int checkC(double pX, double pY, int steps) {
            this.reZ = this.imZ = this.reZ_minus1 = this.imZ_minus1 = 0;
            for (this.i = 0; this.i < steps; this.i++) {
                this.imZ = 2 * this.reZ_minus1 * this.imZ_minus1 + pY;
                this.reZ = this.reZ_minus1 * this.reZ_minus1 - this.imZ_minus1 * this.imZ_minus1 + pX;
                if (this.reZ * this.reZ + this.imZ * this.imZ > 4) return this.i;
                this.reZ_minus1 = this.reZ;
                this.imZ_minus1 = this.imZ;
            }
            return this.i;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
