package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import de.s2d_advgui.core.awidget.ASwtWidgetSelectable;
import de.s2d_advgui.core.awidget.BorderDrawer2;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;

public class SwtButton extends ASwtWidgetSelectable<Button>
{
    // -------------------------------------------------------------------------------------------------------------------------
    String myText = null;

    // -------------------------------------------------------------------------------------------------------------------------
    String myIcon = null;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtButton(ISwtWidget<? extends Group> pParent, String text)
    {
        this(pParent);
        this.myText = text;
        this.addEnabledStateListener((b) -> this.actor.setDisabled(!b));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtButton(ISwtWidget<? extends Group> pParent)
    {
        super(pParent);

        BorderDrawer2 borderDrawer = new BorderDrawer2(this.context);
        BitmapFont a = this.context.getResourceManager().getFont(.5f, true);
        float lh = a.getLineHeight() - a.getAscent() + a.getDescent();
        this.addDrawerForeground(new InternalWidgetDrawerBatch()
        {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims)
            {
                borderDrawer.setGenericColors(enabled, isFocused(), hovered);
                borderDrawer.drawIt(pBatch.getBatch(), pDims);
                a.setColor(Color.WHITE);
                if (myIcon != null)
                {
                    TextureRegion check = SwtButton.this.context.getTextureRegion(myIcon);
                    if (myText == null)
                    {
                        pBatch.draw(check, pDims.x + (pDims.width - 16) / 2f, pDims.y + (pDims.height - 16) / 2f, 16, 16);
                    }
                    else
                    {
                        pBatch.draw(check, pDims.x + 4, pDims.y + 4, 16, 16);
                    }
                }
                if (myText != null)
                {
                    if (myIcon == null)
                    {
                        GlyphLayout gl = new GlyphLayout(a, myText);
                        a.draw(pBatch.getBatch(), gl, pDims.x + (pDims.width - gl.width) / 2f, pDims.y + pDims.height - (pDims.height - gl.height) / 2f);
                    }
                    else
                    {
                        a.draw(pBatch.getBatch(), SwtButton.this.myText, pDims.x + 24, pDims.y + (pDims.height) - (pDims.height - lh) / 2f);
                    }
                }
            }

        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected Button _createActor()
    {
        ButtonStyle style = new ButtonStyle();
        Button back = new Button(style)
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
        back.addListener(new EventListener()
        {
            @Override
            public boolean handle(Event event)
            {
                if (event instanceof InputEvent)
                {
                    if (((InputEvent)event).getType() == Type.keyTyped)
                    {
                        if (((InputEvent)event).getKeyCode() == Keys.ENTER || ((InputEvent)event).getKeyCode() == Keys.SPACE)
                        {
                            callListeners(0);
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
    public void setText(String string)
    {
        this.myText = string;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public String getText()
    {
        return this.myText;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setImage(String string)
    {
        this.myIcon = string;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
