package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import de.s2d_advgui.core.awidget.ASwtWidgetSelectable;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.ATheme;
import de.s2d_advgui.core.utils.RectangleFactory;

public class SwtButton extends ASwtWidgetSelectable<Button> {
    // -------------------------------------------------------------------------------------------------------------------------
    String myText = null;

    // -------------------------------------------------------------------------------------------------------------------------
    String myIcon = null;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtButton(ISwtWidget<? extends Group> pParent, String text) {
        this(pParent);
        this.myText = text;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtButton(ISwtWidget<? extends Group> pParent) {
        super(pParent);
        TextureRegion wh = this.getResourceManager().getColorTextureRegion(Color.WHITE);
        this.addDrawerBackground(new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> batch, Vector2 pScreenCoords, Rectangle dims) {
                if (isEnabled()) {
                    batch.setColor(getTheme().getWidgetPrimaryBorderColor());
                } else {
                    batch.setColor(getTheme().getWidgetPrimaryBorderColorDisabled());
                }
                batch.draw(wh, RectangleFactory.explode(dims, 5));
            }
        });
        this.addDrawerForeground(new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                int iconSize = 16;
                int space = 5;
                Color col1 = isEnabled()?Color.WHITE:getTheme().getLabelColorDisabled();
                if (myIcon != null && myText != null) {
                    Rectangle rIcon = new Rectangle(pDims.x + space, pDims.y, iconSize, pDims.height);
                    Rectangle rText = new Rectangle(pDims.x + space + iconSize + space, pDims.y,
                            pDims.width - iconSize - space - space, pDims.height);
                    TextureRegion tex = SwtButton.this.context.getTextureRegion(myIcon);
                    pBatch.drawText(myText, rText, Align.left, .5f, true, col1);
                    pBatch.draw(tex, rIcon, Scaling.fit);
                } else if (myIcon != null) {
                    TextureRegion tex = SwtButton.this.context.getTextureRegion(myIcon);
                    pBatch.draw(tex, RectangleFactory.explode(pDims, space), Scaling.fit);
                } else if (myText != null) {
                    pBatch.drawText(myText, pDims, Align.center, .5f, true, col1);
                }
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
    @Override
    protected Button _createActor() {
        ButtonStyle style = new ButtonStyle();
        Button back = new Button(style) {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                _internalDrawWidget(this, batch, parentAlpha, () -> {
                    // super.draw(batch, parentAlpha);
                });
            }
        };
        back.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof InputEvent) {
                    if (((InputEvent) event).getType() == Type.keyTyped) {
                        if (((InputEvent) event).getKeyCode() == Keys.ENTER
                                || ((InputEvent) event).getKeyCode() == Keys.SPACE) {
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
    public void setText(String string) {
        this.myText = string;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public String getText() {
        return this.myText;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setImage(String string) {
        this.myIcon = string;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
