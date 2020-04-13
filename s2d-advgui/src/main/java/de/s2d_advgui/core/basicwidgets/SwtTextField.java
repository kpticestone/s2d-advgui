package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import de.s2d_advgui.commons.Trigger;
import de.s2d_advgui.core.SwtColor;
import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.ASwtWidgetDisableable;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.awidget.SwtWidgetBuilder;
import de.s2d_advgui.core.awidget.acc.IActorCreator;
import de.s2d_advgui.core.input.GuiUtils;
import de.s2d_advgui.core.rendering.IRend123;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.resourcemanager.ATheme;
import de.s2d_advgui.core.utils.RectangleFactory;

public class SwtTextField extends ASwtWidgetDisableable<TextField> {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtTextField(ISwtWidget<? extends Group> pParent) {
        super(new SwtWidgetBuilder<>(pParent, true, new IActorCreator<TextField>() {
            @Override
            public TextField createActor(IRend123 pRend) {
                AResourceManager rm = pRend.getResourceManager();
                TextFieldStyle style = new TextFieldStyle();
                BitmapFont fo = rm.getFont(.5f, true);
                style.font = fo;
                style.fontColor = Color.WHITE;
                style.font = fo;
                style.cursor = rm.getDrawable(ATheme.UI_PIPE1_PNG);
                style.messageFont = fo;
                style.messageFontColor = Color.BLUE;
                style.selection = rm.getDrawable(ATheme.UI_PIPE1_PNG);
                style.background = null;
                style.focusedFontColor = Color.WHITE;
                style.disabledFontColor = rm.getTheme().getLabelColorDisabled();
                style.disabledBackground = null;
                style.focusedBackground = null;
                style.background = new TextureRegionDrawable(
                        rm.getColorTextureRegion(SwtColor.TRANSPARENT, 0, 0));
                style.background.setLeftWidth(10);
                style.background.setRightWidth(10);
                TextField back = new TextField(null, style) {
                    @Override
                    public void draw(Batch batch, float parentAlpha) {
                        pRend.doRender(batch, parentAlpha, () -> super.draw(batch, parentAlpha));
                    }
                };
                return back;
            }
        }));

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
    @Override
    protected void applyDisabledOnActor(boolean b) {
        this.actor.setDisabled(b);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtTextField(ASwtWidget<? extends Group> pParent, String text) {
        this(pParent);
        this.setText(text);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected TextField __createActor() {
        TextFieldStyle style = new TextFieldStyle();
        BitmapFont fo = this.context.getResourceManager().getFont(.5f, true);
        style.font = fo;
        style.fontColor = Color.WHITE;
        style.font = fo;
        style.cursor = this.context.getDrawable(ATheme.UI_PIPE1_PNG);
        style.messageFont = fo;
        style.messageFontColor = Color.BLUE;
        style.selection = this.context.getDrawable(ATheme.UI_PIPE1_PNG);
        style.background = null;
        style.focusedFontColor = Color.WHITE;
        style.disabledFontColor = getTheme().getLabelColorDisabled();
        style.disabledBackground = null;
        style.focusedBackground = null;
        style.background = new TextureRegionDrawable(
                this.context.getResourceManager().getColorTextureRegion(SwtColor.TRANSPARENT, 0, 0));
        style.background.setLeftWidth(10);
        style.background.setRightWidth(10);
        TextField back = new TextField(null, style) {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                _internalDrawWidget(batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }
        };
        return back;
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
