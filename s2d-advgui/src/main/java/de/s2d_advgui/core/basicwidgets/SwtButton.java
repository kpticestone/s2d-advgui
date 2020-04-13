package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import de.s2d_advgui.core.awidget.ASwtWidgetSelectable;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.awidget.SwtWidgetBuilder;
import de.s2d_advgui.core.awidget.acc.IActorCreator;
import de.s2d_advgui.core.rendering.IRend123;
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
        super(new SwtWidgetBuilder<>(pParent, true, new IActorCreator<Button>() {
            @Override
            public Button createActor(IRend123 pRend) {
                ButtonStyle style = new ButtonStyle();
                Button back = new Button(style) {
                    @Override
                    public void draw(Batch batch, float parentAlpha) {
                        pRend.doRender(batch, parentAlpha, () -> {
                            // super.draw(batch, parentAlpha);
                        });
                    }
                };
                return back;
            }
        }));

        this.registerEventHandler(InputEvent.Type.keyTyped, (event) -> {
            if (event.getKeyCode() == Keys.ENTER
                    || event.getKeyCode() == Keys.SPACE) {
                System.err.println("SwtButton.inputEventKeyTyped()");
                callListeners(0);
                return true;
            }
            return false;
        });

        TextureRegion wh = this.getResourceManager().getColorTextureRegion(Color.WHITE);
        this.addDrawerBackground(new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> batch, Vector2 pScreenCoords, Rectangle dims) {
                if (isEnabled()) {
                    batch.setColor(getTheme().getWidgetPrimaryBackgroundColor());
                } else {
                    batch.setColor(getTheme().getWidgetPrimaryBackgroundColorDisabled());
                }
                batch.draw(wh, RectangleFactory.explode(dims, 2));
            }
        });
        this.addDrawerForeground(new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                int iconSize = 16;
                int space = 5;
                Color col1 = isEnabled() ? Color.WHITE : getTheme().getLabelColorDisabled();
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
    protected final Button __createActor() {
        ButtonStyle style = new ButtonStyle();
        Button back = new Button(style) {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                _internalDrawWidget(batch, parentAlpha, () -> {
                    // super.draw(batch, parentAlpha);
                });
            }
        };
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
