package de.s2d_advgui.core.awidget;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import de.s2d_advgui.core.layoutmanager.ASwtLayoutManager;
import de.s2d_advgui.commons.TOldCompatibilityCode;
import de.s2d_advgui.core.stage.ISwtStage;
import de.s2d_advgui.core.window.ISwtWindow;

public abstract class ASwtWidget_950_Calculating<ACTOR extends Actor> extends ASwtWidget_900_LayoutManager<ACTOR>
{
    // -------------------------------------------------------------------------------------------------------------------------
    private boolean negativePositions = true;
    
    // -------------------------------------------------------------------------------------------------------------------------
    private boolean hasAlreadyFirstCalculationDone = false;

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
        Rectangle bnds = this.getBounds();
        if (this.parent == null)
        {
            wi = this.context.getWidth();
            hh = this.context.getHeight();
            newX = 0;
            newY = 0;
            newW = bnds.width > 0 ? bnds.width : wi + bnds.width;
            newH = bnds.height > 0 ? bnds.height : hh + bnds.height;
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
                newW = bnds.width > 0 ? bnds.width : wi + bnds.width;
                newH = bnds.height > 0 ? bnds.height : hh + bnds.height;
                newX = !negativePositions || bnds.x >= 0 ? bnds.x : wi + bnds.x;
                if (!negativePositions || bnds.y >= 0)
                {
                    newY = hh - newH - bnds.y;
                }
                else
                {
                    newY = -bnds.y - newH;
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
        if( !hasAlreadyFirstCalculationDone )
        {
            hasAlreadyFirstCalculationDone = true;
            onFirstCalculationDone();
        }
        return true;
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
    public void onFirstCalculationDone() {
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
