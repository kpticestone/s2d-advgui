package de.s2d_advgui.demo.cases.canvas;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;

import de.s2d_advgui.animations.AnimationManager;
import de.s2d_advgui.commons.TOldCompatibilityCode;
import de.s2d_advgui.core.awidget.IMyWidgetUpdateHandler;
import de.s2d_advgui.core.canvas.ICanvasRenderer;
import de.s2d_advgui.core.geom.animations.Animation_ChangeBounds;
import de.s2d_advgui.core.geom.animations.IAnimationListener_ChangeBounds;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.rendering.SwtDrawer_Shapes;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.stage.ASwtStage;

public class DemoCanvasScene extends ICanvasRenderer<AResourceManager, SwtDrawerManager<AResourceManager>>
        implements IMyWidgetUpdateHandler {
    // -------------------------------------------------------------------------------------------------------------------------
    private double time = 0;

    // -------------------------------------------------------------------------------------------------------------------------
    public DemoCanvasScene(ASwtStage<?, ?> stage, SwtDrawerManager<AResourceManager> pDrawerManager) {
        super(stage, pDrawerManager);
        this.newAnim();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void act(float delta) {
        this.time += delta;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void _drawIt(Rectangle dimensions) {
        this.cameraHolder.setWantedZoom(1f);
        this.cameraHolder.getCamera().update();

        Batch bb = this.drawerManager.getBatch();
        bb.setProjectionMatrix(this.cameraHolder.getCamera().combined);
        ShapeRenderer sr = this.drawerManager.getShapeRenderer();
        sr.setProjectionMatrix(this.cameraHolder.getCamera().combined);

        if (TOldCompatibilityCode.FALSE) {
            try (SwtDrawer_Shapes sd = this.drawerManager.startShapesDrawer()) {
                sd.drawCross(0, 0, 100);
                sd.rect(-dimensions.width / 2f + 1, -dimensions.height / 2f + 1, dimensions.width - 2,
                        dimensions.height - 2);
            }
        }
        AResourceManager rm = this.drawerManager.getResourceManager();
        try (SwtDrawer_Batch<AResourceManager> bd = this.drawerManager.startBatchDrawer()) {
//			System.err.println("cur: " + cur);
            bd.draw(rm.getTextureRegion("icons/128/convertible_blue.png"), this.cur.x, this.cur.y, this.cur.width,
                    this.cur.height);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    Rectangle cur = new Rectangle(0, 0, 192, 108);
    Rectangle src = null;
    Rectangle dst = null;

    // -------------------------------------------------------------------------------------------------------------------------
    void newAnim() {
        RandomXS128 rand = new RandomXS128();
        this.src = new Rectangle(this.cur);
        this.dst = new Rectangle(this.cur.x + rand.nextInt(100) - 50, this.cur.y + rand.nextInt(100) - 50,
                this.cur.width + rand.nextInt(100) - 50, this.cur.height + rand.nextInt(100) - 50);

        int jj = 100;
        if (this.dst.x > jj)
            this.dst.x = jj;
        if (this.dst.y > jj)
            this.dst.y = jj;
        if (this.dst.x < -jj)
            this.dst.x = -jj;
        if (this.dst.y < -jj)
            this.dst.y = -jj;
        if (this.dst.width < 100)
            this.dst.width = 100;
        if (this.dst.height < 100)
            this.dst.height = 100;
        if (this.dst.width > 300)
            this.dst.width = 300;
        if (this.dst.height > 300)
            this.dst.height = 300;
//		System.err.println("dst: " + dst);

        Animation_ChangeBounds back = new Animation_ChangeBounds(0f, 1000f, this.src, this.dst,
                new IAnimationListener_ChangeBounds() {
                    @Override
                    public void onChange(Rectangle pRect) {
                        DemoCanvasScene.this.cur.set(pRect);
                    }
                });
        back.setCloseListener(() -> {
            newAnim();
        });
        AnimationManager.getInstance().startAnimation(back);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
