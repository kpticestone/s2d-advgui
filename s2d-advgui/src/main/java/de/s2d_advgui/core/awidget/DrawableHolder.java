package de.s2d_advgui.core.awidget;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import de.s2d_advgui.core.rendering.ISwtDrawerManager;

// -------------------------------------------------------------------------------------------------------------------------
class DrawableHolder {
    // -------------------------------------------------------------------------------------------------------------------------
    boolean clip;

    // -------------------------------------------------------------------------------------------------------------------------
    IInternalWidgetDrawer drawer;

    // -------------------------------------------------------------------------------------------------------------------------
    public DrawableHolder(boolean clip, IInternalWidgetDrawer drawer) {
        this.clip = clip;
        this.drawer = drawer;
    }

    // -------------------------------------------------------------------------------------------------------------------------
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
        } else {
            this.drawer.drawIt(batchdr, v2, drawingRect);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}