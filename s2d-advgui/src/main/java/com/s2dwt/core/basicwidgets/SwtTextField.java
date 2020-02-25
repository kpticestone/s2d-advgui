package com.s2dwt.core.basicwidgets;

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
import com.s2dwt.core.awidget.ASwtWidget;
import com.s2dwt.core.awidget.BorderDrawer2;
import com.s2dwt.core.awidget.InternalWidgetDrawerBatch;
import com.s2dwt.core.input.GuiUtils;
import com.s2dwt.core.rendering.SwtDrawer_Batch;
import com.s2dwt.impcomp.Trigger;

public class SwtTextField extends ASwtWidget<TextField>
{
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtTextField(ASwtWidget<? extends Group> pParent)
    {
        super(pParent, true);
        BorderDrawer2 br1 = new BorderDrawer2(context);
        this.addDrawerBackground(new InternalWidgetDrawerBatch()
        {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> batch, Vector2 pScreenCoords, Rectangle dims)
            {
                br1.setGenericColors(enabled, isFocused(), hovered);
                br1.drawIt(batch.getBatch(), dims);
            }
        });

    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtTextField(ASwtWidget<? extends Group> pParent, String text)
    {
        this(pParent);
        this.setText(text);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected TextField createActor()
    {
        TextFieldStyle style = new TextFieldStyle();
        BitmapFont fo = context.getResourceManager().getFont(.5f, true);
        style.font = fo;
        style.fontColor = Color.WHITE;
        style.font = fo;
        style.cursor = context.getDrawable("ui/pipe1.png");
        style.messageFont = fo;
        style.messageFontColor = Color.BLUE;
        style.selection = context.getDrawable("ui/pipe1.png");
        style.background = null;
        style.focusedFontColor = Color.WHITE;
        style.disabledFontColor = Color.BROWN;
        style.disabledBackground = null;
        style.focusedBackground = null;
        style.background = new TextureRegionDrawable(context.getResourceManager().getColorTextureRegion(new Color(0f, 0f, 0f, 0f), 0, 0));
        style.background.setLeftWidth(10);
        style.background.setRightWidth(10);
        TextField back = new TextField(null, style)
        {
            @Override
            public void draw(Batch batch, float parentAlpha)
            {
                _internalDrawWidget(this, batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }
        };
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setText(String string)
    {
        this.actor.setText(string);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public String getText()
    {
        return this.actor.getText();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setPassword(boolean isPassword)
    {
        if (isPassword)
        {
            this.actor.setPasswordCharacter('*');
            this.actor.setPasswordMode(true);
        }
        else
        {
            this.actor.setPasswordMode(false);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void addEnterListener(Trigger pTrigger)
    {
        this.actor.addListener(new InputListener()
        {
            @Override
            public boolean keyTyped(InputEvent event, char character)
            {
                if (GuiUtils.isEnter(character))
                { // Enter
                    pTrigger.onTrigger();
                    return true;
                }
                return false;
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void clearText()
    {
        this.actor.setText(null);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
