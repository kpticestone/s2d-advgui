package de.s2d_advgui.core.awidget;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import de.s2d_advgui.core.stage.ISwtStage;

public abstract class ASwtWidget_200_Coordinates<ACTOR extends Actor> extends ASwtWidget_050_Disposing<ACTOR> {
    // -------------------------------------------------------------------------------------------------------------------------
    protected float x = 0, y = 0, w = 0, h = 0;

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
        boolean changed = this.x != x || this.y != y || this.w != w || this.h != h;
        if (changed) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }
        this.calcPositions();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void setPosition(float x, float y) {
        boolean changed = this.x != x || this.y != y;
        if (changed) {
            this.x = x;
            this.y = y;
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
        return this.w;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final float getHeight() {
        return this.h;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final float getX() {
        return this.x;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final float getY() {
        return this.y;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
