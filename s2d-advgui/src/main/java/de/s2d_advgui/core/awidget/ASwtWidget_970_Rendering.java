package de.s2d_advgui.core.awidget;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import de.s2d_advgui.commons.Trigger;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.stage.ISwtStage;

public abstract class ASwtWidget_970_Rendering<ACTOR extends Actor> extends ASwtWidget_950_Calculating<ACTOR> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final Map<WidetLayer, Set<DrawableHolder>> drawer = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    private final Rectangle drawingRect = new Rectangle();

    // -------------------------------------------------------------------------------------------------------------------------
    protected boolean clip = false;

    // -------------------------------------------------------------------------------------------------------------------------
    private Vector2 v2 = new Vector2();

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_970_Rendering(ISwtStage<?, ?> pContext) {
        super(pContext);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_970_Rendering(@Nonnull SwtWidgetBuilder<ACTOR> pBuilder) {
        super(pBuilder);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addDrawer(WidetLayer pLayer, boolean pClip, IInternalWidgetDrawer pDrawer) {
        Set<DrawableHolder> kk = this.drawer.get(pLayer);
        if (kk == null) {
            kk = new LinkedHashSet<>();
            this.drawer.put(pLayer, kk);
        }
        kk.add(new DrawableHolder(pClip, pDrawer));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setClip(boolean clip) {
        this.clip = clip;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected final void makeDrawBorderSupport() {
        TextureRegion br1 = this.context.getResourceManager().getColorTextureRegion(Color.WHITE, 1, 1);
        TextureRegion cr1 = this.context.getTextureRegion("ui/corner-1.png");
        this.addDrawer(WidetLayer.BACKGROUND, false, new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> batch, Vector2 pScreenCoords, Rectangle re) {
                batch.setColor(Color.CYAN);
                batch.draw(br1, re.x + 2, re.y + 2, re.width - 4, 2);
                batch.draw(br1, re.x + 2, re.y + re.height - 4, re.width - 4, 2);
                batch.draw(br1, re.x + 2, re.y + 2, 2, re.height - 4);
                batch.draw(br1, re.x + re.width - 4, re.y + 2, 2, re.height - 4);
                batch.setColor(Color.CYAN);
                batch.draw(cr1, re.x, re.y + re.height - 11, 11, 11);
                batch.draw(cr1, re.x + re.width, re.y + re.height - 11, -11, 11);
                batch.draw(cr1, re.x + re.width, re.y + 11, -11, -11);
                batch.draw(cr1, re.x, re.y + 11, 11, -11);
                batch.setColor(Color.WHITE);
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addDrawerBackground(IInternalWidgetDrawer pDrawer) {
        this.addDrawer(WidetLayer.BACKGROUND, false, pDrawer);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addDrawerClipableBackground(IInternalWidgetDrawer pDrawer) {
        this.addDrawer(WidetLayer.BACKGROUND, true, pDrawer);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addDrawerClipableMiddle(IInternalWidgetDrawer pDrawer) {
        this.addDrawer(WidetLayer.MIDDLE, true, pDrawer);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addDrawerForeground(IInternalWidgetDrawer pDrawer) {
        this.addDrawer(WidetLayer.FOREGROUND, false, pDrawer);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addDrawerClipableForeground(IInternalWidgetDrawer pDrawer) {
        this.addDrawer(WidetLayer.FOREGROUND, true, pDrawer);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected final void _internalDrawWidget(Batch batch, float parentAlpha, Trigger pOrg) {
        if (!this.isVisible()) return;

        ACTOR base = this.actor;
        this.v2.set(0, 0);
        this.v2 = base.localToScreenCoordinates(this.v2);
        Rectangle stagedims = this.context.getStageDimensions();
        this.v2.x -= stagedims.x;
        this.v2.y += stagedims.y;

        this.drawingRect.set(base.getX(), base.getY(), base.getWidth(), base.getHeight());

        this.loopDrawer(WidetLayer.BACKGROUND, base, batch, this.v2, this.drawingRect);

        if (!this.clip) // || TOldCompatibilityCode.TRUE)
        {
            pOrg.onTrigger();
        } else {
            if (base.clipBegin()) {
                try {
                    pOrg.onTrigger();
                } finally {
                    batch.flush();
                    base.clipEnd();
                }
            } else {
                // System.err.println("cant clip @
                // ASwtWidget_970_Rendering._internalDrawWidget");
                // pOrg.onTrigger();
            }
        }
        this.loopDrawer(WidetLayer.MIDDLE, base, batch, this.v2, this.drawingRect);
        this.loopDrawer(WidetLayer.FOREGROUND, base, batch, v2, this.drawingRect);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected void loopDrawer(WidetLayer background, Actor base, Batch batch, Vector2 v22, Rectangle drawingRect2) {
        ISwtDrawerManager<?> dm = this.getContext().getDrawerManager();
        Matrix4 pm = batch.getProjectionMatrix();
        Matrix4 tm = batch.getTransformMatrix();

        ShapeRenderer srx = dm.getShapeRenderer();
        srx.setProjectionMatrix(pm);
        srx.setTransformMatrix(tm);

        ISwtDrawerManager<?> useDm = dm.cloneDrawerWithOtherBatch(batch, srx);
        Set<DrawableHolder> d1 = this.drawer.get(background);
        if (d1 != null) {
            for (DrawableHolder a : d1) {
                a.drawIt(base, useDm, this.v2, this.drawingRect);
                batch.setColor(Color.WHITE);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
