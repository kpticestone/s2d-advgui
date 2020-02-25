package com.s2dwt.core.awidget;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.s2dwt.core.layoutmanager.ASwtLayoutManager;
import com.s2dwt.core.stage.ISwtStage;
import com.s2dwt.core.window.ISwtWindow;
import com.s2dwt.impcomp.TOldCompatibilityCode;

public abstract class ASwtWidget_950_Calculating<ACTOR extends Actor> extends ASwtWidget_900_LayoutManager<ACTOR>
{
    // -------------------------------------------------------------------------------------------------------------------------
    private boolean negativePositions = true;

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_950_Calculating(ISwtStage<?, ?> pContext)
    {
        super(pContext);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_950_Calculating(ISwtWidget<? extends Group> pParent, boolean focusable)
    {
        super(pParent, focusable);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean calcPositions()
    {
        float wi, hh, newX, newY, newW, newH;
        if (this.parent == null)
        {
            wi = this.context.getWidth();
            hh = this.context.getHeight();
            newX = 0;
            newY = 0;
            newW = this.w > 0 ? this.w : wi + this.w;
            newH = this.h > 0 ? this.h : hh + this.h;
            if (TOldCompatibilityCode.TRUE)
            {
                newW = newW / this.context.getGuiScale();
                newH = newH / this.context.getGuiScale();
            }
            this.actor.setBounds(newX, newY, newW, newH);
        }
        else
        {
            if (!(this instanceof ISwtWindow))
            {
                wi = this.parent.getActor().getWidth();
                hh = this.parent.getActor().getHeight();
                newW = this.w > 0 ? this.w : wi + this.w;
                newH = this.h > 0 ? this.h : hh + this.h;
                newX = !negativePositions || this.x >= 0 ? this.x : wi + this.x;
                if (!negativePositions || this.y >= 0)
                {
                    newY = hh - newH - this.y;
                }
                else
                {
                    newY = -this.y - newH;
                }
                this.actor.setBounds(newX, newY, newW, newH);
            }
            else
            {
                Rectangle dims = this.context.getStageDimensions();
                this.actor.setBounds(0, 0, dims.width, dims.height);
            }
        }
        this.calcPositionsOfChildren();
        return true;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void calcPositionsOfChildren()
    {
        ASwtLayoutManager lm = this.layoutmanager;
        if (lm != null)
        {
            lm.calculate(this, this.actor.getWidth(), this.actor.getHeight());
        }
        for (ISwtWidget<?> a : this.getChildren())
        {
            a.calcPositions();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setLayoutNegativePositions(boolean b)
    {
        this.negativePositions = b;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
