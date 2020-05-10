package de.s2d_advgui.demo.cases.collider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.commons.TOldCompatibilityCode;
import de.s2d_advgui.core.awidget.IMyWidgetUpdateHandler;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.canvas.SwtCanvas;
import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.collider.Collider;
import de.s2d_advgui.core.geom.collider.ICollider;
import de.s2d_advgui.core.geom.collider.IntersectionListener;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Shapes;
import de.s2d_advgui.core.utils.AffineHelper;
import de.s2d_advgui.core.utils.ShapeUtils;
import de.s2d_advgui.demo.DemoResourceManager;
import de.s2d_advgui.demo.cases.collider.tests.AColliderTest;
import de.s2d_advgui.demo.cases.collider.tests.ColliderTest_CircleConnectedBorders;
import de.s2d_advgui.demo.cases.collider.tests.ColliderTest_CircleInCircle1;
import de.s2d_advgui.demo.cases.collider.tests.ColliderTest_CircleInCircle2;
import de.s2d_advgui.demo.cases.collider.tests.ColliderTest_CircleInRect;
import de.s2d_advgui.demo.cases.collider.tests.ColliderTest_Circles;
import de.s2d_advgui.demo.cases.collider.tests.ColliderTest_ManyRays;
import de.s2d_advgui.demo.cases.collider.tests.ColliderTest_OverlapedPolys;
import de.s2d_advgui.demo.cases.collider.tests.ColliderTest_PolyInRect;
import de.s2d_advgui.demo.cases.collider.tests.ColliderTest_RayBuild1;
import de.s2d_advgui.demo.cases.collider.tests.ColliderTest_RayBuild2;
import de.s2d_advgui.demo.cases.collider.tests.ColliderTest_RayCross;
import de.s2d_advgui.demo.cases.collider.tests.ColliderTest_RayEndpoints;
import de.s2d_advgui.demo.cases.collider.tests.ColliderTest_RayInRect;
import de.s2d_advgui.demo.cases.collider.tests.ColliderTest_RayOverlaps;
import de.s2d_advgui.demo.cases.collider.tests.ColliderTest_RectInPoly;
import de.s2d_advgui.demo.cases.collider.tests.ColliderTest_RectInRect;
import de.s2d_advgui.demo.cases.collider.tests.ColliderTest_RectOverlaps;

public final class SwtCanvas_Collider extends SwtCanvas<DemoResourceManager, SwtDrawerManager<DemoResourceManager>> {
    float cuu = 0;

    public final static class SceneInfo {
        public int colidercount;
        public long constuctionduration;
        public int checks;
        public int hits;
        public long withDraw;
        public long withoutDraw;
    }

    // public int[] intersectionsCounter = { 0, 0, 0, 0, 0, 0 };
    public SceneInfo info = new SceneInfo();
    boolean flip = true;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtCanvas_Collider(ISwtWidget<? extends Group> pParent,
            SwtDrawerManager<DemoResourceManager> pDrawerManager) {
        super(pParent, pDrawerManager);

        this.addUpdateHandler(new IMyWidgetUpdateHandler() {
            @Override
            public void act(float delta) throws Exception {
                SwtCanvas_Collider.this.cuu += delta;
            }
        });
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

        long t1a = System.currentTimeMillis();

//        System.err.println("--------------------------------------------------------");
        List<ICollider> cols = new ArrayList<>();
        List<AColliderTest> tests = new ArrayList<>();
        tests.add(new ColliderTest_RayEndpoints());
        tests.add(new ColliderTest_RayCross());
        tests.add(new ColliderTest_RayOverlaps());
        tests.add(new ColliderTest_OverlapedPolys());
        tests.add(new ColliderTest_Circles());
        tests.add(new ColliderTest_CircleConnectedBorders());
        tests.add(new ColliderTest_CircleInCircle1());
        tests.add(new ColliderTest_CircleInCircle2());
        tests.add(new ColliderTest_RectOverlaps());
        tests.add(new ColliderTest_CircleInRect());
        tests.add(new ColliderTest_RectInRect());
        tests.add(new ColliderTest_RayInRect());
        tests.add(new ColliderTest_RectInPoly());
        tests.add(new ColliderTest_PolyInRect());
        tests.add(new ColliderTest_RayBuild1());
        tests.add(new ColliderTest_RayBuild2());
        tests.add(new ColliderTest_ManyRays());

        {
            int ixox = (int) Math.ceil(Math.sqrt(tests.size()));
            float hhz = (ixox * 100f)/2f;
            int cnt = 0;
            float aty = 0;
            for (AColliderTest test : tests) {
                float atx = cnt;
                test.createCollider(-hhz + atx * 100, hhz - aty * 100, cols);
                if (cnt++ == ixox) {
                    cnt = 0;
                    aty++;
                }
            }
        }

        if (TOldCompatibilityCode.TRUE) {
            cols.add(new Collider(new Rectangle(-150, -150, 300, 300))
                    .getTransformed(AffineHelper.getTranslate(-155, -155))
                    .getTransformed(new Affine2().rotate(this.cuu * 10)));
            cols.add(new Collider(new Circle(0, 0, 150))
                    .getTransformed(AffineHelper.getTranslate(155, -155))
                    .getTransformed(new Affine2().rotate(this.cuu * 10)));

            cols.add(new Collider(ShapeUtils.createGear(50, 145, 155))
                    .getTransformed(new Affine2().rotate(-this.cuu * 20))
                    .getTransformed(AffineHelper.getTranslate(-155, 155))
                    .getTransformed(new Affine2().rotate(this.cuu * 10)));
        }

        // intersectionsCounter = new int[] { 0, 0, 0, 0, 0, 0 };
        long t2a = System.currentTimeMillis();
        this.info.colidercount = cols.size();
        this.info.constuctionduration = (t2a - t1a);

        long t1 = System.currentTimeMillis();
        this.info.checks = 0;
        this.info.hits = 0;
        try (SwtDrawer_Shapes sdr = this.drawerManager.startShapesDrawer()) {
            sdr.setColor(Color.GRAY);
            if (this.flip) {
                for (ICollider c1 : cols) {
                    sdr.drawCollider(c1);
                }
            }
            sdr.setColor(Color.GREEN);
            IntersectionListener il = (context, x, y) -> {
                if (this.flip) {
                    sdr.drawCross(x, y, 4);
                }
                ++this.info.hits;
            };
            for (int i = 0; i < cols.size() - 1; i++) {
                for (int j = i + 1; j < cols.size(); j++) {
                    ICollider a = cols.get(i);
                    ICollider b = cols.get(j);
//                    System.err.println( a + " vs " + b);
                    a.calcIntersections(b, true, il);
                    ++this.info.checks;
                }
            }
        }
        long t2 = System.currentTimeMillis();
        if (this.flip) {
            this.info.withDraw = (t2 - t1);
        } else {
            this.info.withoutDraw = (t2 - t1);
        }
        this.flip = true;
        // flip = !flip;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
