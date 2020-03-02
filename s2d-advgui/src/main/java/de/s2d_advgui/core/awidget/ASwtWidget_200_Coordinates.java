package de.s2d_advgui.core.awidget;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.stage.ISwtStage;
import de.s2d_advgui.core.utils.IRectangleSupport;

public abstract class ASwtWidget_200_Coordinates<ACTOR extends Actor> extends ASwtWidget_050_Disposing<ACTOR> implements IRectangleSupport {
    // -------------------------------------------------------------------------------------------------------------------------
    // protected float x = 0, y = 0, w = 0, h = 0;
    protected final Rectangle bounds = new Rectangle();

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_200_Coordinates(ISwtStage<?, ?> pContext) {
        super(pContext);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_200_Coordinates(ISwtWidget<? extends Group> pParent, boolean focusable) {
        super(pParent, focusable);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void setBounds(float x, float y, float w, float h) {
        boolean changed = this.bounds.x != x || this.bounds.y != y || this.bounds.width != w || this.bounds.height != h;
        if (changed) {
            this.bounds.x = x;
            this.bounds.y = y;
            this.bounds.width = w;
            this.bounds.height = h;
        }
        this.calcPositions();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void setBounds(Rectangle src) {
        this.setBounds(src.x, src.y, src.width, src.height);
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
    public final Rectangle getBounds() {
        return new Rectangle(this.bounds); // das originale "bounds" verlässt nicht diese Klasse!
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void setPosition(float x, float y) {
        boolean changed = this.bounds.x != x || this.bounds.y != y;
        if (changed) {
            this.bounds.x = x;
            this.bounds.y = y;
        }
        this.calcPositions();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean getIgnoreOnCalcPos() {
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final float getWidth() {
        return this.bounds.width;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final float getHeight() {
        return this.bounds.height;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final float getX() {
        return this.bounds.x;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final float getY() {
        return this.bounds.y;
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
    public final void setX(float x) {
        this.bounds.x = x;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void setY(float y) {
        this.bounds.y = y;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void setWidth(float width) {
        this.bounds.width = width;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void setHeight(float height) {
        this.bounds.height = height;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
