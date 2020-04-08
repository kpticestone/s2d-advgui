package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.Input.Keys;
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

import de.s2d_advgui.core.awidget.ASwtWidgetSelectable;
import de.s2d_advgui.core.awidget.BorderDrawer2;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.ATheme;

public class SwtCheckbox extends ASwtWidgetSelectable<CheckBox> {
    // -------------------------------------------------------------------------------------------------------------------------
    String myText;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtCheckbox(ISwtWidget<? extends Group> pParent, String text) {
        super(pParent);
        this.setText(text);
        TextureRegion check = this.context.getTextureRegion(ATheme.ICONS_128_SNOWFLAKE_PNG);
        BorderDrawer2 borderDrawer = new BorderDrawer2(this.context);
        BitmapFont a = this.getResourceManager().getFont(.5f, true);
        float lh = a.getLineHeight() - a.getAscent() + a.getDescent();
        ATheme theme = this.getTheme();
        this.addDrawerForeground(new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                borderDrawer.setGenericColors(SwtCheckbox.this.enabled, isFocused(), SwtCheckbox.this.hovered);
                borderDrawer.drawIt(pBatch.getBatch(), pDims);
                borderDrawer.setBackgroundColor(SwtCheckbox.this.enabled ? theme.getWidgetPrimaryBackgroundColor() : theme.getWidgetPrimaryBackgroundColorDisabled());
                borderDrawer.setBorderColor(SwtCheckbox.this.enabled ? theme.getWidgetPrimaryBorderColor() : theme.getWidgetPrimaryBorderColorDisabled());
                borderDrawer.drawIt(pBatch.getBatch(), pDims.x, pDims.y, pDims.height, pDims.height);
                if (isChecked()) {
                    pBatch.draw(check, pDims.x + 6, pDims.y + 6, 11, 11);
                }

                a.draw(pBatch.getBatch(), SwtCheckbox.this.myText + " (" + isChecked() + ")",
                        pDims.x + (pDims.height * 1.25f), pDims.y + (pDims.height) - (pDims.height - lh) / 2f);
            }

        });
        this.addEnabledStateListener((b) -> this.actor.setDisabled(!b));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected CheckBox _createActor() {
        // Skin style = new ButtonStyle();
        // style.down = new
        // TextureRegionDrawable(context.resourceManager.getColorTextureRegion(Color.GREEN));
        // style.over = new
        // TextureRegionDrawable(context.resourceManager.getColorTextureRegion(new
        // Color(.9f,.9f,1,1)));
        // style.up = new
        // TextureRegionDrawable(context.resourceManager.getColorTextureRegion(Color.BLUE));
        // this.makeDrawBorderSupport();
        CheckBoxStyle aa = this.context.getResourceManager().getSkin().get(CheckBoxStyle.class);
        System.err.println("aa:" + aa);
        CheckBoxStyle cbs = new CheckBoxStyle();
        cbs.font = this.context.getResourceManager().getFont(.5f, false);
        cbs.checkedOffsetX = 0;
        cbs.checkboxOff = this.context.getDrawable(ATheme.ICONS_128_SIGNALING_DISK_GREEN_PNG);
        cbs.checkboxOff.setMinWidth(16);
        cbs.checkboxOff.setMinHeight(16);
        cbs.checkboxOff.setLeftWidth(100);
        cbs.checkboxOn = this.context.getDrawable(ATheme.ICONS_128_SIGNALING_DISK_RED_PNG);
        cbs.checkboxOn.setMinHeight(16);
        cbs.checkboxOn.setMinWidth(16);
        // CheckBox back = new CheckBox(null, context.getResourceManager().getSkin())
        CheckBox back = new CheckBox(null, cbs) {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                _internalDrawWidget(this, batch, parentAlpha, () -> {
                    // super.draw(batch, parentAlpha);
                });
            }
        };
        back.getLabel().setFontScale(.75f);
        back.left();
        back.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof ChangeEvent) {
                    onInternalChanged();
                    return false;
                }
                if (event instanceof InputEvent) {
                    if (((InputEvent) event).getType() == Type.keyTyped) {
                        if (((InputEvent) event).getKeyCode() == Keys.ENTER
                                || ((InputEvent) event).getKeyCode() == Keys.SPACE) {
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
    private void onInternalChanged() {
        callListeners(0);
        this.focus();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void toggle() {
        this.actor.setChecked(!this.actor.isChecked());
        this.focus();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean isChecked() {
        return this.actor.isChecked();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setText(String text) {
        this.myText = text;
        this.actor.setText(text);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setChecked(boolean pChecked) {
        this.actor.setChecked(pChecked);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
