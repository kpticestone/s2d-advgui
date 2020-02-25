package com.s2dwt.core.awidget;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.leo.commons.utils.TOldCompatibilityCode;
import com.leo.commons.utils.Trigger;
import com.s2dwt.core.stage.ISwtStage;

public abstract class ASwtWidget_970_Rendering<ACTOR extends Actor> extends ASwtWidget_950_Calculating<ACTOR> {
    static float AX = .0f;
    // -------------------------------------------------------------------------------------------------------------------------
    static Color COL_BLUE_100 = new Color(AX, AX, 1f, 1f);
    static Color COL_BLUE_075 = new Color(AX, AX, .75f, .9f);
    static Color COL_BLUE_050 = new Color(AX, AX, .5f, .8f);
    static Color COL_BLUE_025 = new Color(AX, AX, .25f, .9f);
    static Color COL_BLUE_000 = new Color(AX, AX, 0f, 1f);

    // -------------------------------------------------------------------------------------------------------------------------
    private final List<IInternalWidgetDrawer> drawerBackground = new ArrayList<>();
    private final List<IInternalWidgetDrawer> drawerForeground = new ArrayList<>();
    private final List<IInternalWidgetDrawer> drawerClipableMiddle = new ArrayList<>();

    // -------------------------------------------------------------------------------------------------------------------------
    private final Rectangle drawingRect = new Rectangle();

    // -------------------------------------------------------------------------------------------------------------------------
    protected boolean clip = false;

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_970_Rendering(ISwtStage<?, ?> pContext) {
        super(pContext);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_970_Rendering(ISwtWidget<? extends Group> pParent, boolean focusable) {
        super(pParent, focusable);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setClip(boolean clip) {
        this.clip = clip;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected final void makeDrawBorderSupport() {
        TextureRegion br1 = this.context.getResourceManager().getColorTextureRegion(Color.WHITE, 1, 1);
        TextureRegion cr1 = this.context.getTextureRegion("ui2/corner-1.png");
        this.drawerBackground.add((batch, pScreenCoords, re) -> {
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
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addDrawerBackground(IInternalWidgetDrawer pDrawer) {
        this.drawerBackground.add(pDrawer);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addDrawerForeground(IInternalWidgetDrawer pDrawer) {
        this.drawerForeground.add(pDrawer);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addDrawerClipableMiddle(IInternalWidgetDrawer pDrawer) {
        this.drawerClipableMiddle.add(pDrawer);
    }

    Vector2 v2 = new Vector2();

    // -------------------------------------------------------------------------------------------------------------------------
    protected final void _internalDrawWidget(Actor base, Batch batch, float parentAlpha, Trigger pOrg) {
        if (!this.isVisible()) return;

        this.v2.set(0, 0);
        this.v2 = base.localToScreenCoordinates(this.v2);
        Rectangle stagedims = this.context.getStageDimensions();
        this.v2.x-= stagedims.x;
        this.v2.y+= stagedims.y;
        
        this.drawingRect.set(base.getX(), base.getY(), base.getWidth(), base.getHeight());
        for (IInternalWidgetDrawer a : this.drawerBackground) {
            a.drawIt(batch, this.v2, this.drawingRect);
            batch.setColor(Color.WHITE);
        }
        if (this.clip || TOldCompatibilityCode.TRUE) {
            pOrg.onTrigger();
        } else {
            if (base.clipBegin()) {
                try {
                    pOrg.onTrigger();
                } finally {
                    base.clipEnd();
                }
            }
        }

        if (!this.drawerClipableMiddle.isEmpty()) {
            if (TOldCompatibilityCode.TRUE) {
                for (IInternalWidgetDrawer a : this.drawerClipableMiddle) {
                    if (a != null) {
                        a.drawIt(batch, this.v2, this.drawingRect);
                        batch.setColor(Color.WHITE);
                    }
                }
            } else {
                if (base.clipBegin()) {
                    try {
                        for (IInternalWidgetDrawer a : this.drawerClipableMiddle) {
                            if (a != null) {
                                a.drawIt(batch, this.v2, this.drawingRect);
                                batch.setColor(Color.WHITE);
                            }
                        }
                    } finally {
                        base.clipEnd();
                    }
                }
            }
        }

        for (IInternalWidgetDrawer a : this.drawerForeground) {
            a.drawIt(batch, this.v2, this.drawingRect);
            batch.setColor(Color.WHITE);
        }

        if( TOldCompatibilityCode.FALSE)    
        if (this.isFocusable() && this.context.getKeyboardFocus() == this.actor) {
            batch.setColor(Color.RED);
            TextureRegion br1 = this.context.getResourceManager().getColorTextureRegion(Color.WHITE, 1, 1);
            batch.draw(br1, this.drawingRect.x, this.drawingRect.y - 2, this.drawingRect.width, 2);
            batch.draw(br1, this.drawingRect.x, this.drawingRect.y + this.drawingRect.height, this.drawingRect.width, 2);

            batch.draw(br1, this.drawingRect.x - 2, this.drawingRect.y, 2, this.drawingRect.height);
            batch.draw(br1, this.drawingRect.x + this.drawingRect.width, this.drawingRect.y, 2, this.drawingRect.height);
            batch.setColor(Color.WHITE);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
