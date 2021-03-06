package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;

public class SwtProgressBar extends ASwtWidget<ProgressBar>
{
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtProgressBar(ASwtWidget<? extends Group> pParent)
    {
        super(pParent, false);
        this.makeDrawBorderSupport();
        TextureRegion t1 = context.getResourceManager().getColorTextureRegion(Color.GREEN);
        int space = 8;
        int space2 = space * 2;
        this.addDrawerClipableMiddle(new InternalWidgetDrawerBatch()
        {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> batch, Vector2 pScreenCoords, Rectangle dims)
            {
                batch.draw(t1, dims.x + space, dims.y + space, (dims.width - space2) * actor.getPercent(), dims.height - space2);
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected ProgressBar createActor()
    {
        ProgressBarStyle style = new ProgressBarStyle();
        //      style.knobBefore = new TextureRegionDrawable(context.resourceManager.getColorTextureRegion(Color.GREEN, 15, 15));
        //      style.knob = null; // context.getDrawable("ui/icon.png");
        //      style.knobAfter = null; // new TextureRegionDrawable(context.resourceManager.getColorTextureRegion(Color.BLACK));
        ProgressBar back = new ProgressBar(0, 100, 1, false, style)
        {
            @Override
            public void draw(Batch batch, float parentAlpha)
            {
                _internalDrawWidget(this, batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }
        };
        back.setValue(25);
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setValue(float val)
    {
        this.actor.setValue(val);
    }

    // -------------------------------------------------------------------------------------------------------------------------

    /**
     * Set the maximum value of the progressbar
     * Warning! This needs to be set BEFORE setting the actual value!
     *
     * @param size max size
     */
    public void setMax(float size)
    {
        this.actor.setRange(0, size);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
