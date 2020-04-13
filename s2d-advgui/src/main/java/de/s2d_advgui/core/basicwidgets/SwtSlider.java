package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.ASwtWidgetSelectable;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.awidget.SwtWidgetBuilder;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.resourcemanager.ATheme;

public class SwtSlider extends ASwtWidgetSelectable<Slider> {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtSlider(ASwtWidget<? extends Group> pParent) {
        super(new SwtWidgetBuilder<>(pParent, true, new ActorCreatorSlider()));
        this.registerChangeEventHandler(event -> {
            callListeners(0);
        });
        this.addDrawerBackground(new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                AResourceManager rm = pBatch.getResourceManager();
                ATheme theme = rm.getTheme();
                TextureRegion white = rm.getColorTextureRegion(Color.WHITE);
                pBatch.setColor(isEnabled() ? theme.getWidgetPrimaryBorderColor()
                        : theme.getWidgetPrimaryBorderColorDisabled());
                pBatch.drawBorder(ATheme.BORDERS_WHITE_ROUND_5_PNG, pDims);
                // pBatch.drawBorder(ATheme.BORDERS_WHITE_ROUND_5_PNG, new Rectangle(pDims.x,
                // pDims.y+4, pDims.width, pDims.height-8));

                pBatch.drawBorder(ATheme.BORDERS_WHITE_ROUND_5_PNG, new Rectangle(pDims.x, pDims.y, 15, pDims.height));
                pBatch.drawBorder(ATheme.BORDERS_WHITE_ROUND_5_PNG,
                        new Rectangle(pDims.x + pDims.width - 15, pDims.y, 15, pDims.height));

                float allWi = actor.getMaxValue() - actor.getMinValue();
                Rectangle r1 = new Rectangle(pDims.x + 12.5f, pDims.y + 6,
                        (pDims.width - 25) / allWi * actor.getValue(), pDims.height - 12);
                pBatch.draw(white, r1);

            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setRange(float min, float max) {
        this.actor.setRange(min, max);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public float getMax() {
        return this.actor.getMaxValue();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public float getMin() {
        return this.actor.getMinValue();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setValue(float value) {
        this.actor.setValue(value);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public float getValue() {
        return this.actor.getValue();
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
