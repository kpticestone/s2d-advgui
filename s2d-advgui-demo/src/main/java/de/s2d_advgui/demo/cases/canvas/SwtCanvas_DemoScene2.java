package de.s2d_advgui.demo.cases.canvas;

import javax.annotation.Nonnull;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.animations.AnimationManager;
import de.s2d_advgui.animations.impl.Animation_Sleep;
import de.s2d_advgui.core.awidget.IMyWidgetUpdateHandler;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.canvas.SwtCanvas;
import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.animations.Animation_ChangeBounds;
import de.s2d_advgui.core.geom.animations.IAnimationListener_ChangeBounds;
import de.s2d_advgui.core.input.ASwtMouseMoveListenerCanvas;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.rendering.SwtDrawer_Shapes;
import de.s2d_advgui.core.resourcemanager.AResourceManager;

public class SwtCanvas_DemoScene2 extends SwtCanvas<AResourceManager, SwtDrawerManager<AResourceManager>>
        implements IMyWidgetUpdateHandler {
    // -------------------------------------------------------------------------------------------------------------------------
    private final Rectangle entity = new Rectangle(0, 0, 40, 20);

    // -------------------------------------------------------------------------------------------------------------------------
    Vector2 mouseScreenCoords = new Vector2();

    // -------------------------------------------------------------------------------------------------------------------------
    Vector2 mouseCanvasCoords = new Vector2();

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtCanvas_DemoScene2(@Nonnull ISwtWidget<? extends Group> pParent, boolean animate) {
        super(pParent, new SwtDrawerManager<>(pParent.getContext().getResourceManager()));
        // if (animate)
        {
            nxt();
            zoomAnim();
        }
        this.addMouseMoveHandler(new ASwtMouseMoveListenerCanvas<>(this) {
            protected boolean onCanvasMouseMove(float pSceneX, float pSceneY, float pCanvasX, float pCanvasY,
                    int pButton) {
                mouseCanvasCoords.set(pCanvasX, pCanvasY);
                mouseScreenCoords.set(pSceneX, pSceneY);
                return true;
            }

            @Override
            protected boolean onCanvasMouseDown(float pSceneX, float pSceneY, float pCanvasX, float pCanvasY,
                    int pButton) {
                mouseCanvasCoords.set(pCanvasX, pCanvasY);
                mouseScreenCoords.set(pSceneX, pSceneY);
                return true;
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void zoomAnim() {
        float rndZoom = .2f + new RandomXS128().nextFloat() * .2f;
        this.drawerManager.getCameraHolder().setWantedZoom(rndZoom);
        AnimationManager.getInstance().startAnimation(new Animation_Sleep(0f, 2500f, () -> {
            zoomAnim();
        }));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    void nxt() {
        RandomXS128 rnd = new RandomXS128();
        Rectangle src = new Rectangle(this.entity);
        Rectangle dst = new Rectangle(-20 + rnd.nextInt(40), -20 + rnd.nextInt(40),
                20 + rnd.nextInt(10),
                20 + rnd.nextInt(10));
        // dst.setPosition(20, 10);
        IAnimationListener_ChangeBounds pListener = pRect -> this.entity.set(pRect);
        Animation_ChangeBounds an = new Animation_ChangeBounds(0f, 5000f, src, dst, pListener);
        an.setCloseListener(() -> {
            nxt();
        });
        AnimationManager.getInstance().startAnimation(an);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void _drawIt(Rectangle dimensions) {
        CameraHolder cameraHolder = this.drawerManager.getCameraHolder();
        // cameraHolder.setWantedZoom(.5f);
        OrthographicCamera cam = cameraHolder.getCamera();
        cam.rotate(.1f);
        cam.position.set(-20, 0, 0);

        // cam.up.set(0, 1, 0);
        // cam.direction.set(0, 0, -1);

        //        cam.rotate(45/2f);
        //        cam.rotate(45);

        //        Vector3 pos = new Vector3();
        //        Quaternion rot = new Quaternion(0, 0, 0, 1);
        //        Vector3 sca = new Vector3(1f, 1f, 1f);
        //        cam.projection.set(new Matrix4(pos, rot, sca));
        // cam.rotate(0, 0, 0, 0);

        cam.update();

        Batch bb = this.drawerManager.getBatch();
        bb.setProjectionMatrix(cam.combined);

        ShapeRenderer sr = this.drawerManager.getShapeRenderer();
        sr.setProjectionMatrix(cam.combined);

        TextureRegion rr = this.drawerManager.getResourceManager().getColorTextureRegion(Color.BROWN);

        try (SwtDrawer_Shapes sd = this.drawerManager.startShapesDrawer()) {
            sd.setColor(Color.RED);
            ShapeRenderer re = sd.getRenderer();
            re.line(-10 * 10, 0, 10 * 10, 0);
            re.line(0, -10 * 10, 0, 10 * 10);
            for (int i = -10; i < 10; i++) {
                re.line(i * 10, -5, i * 10, 5);
                re.line(5, i * 10, -5, i * 10);
            }
            sd.drawCross(50, 50, 40);

            //          Rectangle canvas = this.drawerManager.getCameraHolder().getLastDims();
            Vector2 voo = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            Rectangle std = context.getStageDimensions();
            //            voo.y = std.height-voo.y;

            Vector2 scre = this.actor.screenToLocalCoordinates(voo);
            // scre.y = this.actor.getHeight() + scre.y;

            sd.setColor(Color.BLUE);
            sd.draw(new Ray2D(new Vector2(), scre));
            //            sd.draw(new Ray2D(new Vector2(), mouseScreenCoords));
            //
            //            sd.setColor(Color.MAGENTA);
            //            sd.draw(new Ray2D(new Vector2(), mouseCanvasCoords));

        }

        try (SwtDrawer_Batch<AResourceManager> bd = this.drawerManager.startBatchDrawer()) {
            bd.draw(rr, this.entity);
            bd.draw("/icons/128/snowflake.png", this.entity);

            if (this.isHovered())
            {
                // WAY: CANVAS-Relative Koordinaten müssen also ERST in Screen-Koordinaten umgewandelt werden.
                // Diese können dann wiederum auf die Cam zur Projektion gegeben werden.
                
                Vector2 in = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                Vector2 out = new Vector2();
                
                cameraHolder.screenCoordsToSceneCoords(in, out);
                
                bd.draw("/ui/starfield.png", 0, 0, out.x, out.y);                
                bd.drawText("" + out.x + "|" + out.y, new Rectangle(out.x, out.y, 2, 2), Align.top, .2f, true, Color.WHITE);
            }

        }

    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Rectangle getEntity() {
        return this.entity;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
