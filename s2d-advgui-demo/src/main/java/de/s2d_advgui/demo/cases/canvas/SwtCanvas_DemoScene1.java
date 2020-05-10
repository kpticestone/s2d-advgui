package de.s2d_advgui.demo.cases.canvas;

import javax.annotation.Nonnull;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.animations.AnimationManager;
import de.s2d_advgui.core.awidget.IMyWidgetUpdateHandler;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.canvas.SwtCanvas;
import de.s2d_advgui.core.geom.animations.Animation_ChangeBounds;
import de.s2d_advgui.core.geom.animations.IAnimationListener_ChangeBounds;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.utils.ShapeUtils;

public class SwtCanvas_DemoScene1 extends SwtCanvas<AResourceManager, SwtDrawerManager<AResourceManager>>
        implements IMyWidgetUpdateHandler {
    // -------------------------------------------------------------------------------------------------------------------------
    private double time = 0;

    // -------------------------------------------------------------------------------------------------------------------------
    Rectangle cur = new Rectangle(0, 0, 192, 108);

    // -------------------------------------------------------------------------------------------------------------------------
    Rectangle src = null;

    // -------------------------------------------------------------------------------------------------------------------------
    Rectangle dst = null;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtCanvas_DemoScene1(@Nonnull ISwtWidget<? extends Group> pParent) {
        super(pParent, new SwtDrawerManager<>(pParent.getContext().getResourceManager()));
        this.newAnim();
        this.addUpdateHandler(delta -> this.time += delta);
        this.addDrawerBackground(new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> batch, Vector2 pScreenCoords, Rectangle dims) {
                batch.setColor(0.6f, 0.6f, 0.8f, .75f);
                batch.draw("icons/128/cow.png", ShapeUtils.explode(dims, -10));
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void _drawIt(Rectangle dimensions) {
        CameraHolder cameraHolder = this.drawerManager.getCameraHolder();
        cameraHolder.setWantedZoom(1f);
        cameraHolder.getCamera().update();

        Batch bb = this.drawerManager.getBatch();
        bb.setProjectionMatrix(cameraHolder.getCamera().combined);
        ShapeRenderer sr = this.drawerManager.getShapeRenderer();
        sr.setProjectionMatrix(cameraHolder.getCamera().combined);

        try (SwtDrawer_Batch<AResourceManager> bd = this.drawerManager.startBatchDrawer()) {
            bd.draw("icons/128/convertible_blue.png", this.cur);
        }
    }

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

        Animation_ChangeBounds back = new Animation_ChangeBounds(0f, 1000f, this.src, this.dst,
                new IAnimationListener_ChangeBounds() {
                    @Override
                    public void onChange(Rectangle pRect) {
                        SwtCanvas_DemoScene1.this.cur.set(pRect);
                    }
                });
        back.setCloseListener(() -> {
            newAnim();
        });
        AnimationManager.getInstance().startAnimation(back);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
