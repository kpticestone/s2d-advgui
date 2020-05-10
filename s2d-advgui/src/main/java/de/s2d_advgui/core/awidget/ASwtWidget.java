package de.s2d_advgui.core.awidget;

import javax.annotation.Nonnull;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.ATheme;
import de.s2d_advgui.core.stage.ISwtStage;
import de.s2d_advgui.core.utils.ShapeUtils;

public abstract class ASwtWidget<ACTOR extends Actor> extends ASwtWidget_970_Rendering<ACTOR> {
    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget(ISwtStage<?, ?> pContext) {
        super(pContext);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget(@Nonnull SwtWidgetBuilder<ACTOR> pBuilder) {
        super(pBuilder);
        if (this.focusable) {
            this.addDrawerBackground(new InternalWidgetDrawerBatch() {
                @Override
                protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                    if (isFocused()) {
                        pBatch.setColor(getTheme().getWidgetPrimaryBackgroundColorFocused());
                        pBatch.drawBorder(ATheme.BORDERS_FOCUS_ROUND_5_PNG, ShapeUtils.explode(pDims, -5, -2));
                    }
                }
            });
            this.addDrawerForeground(new InternalWidgetDrawerBatch() {
                @Override
                protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                    if (isHovered()) {
                        pBatch.setColor(getTheme().getWidgetPrimaryBorderColorHovered());
                        pBatch.drawBorder(ATheme.BORDERS_FOCUS_ROUND_5_PNG, ShapeUtils.explode(pDims, -5, -2));
                    }
                }
            });
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
