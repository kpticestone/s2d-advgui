package com.s2dwt.core.basicwidgets;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.s2dwt.core.SwtColor;
import com.s2dwt.core.awidget.ASwtWidget;
import com.s2dwt.core.awidget.ASwtWidgetSelectable;
import com.s2dwt.core.awidget.BorderDrawer2;
import com.s2dwt.core.awidget.InternalWidgetDrawerBatch;
import com.s2dwt.core.rendering.SwtDrawer_Batch;

public class SwtCheckbox extends ASwtWidgetSelectable<CheckBox>
{

    // -------------------------------------------------------------------------------------------------------------------------
    String myText;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtCheckbox(ASwtWidget<? extends Group> pParent, String text)
    {
        super(pParent);
        this.setText(text);
        TextureRegion check = context.getTextureRegion("icons/128/snowflake.png");
        BorderDrawer2 borderDrawer = new BorderDrawer2(this.context);
        BitmapFont a = context.getResourceManager().getFont(.5f, true);
        float lh = a.getLineHeight() - a.getAscent() + a.getDescent();
        this.addDrawerForeground(new InternalWidgetDrawerBatch()
        {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims)
            {
                borderDrawer.setGenericColors(enabled, isFocused(), hovered);
                borderDrawer.drawIt(pBatch.getBatch(), pDims);

                borderDrawer.setBackgroundColor(enabled ? Color.BLACK : Color.DARK_GRAY);
                borderDrawer.setBorderColor(enabled ? SwtColor.BORDER_COLOR_CYAN : Color.GRAY);
                borderDrawer.drawIt(pBatch.getBatch(), pDims.x, pDims.y, pDims.height, pDims.height);

                if (isChecked())
                {
                    pBatch.draw(check, pDims.x + 6, pDims.y + 6, 11, 11);
                }

                a.draw(pBatch.getBatch(), SwtCheckbox.this.myText + " (" + isChecked() + ")", pDims.x + (pDims.height * 1.25f), pDims.y + (pDims.height) - (pDims.height - lh) / 2f);
            }

        });
        this.addEnabledStateListener((b) -> this.actor.setDisabled(!b));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected CheckBox _createActor()
    {
        // Skin style = new ButtonStyle();
        // style.down = new
        // TextureRegionDrawable(context.resourceManager.getColorTextureRegion(Color.GREEN));
        // style.over = new
        // TextureRegionDrawable(context.resourceManager.getColorTextureRegion(new
        // Color(.9f,.9f,1,1)));
        // style.up = new
        // TextureRegionDrawable(context.resourceManager.getColorTextureRegion(Color.BLUE));
        // this.makeDrawBorderSupport();
        CheckBoxStyle aa = context.getResourceManager().getSkin().get(CheckBoxStyle.class);
        System.err.println("aa:" + aa);
        CheckBoxStyle cbs = new CheckBoxStyle();
        cbs.font = context.getResourceManager().getFont(.5f, false);
        cbs.checkedOffsetX = 0;
        cbs.checkboxOff = context.getDrawable("icons/128/signaling_disk_green.png");
        cbs.checkboxOff.setMinWidth(16);
        cbs.checkboxOff.setMinHeight(16);
        cbs.checkboxOff.setLeftWidth(100);
        cbs.checkboxOn = context.getDrawable("icons/128/signaling_disk_red.png");
        cbs.checkboxOn.setMinHeight(16);
        cbs.checkboxOn.setMinWidth(16);
        //      CheckBox back = new CheckBox(null, context.getResourceManager().getSkin())
        CheckBox back = new CheckBox(null, cbs)
        {
            @Override
            public void draw(Batch batch, float parentAlpha)
            {
                _internalDrawWidget(this, batch, parentAlpha, () ->
                {
                    // super.draw(batch, parentAlpha);
                });
            }
        };
        back.getLabel().setFontScale(.75f);
        back.left();
        back.addListener(new EventListener()
        {
            @Override
            public boolean handle(Event event)
            {
                if (event instanceof ChangeEvent)
                {
                    onInternalChanged();
                    return false;
                }
                if (event instanceof InputEvent)
                {
                    if (((InputEvent)event).getType() == Type.keyTyped)
                    {
                        if (((InputEvent)event).getKeyCode() == Keys.ENTER || ((InputEvent)event).getKeyCode() == Keys.SPACE)
                        {
                            toggle();
                            return true;
                        }
                    }
                }
                return false;
            }

        });
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void onInternalChanged()
    {
        callListeners(0);
        this.focus();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void toggle()
    {
        this.actor.setChecked(!this.actor.isChecked());
        this.focus();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean isChecked()
    {
        return this.actor.isChecked();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setText(String text)
    {
        this.myText = text;
        this.actor.setText(text);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setChecked(boolean pChecked)
    {
        this.actor.setChecked(pChecked);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
