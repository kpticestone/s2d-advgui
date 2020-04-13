package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import de.s2d_advgui.commons.Trigger;
import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.ASwtWidgetDisableable;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.awidget.SwtWidgetBuilder;
import de.s2d_advgui.core.input.GuiUtils;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.ATheme;
import de.s2d_advgui.core.utils.RectangleFactory;

public class SwtTextField extends ASwtWidgetDisableable<TextField> {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtTextField(ISwtWidget<? extends Group> pParent) {
        super(new SwtWidgetBuilder<>(pParent, true, new ActorCreatorTextField()));

        TextureRegion wh = this.getResourceManager().getColorTextureRegion(Color.WHITE);

        this.addDrawerBackground(new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> batch, Vector2 pScreenCoords, Rectangle dims) {
                batch.setColor(getTheme().getWidgetPrimaryBackgroundColor());
                batch.setColor(Color.BLACK);
                batch.draw(wh, RectangleFactory.explode(dims, 5));
            }
        });
        this.addDrawerForeground(new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                if (isEnabled()) {
                    pBatch.setColor(getTheme().getWidgetPrimaryBorderColor());
                } else {
                    pBatch.setColor(getTheme().getWidgetPrimaryBorderColorDisabled());
                }
                pBatch.drawBorder(ATheme.BORDERS_WHITE_NB_ROUND_5_PNG, pDims);
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtTextField(ASwtWidget<? extends Group> pParent, String text) {
        this(pParent);
        this.setText(text);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setText(String string) {
        this.actor.setText(string);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public String getText() {
        return this.actor.getText();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setPassword(boolean isPassword) {
        if (isPassword) {
            this.actor.setPasswordCharacter('*');
            this.actor.setPasswordMode(true);
        } else {
            this.actor.setPasswordMode(false);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void addEnterListener(Trigger pTrigger) {
        this.registerEventHandler(InputEvent.Type.keyTyped, (event) -> {
            if (GuiUtils.isEnter(event.getCharacter())) { // Enter
                pTrigger.onTrigger();
                return true;
            }
            return false;
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void clearText() {
        this.actor.setText(null);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
