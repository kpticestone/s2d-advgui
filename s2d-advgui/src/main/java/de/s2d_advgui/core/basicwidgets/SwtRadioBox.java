package de.s2d_advgui.core.basicwidgets;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.core.awidget.ASwtWidgetSelectable;
import de.s2d_advgui.core.awidget.ISwtForm;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.awidget.SwtWidgetBuilder;
import de.s2d_advgui.core.awidget.acc.IActorCreator;
import de.s2d_advgui.core.rendering.IRend123;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.ATheme;

public class SwtRadioBox extends ASwtWidgetSelectable<ActorRadioBox> {
    // -------------------------------------------------------------------------------------------------------------------------
    private static final String PARENT_RADIO_BOXES = "parent-radio-boxes"; //$NON-NLS-1$

    // -------------------------------------------------------------------------------------------------------------------------
    private String myText;

    // -------------------------------------------------------------------------------------------------------------------------
    private RadioGroup radioGroup;

    // -------------------------------------------------------------------------------------------------------------------------
    static class RadioGroup {
        Set<SwtRadioBox> boxes = new HashSet<>();

        public boolean register(SwtRadioBox swtRadioBox) {
            this.boxes.add(swtRadioBox);
            return this.boxes.size() == 1;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtRadioBox(ISwtWidget<? extends Group> pParent) {
        this(pParent, null);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtRadioBox(ISwtWidget<? extends Group> pParent, @Nullable String pRadioGroup) {
        super(new SwtWidgetBuilder<>(pParent, true, new IActorCreator<ActorRadioBox>() {
            @Override
            public ActorRadioBox createActor(IRend123 pRend) {
                ActorRadioBox back = new ActorRadioBox() {
                    @Override
                    public void draw(Batch batch, float parentAlpha) {
                        pRend.doRender(batch, parentAlpha, () -> {
                        });
                    }
                };
                return back;
            }
        }));

        this.registerEventHandler(InputEvent.Type.keyTyped, (event) -> {
            if (event.getKeyCode() == Keys.ENTER
                    || event.getKeyCode() == Keys.SPACE) {
                setChecked();
                return true;
            }
            return false;
        });

        this.registerChangeEventHandler(event -> {
            onInternalChanged();
        });

        ISwtForm<?> form = this.getNextParent(ISwtForm.class);
        this.radioGroup = form.computeDataIfNotExists(
                pRadioGroup == null ? PARENT_RADIO_BOXES : PARENT_RADIO_BOXES + pRadioGroup, () -> new RadioGroup());
        if (this.radioGroup.register(this)) {
            this.setChecked(false);
        }

        TextureRegion check = this.context.getTextureRegion("ui/radio-checked.png"); //$NON-NLS-1$
        TextureRegion uncheck = this.context.getTextureRegion("ui/radio-unchecked.png"); //$NON-NLS-1$
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
                pBatch.drawText(SwtRadioBox.this.myText, pDims2, Align.left, .5f, true, theme.getLabelColor());
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
    protected ActorRadioBox __createActor() {
        ActorRadioBox back = new ActorRadioBox() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                _internalDrawWidget(batch, parentAlpha, () -> {
                });
            }
        };
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void onInternalChanged() {
        if (this.actor.isChecked()) {
            callListeners(0);
            for (SwtRadioBox a : this.radioGroup.boxes) {
                if (a != this) {
                    a.actor.setChecked(false, false);
                }
            }
            this.focus();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean isChecked() {
        return this.actor.isChecked();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setChecked(boolean pj) {
        this.actor.setChecked(true, pj);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setText(String text) {
        this.myText = text;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setChecked() {
        this.actor.setChecked();
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
