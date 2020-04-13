package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.core.awidget.ASwtWidgetSelectable;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.awidget.SwtWidgetBuilder;
import de.s2d_advgui.core.awidget.acc.IActorCreator;
import de.s2d_advgui.core.rendering.IRend123;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.resourcemanager.ATheme;

public class SwtCheckbox extends ASwtWidgetSelectable<CheckBox> {
    // -------------------------------------------------------------------------------------------------------------------------
    String myText;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtCheckbox(ISwtWidget<? extends Group> pParent) {
        this(pParent, null);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtCheckbox(ISwtWidget<? extends Group> pParent, String text) {
        super(new SwtWidgetBuilder<>(pParent, true, new IActorCreator<CheckBox>() {
            @Override
            public CheckBox createActor(IRend123 pRend) {
                AResourceManager rm = pRend.getResourceManager();
                CheckBoxStyle cbs = new CheckBoxStyle();
                cbs.font = rm.getFont(.5f, false);
                cbs.checkedOffsetX = 0;
                cbs.checkboxOff = rm.getDrawable(ATheme.ICONS_128_SIGNALING_DISK_GREEN_PNG);
                cbs.checkboxOff.setMinWidth(16);
                cbs.checkboxOff.setMinHeight(16);
                cbs.checkboxOff.setLeftWidth(100);
                cbs.checkboxOn = rm.getDrawable(ATheme.ICONS_128_SIGNALING_DISK_RED_PNG);
                cbs.checkboxOn.setMinHeight(16);
                cbs.checkboxOn.setMinWidth(16);
                CheckBox back = new CheckBox(null, cbs) {
                    @Override
                    public void draw(Batch batch, float parentAlpha) {
                        pRend.doRender(batch, parentAlpha, () -> {
                        });
                    }
                };
                back.getLabel().setFontScale(.75f);
                back.left();
                return back;
            }

        }));
        if (text != null) {
            this.setText(text);
        }
        this.registerEventHandler(InputEvent.Type.keyTyped, (event) -> {
            if (event.getKeyCode() == Keys.ENTER
                    || event.getKeyCode() == Keys.SPACE) {
                toggle();
                return true;
            }
            return false;
        });
        this.registerChangeEventHandler(event -> {
            onInternalChanged();
        });

        TextureRegion check = this.context.getTextureRegion("ui/check-checked.png"); //$NON-NLS-1$
        TextureRegion uncheck = this.context.getTextureRegion("ui/check-unchecked.png"); //$NON-NLS-1$
        ATheme theme = this.getTheme();
        this.addDrawerForeground(new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                if (isEnabled()) {
                    pBatch.setColor(theme.getWidgetPrimaryBorderColor());
                } else {
                    pBatch.setColor(theme.getWidgetPrimaryBorderColorDisabled());
                }
                if (isChecked()) {
                    pBatch.draw(check, pDims.x, pDims.y + (pDims.height - 16) / 2f, 16, 16);
                } else {
                    pBatch.draw(uncheck, pDims.x, pDims.y + (pDims.height - 16) / 2f, 16, 16);
                }
                Rectangle pDims2 = new Rectangle(pDims.x + 16 + 5, pDims.y, pDims.width - 16 - 5, pDims.height);
                pBatch.drawText(SwtCheckbox.this.myText, pDims2, Align.left, .5f, true, theme.getLabelColor());
            }

        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void applyDisabledOnActor(boolean b) {
        this.actor.setDisabled(b);
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
