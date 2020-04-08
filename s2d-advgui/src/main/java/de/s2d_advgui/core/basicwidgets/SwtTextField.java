package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import de.s2d_advgui.commons.Trigger;
import de.s2d_advgui.core.SwtColor;
import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.BorderDrawer2;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.input.GuiUtils;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.ATheme;

public class SwtTextField extends ASwtWidget<TextField> {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtTextField(ISwtWidget<? extends Group> pParent) {
        super(pParent, true);
        BorderDrawer2 br1 = new BorderDrawer2(this.context);
        this.addDrawerBackground(new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> batch, Vector2 pScreenCoords, Rectangle dims) {
                br1.setGenericColors(SwtTextField.this.enabled, isFocused(), SwtTextField.this.hovered);
                br1.drawIt(batch.getBatch(), dims);
            }
        });

    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtTextField(ASwtWidget<? extends Group> pParent, String text) {
        this(pParent);
        this.setText(text);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected TextField createActor() {
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
        style.disabledFontColor = Color.BROWN;
        style.disabledBackground = null;
        style.focusedBackground = null;
        style.background = new TextureRegionDrawable(
                this.context.getResourceManager().getColorTextureRegion(SwtColor.TRANSPARENT, 0, 0));
        style.background.setLeftWidth(10);
        style.background.setRightWidth(10);
        TextField back = new TextField(null, style) {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                _internalDrawWidget(this, batch, parentAlpha, () -> super.draw(batch, parentAlpha));
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
        this.actor.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                if (GuiUtils.isEnter(character)) { // Enter
                    pTrigger.onTrigger();
                    return true;
                }
                return false;
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void clearText() {
        this.actor.setText(null);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
