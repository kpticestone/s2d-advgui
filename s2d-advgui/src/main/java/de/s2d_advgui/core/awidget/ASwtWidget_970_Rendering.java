package de.s2d_advgui.core.awidget;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.commons.TOldCompatibilityCode;
import de.s2d_advgui.commons.Trigger;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.stage.ISwtStage;

public abstract class ASwtWidget_970_Rendering<ACTOR extends Actor> extends ASwtWidget_950_Calculating<ACTOR> {
    // -------------------------------------------------------------------------------------------------------------------------
    public static enum WidetLayer {
        BACKGROUND, MIDDLE, FOREGROUND,
    }

    // -------------------------------------------------------------------------------------------------------------------------
    static class DrawableHolder {
        boolean clip;
        IInternalWidgetDrawer drawer;

        public DrawableHolder(boolean clip, IInternalWidgetDrawer drawer) {
            this.clip = clip;
            this.drawer = drawer;
        }

        public void drawIt(Actor base, ISwtDrawerManager<?> batchdr, Vector2 v2, Rectangle drawingRect) {
            if (this.clip) {
                if (base.clipBegin()) {
                    try {
                        this.drawer.drawIt(batchdr, v2, drawingRect);
                        batchdr.getBatch().flush();
                    } finally {
                        base.clipEnd();
                    }
                }
//                else
//                {
//                    System.err.println("cant clip 2 @ "+ base);
//                }
            } else {
                this.drawer.drawIt(batchdr, v2, drawingRect);
            }
        }

    }

    // -------------------------------------------------------------------------------------------------------------------------
    static float AX = .0f;

    // -------------------------------------------------------------------------------------------------------------------------
    static Color COL_BLUE_100 = new Color(AX, AX, 1f, 1f);
    static Color COL_BLUE_075 = new Color(AX, AX, .75f, .9f);
    static Color COL_BLUE_050 = new Color(AX, AX, .5f, .8f);
    static Color COL_BLUE_025 = new Color(AX, AX, .25f, .9f);
    static Color COL_BLUE_000 = new Color(AX, AX, 0f, 1f);

    // -------------------------------------------------------------------------------------------------------------------------
    // private final List<IInternalWidgetDrawer> drawerBackground = new
    // ArrayList<>();
    // private final List<IInternalWidgetDrawer> drawerForeground = new
    // ArrayList<>();
    // private final List<IInternalWidgetDrawer> drawerClipableMiddle = new
    // ArrayList<>();

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
    public ASwtWidget_970_Rendering(ISwtWidget<? extends Group> pParent, boolean focusable) {
        super(pParent, focusable);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addDrawer(WidetLayer layer, boolean clip, IInternalWidgetDrawer drawer) {
        Set<DrawableHolder> kk = this.drawer.get(layer);
        if (kk == null) {
            kk = new LinkedHashSet<>();
            this.drawer.put(layer, kk);
        }
        kk.add(new DrawableHolder(clip, drawer));
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
    protected final void _internalDrawWidget(Actor base, Batch batch, float parentAlpha, Trigger pOrg) {
        if (!this.isVisible()) return;

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
        this.loopDrawer(WidetLayer.FOREGROUND, base, batch, v2, drawingRect);

        if (TOldCompatibilityCode.FALSE) if (this.isFocusable() && this.context.getKeyboardFocus() == this.actor) {
            batch.setColor(Color.RED);
            TextureRegion br1 = this.context.getResourceManager().getColorTextureRegion(Color.WHITE, 1, 1);
            batch.draw(br1, this.drawingRect.x, this.drawingRect.y - 2, this.drawingRect.width, 2);
            batch.draw(br1, this.drawingRect.x, this.drawingRect.y + this.drawingRect.height, this.drawingRect.width,
                    2);

            batch.draw(br1, this.drawingRect.x - 2, this.drawingRect.y, 2, this.drawingRect.height);
            batch.draw(br1, this.drawingRect.x + this.drawingRect.width, this.drawingRect.y, 2,
                    this.drawingRect.height);
            batch.setColor(Color.WHITE);
        }
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
